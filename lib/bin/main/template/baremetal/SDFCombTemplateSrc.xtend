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
import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.typed.viewers.moc.sdf.SDFCombViewer
import forsyde.io.java.typed.viewers.impl.Executable
import forsyde.io.java.typed.viewers.typing.TypedOperation

@FileTypeAnno(type=FileType.C_SOURCE)
class SDFCombTemplateSrc implements ActorTemplate {
	Set<Vertex> implActorSet
	Set<Vertex> inputSDFChannelSet
	Set<Vertex> outputSDFChannelSet

	override create(Vertex actor) {
		var model = Generator.model
		//implActorSet = (new SDFCombViewer(actor)).getCombFunctionsPort(Generator.model)
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
				«initMemory(model,actor)»
				/* Read From Input Port  */
				«read(model,actor)»
				/* Inline Code           */
				«getInlineCode()»
			
				/* Write To Output Ports */
				«write(actor)»
			}
		'''
	}

	def String extern() {
		var Set<Vertex> record = new HashSet
		'''
			/* Input FIFO */
			«FOR sdf : this.inputSDFChannelSet SEPARATOR "" AFTER ""»
				«IF !record.contains(sdf)»
					extern fifo_«sdf.getIdentifier()»;
					«var tmp=record.add(sdf)»
				«ENDIF»
			«ENDFOR»
			/* Output FIFO */
			«FOR sdf : this.outputSDFChannelSet SEPARATOR "" AFTER ""»
				«IF !record.contains(sdf)»
					extern fifo_«sdf.getIdentifier()»;
					«var tmp=record.add(sdf)»
				«ENDIF»
			«ENDFOR»		
		'''
	}

	def String initMemory(ForSyDeSystemGraph model,Vertex actor) {
		
		var impls = Query.findCombFuntionVertex(model, actor)
		var Set<String> variableNameRecord = new HashSet
		var String ret = ""

		for (String impl : impls) {
			var actorimpl = model.queryVertex(impl).get()
			var Set<String> ports = new HashSet
			ports.addAll(Query.findImplInputPorts(actorimpl))
			ports.addAll(Query.findImplOutputPorts(actorimpl))
			for(String port:ports){
				var datatype = Query.findImplPortDataType(model, actorimpl, port)
				if (!variableNameRecord.contains(port)) {
					if (Query.isSystemChannel(model, actorimpl, port) === null) {
						ret += '''
							«datatype» «port»; 
						'''
					} else {
						ret += '''
							«datatype» «port» = «Query.isSystemChannel(model,actorimpl,port)»; 
						'''
					}
					variableNameRecord.add(port)
				}
			}
//			var actorimpl = model.queryVertex(impl).get()
//			var inputPortList =Query.findImplInputPorts(actorimpl)
//			for (String inport : inputPortList) {
//				var datatype = Query.findImplPortDataType(model, actorimpl, inport)
//				if (!variableNameRecord.contains(inport)) {
//					if (Query.isSystemChannel(model, actorimpl, inport) === null) {
//						ret += '''
//							«datatype» «inport»; 
//						'''
//					} else {
//						ret += '''
//							«datatype» «inport» = «Query.isSystemChannel(model,actorimpl,inport)»; 
//						'''
//					}
//					variableNameRecord.add(inport)
//				}
//			}
//			var outputPortList = TypedOperation.safeCast(actorimpl).get().getOutputPorts()
//			for (String outport : outputPortList) {
//				var datatype = Query.findImplPortDataType(model, actorimpl, outport)
//				if (!variableNameRecord.contains(outport)) {
//					if (Query.isSystemChannel(model, actorimpl, outport) === null) {
//						ret += '''
//							«datatype» «outport»; 
//						'''
//					} else {
//						ret += '''
//							«datatype» «outport» = «Query.isSystemChannel(model, actorimpl, outport)»; 
//						'''
//					}
//
//					variableNameRecord.add(outport)
//				}
//			}
		}
		return ret
	}

	def String read(ForSyDeSystemGraph model,Vertex actor) {
		var Set<Vertex> impls = SDFComb.safeCast(actor).get().getCombFunctionsPort(model).stream()
							.map([e|e.getViewedVertex()])
							.collect(Collectors.toSet())
		var Set<String> variableNameRecord = new HashSet
		var String ret = ""
		for(Vertex impl: impls){
			
			var inputPorts = TypedOperation.safeCast(impl).get().getInputPorts()
			for(String port :inputPorts){
				if(!variableNameRecord.contains(port) && Query.isSystemChannel(model,impl, port) === null){
					var datatype = Query.findImplPortDataType(model, impl, port)
					var actorPortName = Query.findActorPortConnectedToImplInputPort(model, actor, impl, port)
					var sdfchannelName = Query.findInputSDFChannelConnectedToActorPort(model, actor, actorPortName)
					var consumption = SDFComb.safeCast(actor).get().getConsumption().get(actorPortName)
					
					if (consumption == 1) {
						ret += '''
							read_non_blocking(&fifo_«sdfchannelName»,&«port»);
						'''
					} else {
						ret += '''
							for(int i=0;i<«consumption»;++i){
								read_non_blocking(&fifo_«sdfchannelName»,&«port»[i]);
							}
						'''
					}
					variableNameRecord.add(port)
				}
			}
				
			
		}
		return ret;
//		var impls = Query.findCombFuntionVertex(model, actor)
//		var Set<String> variableNameRecord = new HashSet
//		var String ret = ""
//		for (String impl : impls) {
//			var actorimpl = Query.findVertexByName(model, impl)
//			var inputPortSet = Query.findImplInputPorts(actorimpl)
//			for (String inport : inputPortSet) {
//				if (!variableNameRecord.contains(inport) && Query.isSystemChannel(model, actorimpl, inport) === null) {
//					var datatype = Query.findImplPortDataType(model, actorimpl, inport)
//					var actorPortName = Query.findActorPortConnectedToImplInputPort(model, actor, actorimpl, inport)
//					var sdfchannelName = Query.findInputSDFChannelConnectedToActorPort(model, actor, actorPortName)
//					println(actor.getIdentifier()+" "+actorPortName)
//					var consumption = SDFComb.enforce(actor).getConsumption().get(actorPortName)
//					if (consumption == 1) {
//						ret += '''
//							read_non_blocking(&fifo_«sdfchannelName»,&«inport»);
//						'''
//					} else {
//						ret += '''
//							for(int i=0;i<«consumption»;++i){
//								read_non_blocking(&fifo_«sdfchannelName»,&«inport»[i]);
//							}
//						'''
//					}
//					variableNameRecord.add(inport)
//
//				}
//			}
//		}
//		return ret

	}

	def String write(Vertex actor) {
		var model = Generator.model
		var impls = Query.findCombFuntionVertex(model, actor)
		var Set<String> variableNameRecord = new HashSet
		var String ret = ""
		for (String impl : impls) {
			var actorimpl = Query.findVertexByName(model, impl)
			var outputPortSet = Query.findImplOutputPorts(actorimpl)
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
								for(int i=0;i<«production»;++i){
									write_non_blocking(&fifo_«sdfchannelName»,&«outport»[i]);
								}
							'''
						}
						variableNameRecord.add(outport)
					} catch (Exception e) {
						println("In actor " + actor.getIdentifier() + " port " + outport + " no production")
						return "error " + outport + ";"
					}

				}
			}
		}
		return ret
	}

	private def String getInlineCode() {

		'''
			«FOR impl : implActorSet SEPARATOR "" AFTER ""»
				/* in combFunction «impl.getIdentifier()» */
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
