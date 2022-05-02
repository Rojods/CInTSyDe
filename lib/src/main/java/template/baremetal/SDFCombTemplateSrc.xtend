package template.baremetal

import fileAnnotation.FileType

import fileAnnotation.FileTypeAnno
import forsyde.io.java.core.Vertex
import forsyde.io.java.core.VertexAcessor
import forsyde.io.java.core.VertexAcessor.VertexPortDirection
import forsyde.io.java.core.VertexTrait
import generator.Generator
import java.util.ArrayList
import java.util.HashMap
import java.util.HashSet
import java.util.Set
import template.templateInterface.ActorTemplate

import utils.Query
import java.util.stream.Collectors
import forsyde.io.java.core.EdgeInfo
import forsyde.io.java.core.EdgeTrait
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb

@FileTypeAnno(type=FileType.C_SOURCE)
class SDFCombTemplateSrc implements ActorTemplate {
	Set<Vertex> implActorSet
	Set<Vertex> inputSDFChannelSet
	Set<Vertex> outputSDFChannelSet

	override create(Vertex actor) {
		var model = Generator.model
		implActorSet = VertexAcessor.getMultipleNamedPort(Generator.model, actor, "combFunctions",
			VertexTrait.IMPL_ANSICBLACKBOXEXECUTABLE, VertexPortDirection.OUTGOING)
		this.inputSDFChannelSet = Query.findInputSDFChannels(model, actor)
		this.outputSDFChannelSet = Query.findOutputSDFChannels(model, actor)
		'''
			/* Includes-------------------------- */
			#include "../inc/datatype_definition.h"
			«var name = actor.getIdentifier()»
			/*
			========================================
				Declare Extern Channal Variables
			========================================
			*/
			«extern()»
			/*
			========================================
				Actor Function
			========================================
			*/			
			inline void actor_«name»(){
				/* Initilize Memory      */
				«initMemory(actor)»
				
				/* Read From Input Port  */
				«read(actor)»
				/* Inline Code           */
				«getInlineCode()»
			
				/* Write To Output Ports */
				«write(actor)»
			}
		'''
	}
	
	def String extern() {
		var Set<Vertex> record=new HashSet
		'''
		«FOR sdf: this.inputSDFChannelSet SEPARATOR"" AFTER""»
		«IF !record.contains(sdf)»
		extern fifo_«sdf.getIdentifier()»;
		«var tmp=record.add(sdf)»
		«ENDIF»
		«ENDFOR»
		
		«FOR sdf: this.outputSDFChannelSet SEPARATOR"" AFTER""»
		«IF !record.contains(sdf)»
		extern fifo_«sdf.getIdentifier()»;
		«var tmp=record.add(sdf)»
		«ENDIF»
		«ENDFOR»		
		'''
	}

	def String initMemory(Vertex actor) {
		var model = Generator.model
		var impls = Query.findCombFuntionVertex(model, actor)
		var Set<String> variableNameRecord = new HashSet
		var String ret = ""

		for (String impl : impls) {
			var actorimpl = model.vertexSet().stream().filter([v|v.getIdentifier().equals(impl)]).findAny().orElse(null)
			var inputPortSet = Query.findImplInputPortSet(actorimpl)
			for (String inport : inputPortSet) {
				var datatype = Query.findImplPortDataType(model, actorimpl, inport)
				if (!variableNameRecord.contains(inport)) {
					ret += '''
						«datatype» «inport»; 
					'''
					variableNameRecord.add(inport)
				}
			}
			var outputPortSet = Query.findImplOutputPortSet(actorimpl)
			for (String outport : outputPortSet) {
				var datatype = Query.findImplPortDataType(model, actorimpl, outport)
				if (!variableNameRecord.contains(outport)) {
					ret += '''
						«datatype» «outport»; 
					'''
					variableNameRecord.add(outport)
				}
			}
		}
		return ret
	}

	def String read(Vertex actor) {
		var model = Generator.model
		var impls = Query.findCombFuntionVertex(model, actor)
		var Set<String> variableNameRecord = new HashSet
		var String ret = ""
		for (String impl : impls) {
			var actorimpl = Query.findVertexByName(model, impl)
			var inputPortSet = Query.findImplInputPortSet(actorimpl)
			for (String inport : inputPortSet) {

				if (!variableNameRecord.contains(inport)) {
					var datatype = Query.findImplPortDataType(model, actorimpl, inport)
					var actorPortName = Query.findActorPortConnectedToImplInputPort(model, actor, actorimpl, inport)
					var sdfchannelName = Query.findInputSDFChannelConnectedToActorPort(model, actor, actorPortName)
					try {
						var consumption = SDFComb.enforce(actor).getConsumption().get(actorPortName)
						if (consumption == 1) {
							ret += '''
								read_non_blocking(&fifo_«sdfchannelName»,&«inport»);
							'''
						} else {
							ret += '''
								for(int i=0;i<«consumption»;++i){
									read_non_blocking(&fifo_«sdfchannelName»,&«inport»[i]);
								}
							'''
						}
						variableNameRecord.add(inport)
					} catch (Exception e) {
						println("In actor " + actor.getIdentifier() + " port " + inport + " no comsumption")
						return "error "+inport+";"
					}

				}
			}
		}
		return ret
	}

	def String write(Vertex actor) {
		var model = Generator.model
		var impls = Query.findCombFuntionVertex(model, actor)
		var Set<String> variableNameRecord = new HashSet
		var String ret = ""
		for (String impl : impls) {
			var actorimpl = Query.findVertexByName(model, impl)
			var outputPortSet = Query.findImplOutputPortSet(actorimpl)
			for (String outport : outputPortSet) {

				if (!variableNameRecord.contains(outport)) {
					var datatype = Query.findImplPortDataType(model, actorimpl, outport)
					var actorPortName = Query.findActorPortConnectedToImplOutputPort(model, actor, actorimpl, outport)
					var sdfchannelName = Query.findOutputSDFChannelConnectedToActorPort(model, actor, actorPortName)
					try {
						var production = SDFComb.enforce(actor).getProduction().get(actorPortName)
						if (production == 1) {
							ret += '''
								write_non_blocking(&fifo_«sdfchannelName»,&«outport»);
							'''
						} else {
							ret += '''
								for(int i=0;i<«production »;++i){
									write_non_blocking(&fifo_«sdfchannelName»,&«outport»[i]);
								}
							'''
						}
						variableNameRecord.add(outport)
					} catch (Exception e) {
						println("In actor " + actor.getIdentifier() + " port " + outport + " no production")
						return "error "+outport+";"
					}

				}
			}
		}
		return ret
	}

	private def String getInlineCode() {

		'''
			«FOR impl : implActorSet SEPARATOR "" AFTER ""»
				//in combFunction «impl.getIdentifier()»
				«Query.getInlineCode(impl)»
			«ENDFOR»		
		'''

	}

	private def String getExternSDFChannel(Vertex actor) {

		var SDFChannelSet = Query.findInputSDFChannels(Generator.model, actor)
		SDFChannelSet.addAll(Query.findOutputSDFChannels(Generator.model, actor))
		'''
			«FOR sdfchannel : SDFChannelSet SEPARATOR "" AFTER ""»
				
			«ENDFOR»
		'''
	}

}
