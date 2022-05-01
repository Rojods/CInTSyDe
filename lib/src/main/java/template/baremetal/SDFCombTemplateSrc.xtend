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
import utils.Name
import utils.Query
import java.util.stream.Collectors
import forsyde.io.java.core.EdgeInfo
import forsyde.io.java.core.EdgeTrait

@FileTypeAnno(type=FileType.C_SOURCE)
class SDFCombTemplateSrc implements ActorTemplate {
	Set<Vertex> implActorSet
	Set<Vertex> inputSDFChannelSet
	Set<Vertex> outputSDFChannelSet

	override create(Vertex actor) {
		implActorSet = VertexAcessor.getMultipleNamedPort(Generator.model, actor, "combFunctions",
			VertexTrait.IMPL_ANSICBLACKBOXEXECUTABLE, VertexPortDirection.OUTGOING)
		this.inputSDFChannelSet = Query.findInputSDFChannels(actor)
		this.outputSDFChannelSet = Query.findOutputSDFChannels(actor)
		'''
			/* Includes-------------------------- */
			#include "../inc/datatype_definition.h"
			«var name = actor.getIdentifier()»
			/*
			========================================
				Declare Extern Channal Variables
			========================================
			*/
			
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

	def boolean isExtern(String string) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

	def String read(Vertex vertex) {
		var consumption = vertex.getProperties().get("consumption")
		if (consumption !== null) {
			var inputPortsHashMap = (consumption.unwrap() as HashMap<String, Integer>)
			'''
				«FOR port : inputPortsHashMap.keySet() SEPARATOR "" AFTER ""»
					«IF inputPortsHashMap.get(port)==1»
						read_non_blocking(&channel,&«port»);
					«ELSE»
						for(int i=0;i<«inputPortsHashMap.get(port)»;++i){
							read_non_blocking(&channel,«port»+i);
						}
					«ENDIF»
				«ENDFOR»
			'''
		} else {
			'''
			'''
		}
	}

	def String write(Vertex vertex) {
		var production = vertex.getProperties().get("production")
		if (production !== null) {
			var inputPortsHashMap = (production.unwrap() as HashMap<String, Integer>)
			'''
				«FOR port : inputPortsHashMap.keySet() SEPARATOR "" AFTER ""»
					«IF inputPortsHashMap.get(port)==1»
						write(«port»_channel);
					«ELSE»
						for(int i=0;i<«inputPortsHashMap.get(port)»;++i){
							write(«port»_channel);
						}
					«ENDIF»
				«ENDFOR»
			'''
		} else {
			'''
			'''
		}
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

		var SDFChannelSet = Query.findInputSDFChannels(actor)
		SDFChannelSet.addAll(Query.findOutputSDFChannels(actor))
		'''
			«FOR sdfchannel : SDFChannelSet SEPARATOR "" AFTER ""»
				
			«ENDFOR»
		'''
	}

}
