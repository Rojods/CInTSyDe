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

@FileTypeAnno(type=FileType.C_SOURCE)
class SDFCombTemplateSrc implements ActorTemplate {
	Set<Vertex> implActorSet

	override create(Vertex vertex) {
		implActorSet = VertexAcessor.getMultipleNamedPort(Generator.model, vertex, "combFunctions",
			VertexTrait.IMPL_ANSICBLACKBOXEXECUTABLE, VertexPortDirection.OUTGOING)
		'''
			/* Includes-------------------------- */
			#include "../inc/datatype_definition.h"
			«var name = Name.name(vertex)»
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
				«initMemory()»
				
				/* Read From Input Port  */
				«read(vertex)»
				/* Inline Code           */
				«getInlineCode()»
			
				/* Write To Output Ports */
				«write(vertex)»
			}
		'''
	}

	def String initMemory() {
		var Set<String> variableNameRecord = new HashSet
		var String ret = ""
		for (Vertex impl : implActorSet) {

			val portToTypeNameHashMap = (impl.getProperties().get("portToTypeName").unwrap() as HashMap<String, String>)
			val inputPortList = (impl.getProperties().get("inputPorts").unwrap() as ArrayList<String>)
			val outputPortList = (impl.getProperties().get("outputPorts").unwrap() as ArrayList<String>)

			var inputPortAndoutputPortSet = new HashSet
			inputPortAndoutputPortSet.addAll(inputPortList)
			inputPortAndoutputPortSet.addAll(outputPortList)

			ret += '''
				«FOR port : inputPortAndoutputPortSet SEPARATOR "" AFTER ""»
					«IF !variableNameRecord.contains(port)»
						«portToTypeNameHashMap.get(port)»  «port»;
						«var tmp=variableNameRecord.add(port)»
					«ENDIF»
				«ENDFOR»
			'''
		}
		return ret
	}

	def String read(Vertex vertex) {
		var consumption = vertex.getProperties().get("consumption")
		if (consumption !== null) {
			var inputPortsHashMap = (consumption.unwrap() as HashMap<String, Integer>)
			'''
				«FOR port : inputPortsHashMap.keySet() SEPARATOR "" AFTER ""»
					«IF inputPortsHashMap.get(port)==1»
						read_nonblocking(«port»_channel);
					«ELSE»
						for(int i=0;i<«inputPortsHashMap.get(port)»;++i){
							read_nonblocking(«port»_channel);
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

}
