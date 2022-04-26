package template.nonRTOS.subsystem

import forsyde.io.java.core.EdgeTrait
import forsyde.io.java.core.Vertex
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb
import forsyde.io.java.typed.viewers.moc.sdf.SDFDelay
import java.util.ArrayList
import java.util.TreeSet
import java.util.stream.Collectors
import utils.Global
import utils.Name

import static extension utils.Query.getPortRate
import static extension utils.Query.getSDFChannelName
import java.util.Set
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel

class subsystemHelp {
	static def String actorParameter( Vertex vertex){
		var name =Name.name(vertex)
		var out= Global.model.outgoingEdgesOf(vertex).stream()
									.filter([
										edgeinfo|edgeinfo.hasTrait(EdgeTrait.MOC_SDF_SDFDATAEDGE) 
											||	edgeinfo.hasTrait(EdgeTrait.IMPL_DATAMOVEMENT)
									])
									.map([e|e.getSourcePort().get()])
									.collect(Collectors.toSet())
		
		
		var in = Global.model.incomingEdgesOf(vertex).stream()
									.filter([
										edgeinfo|edgeinfo.hasTrait(EdgeTrait.MOC_SDF_SDFDATAEDGE)
										||	edgeinfo.hasTrait(EdgeTrait.IMPL_DATAMOVEMENT)
									]
											
									)
									.map([e|e.getTargetPort().get()])
									.collect(Collectors.toSet())	

		
		var TreeSet<String> inputPorts =new TreeSet(in)
		var TreeSet<String> outputPorts = new TreeSet(out)		
		
		'''
			«FOR port: inputPorts SEPARATOR "," AFTER " " »
				«««		«var int rate = SDFComb.enforce(vertex).getPortRate(port)»
		&channel_«vertex.getSDFChannelName(port,Global.model)»,«SDFComb.enforce(vertex).getPortRate(port)»
			«ENDFOR»
			«««	
		«IF inputPorts.size()==0 »
				«FOR port: outputPorts SEPARATOR "," AFTER " " »
					&channel_«vertex.getSDFChannelName(port,Global.model)»,«SDFComb.enforce(vertex).getPortRate(port)»
				«ENDFOR»
			«ELSE»
				«FOR port: outputPorts SEPARATOR " " AFTER " " »
					,&channel_«vertex.getSDFChannelName(port,Global.model)»,«SDFComb.enforce(vertex).getPortRate(port)»
				«ENDFOR»		
			«ENDIF»
		'''
	}	
	
	static def String  sdfDelayHelpB(Set<SDFChannel> temps){
		var delays = Global.model.vertexSet().stream().filter([v|SDFDelay.conforms(v)]).collect(Collectors.toSet())
		var sdfdelays=  delays.stream().map([v|SDFDelay.enforce(v)]).collect(Collectors.toSet())
		
		'''
			«FOR delay:sdfdelays   SEPARATOR "" AFTER ""»
				
				«IF delay.getDelayedChannelPort(Global.model).isPresent()»
					«var sdfchannel=delay.getDelayedChannelPort(Global.model).get()»
				«IF temps.contains(sdfchannel)»	
					«var tokens = (delay.getProperties().get("delayedTokens").unwrap() as ArrayList<Integer>)»
					«var channelName= Name.name(sdfchannel)»
					«FOR  token :tokens SEPARATOR "\n" AFTER"\n"»
					write_circularFIFO_non_blocking_«channelName»(&channel_«channelName»,«token»);
					«ENDFOR»
				«ENDIF»
				«ENDIF»
			«ENDFOR»	
		'''		
	}
	static def String  sdfDelayHelpA(Set<Vertex> channels){
		var temps =channels.stream().map([cha|SDFChannel.enforce(cha)]).collect(Collectors.toSet())
		sdfDelayHelpB(temps)
	}	 
}