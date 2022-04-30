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
			var implPortSet = new HashSet<String>(impl.getPorts())
			implPortSet.remove("portTypes")

			var HashMap<String, String> portToTypeNameHashMap = new HashMap
			var dataDefinitionEdgeInfoSet = Generator.model.outgoingEdgesOf(impl).stream().filter([ edgeInfo |
				edgeInfo.hasTrait(EdgeTrait.TYPING_DATATYPES_DATADEFINITION)
			]).collect(Collectors.toSet())
			for (EdgeInfo e : dataDefinitionEdgeInfoSet) {
				if (e.getSource() != "portTypes") {
					portToTypeNameHashMap.put(e.getSourcePort().orElse(""), e.getTarget())
				}
			}

			ret += '''
				«FOR port : implPortSet SEPARATOR "" AFTER ""»
					«IF !variableNameRecord.contains(port)»
				«««						«IF isExtern(port)»
						«portToTypeNameHashMap.get(port)»  «port»;
				«««						«ELSE»
«««						«ENDIF»
						«var tmp=variableNameRecord.add(port)»
					«ENDIF»
				«ENDFOR»
			'''
		}
		return ret
	}

	def boolean isExtern(String string) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

	def String read(Vertex vertex) {
		
//		var String ret = ""
////		var inputFunctionPortsAndChannelNameHashMap = new HashMap<String,String>
////		var inputFunctionPortsAndConsumptionHashMap = new HashMap<String,Integer>
//		var record = new HashSet<String>
//		for (Vertex impl : implActorSet) {
//			var inputPortsProperty = (impl.getProperties().get("inputPorts").unwrap() as ArrayList<String>)
//			for (String implPort : inputPortsProperty) {
//
//				if (!record.contains(implPort)) {
//					record.add(implPort)
//					println(impl.getIdentifier() + " --->  " + implPort)
//					Generator.model.incomingEdgesOf(impl).stream().forEach([e|println(e)])
//					var edgeinfo = Generator.model.incomingEdgesOf(impl).stream().filter([ e |
//						
//						e.getTargetPort().isPresent() && e.getTargetPort().get() == implPort
//					]).findAny().orElse(null)
//						
//					if (edgeinfo !== null) {
//						val actorPortName = edgeinfo.getSourcePort().orElse("")
//						var consumptionProperty = vertex.getProperties().get("consumption")
//						var int consumption = 0
//						if (consumptionProperty !== null) {
//							var consumptionPropertyHashMap = (consumptionProperty.unwrap() as HashMap<String, Integer>)
//							if (consumptionPropertyHashMap.keySet().contains(actorPortName)) {
//								consumption = consumptionPropertyHashMap.get(actorPortName)
//							} else {
//								consumption = 1
//							}
//
//						}
//					println("in vertex =======================" + vertex.getIdentifier())
//					println("impl port "+implPort)
//					Generator.model.incomingEdgesOf(vertex).stream().filter([ e |
//							e.hasTrait(EdgeTrait.MOC_SDF_SDFDATAEDGE) || e.hasTrait(EdgeTrait.IMPL_DATAMOVEMENT)
//						]).filter([e|e.getTargetPort().isPresent() && e.getTargetPort().get() == actorPortName]).forEach(e|println(e))
//						var channelEdgeInfo = Generator.model.incomingEdgesOf(vertex).stream().filter([ e |
//							e.hasTrait(EdgeTrait.MOC_SDF_SDFDATAEDGE) || e.hasTrait(EdgeTrait.IMPL_DATAMOVEMENT)
//						]).filter([e|e.getTargetPort().isPresent() && e.getTargetPort().get() == actorPortName]).
//							findAny()
//						if (channelEdgeInfo.isPresent()) {
//							var channelName = channelEdgeInfo.get().getSource()
//							ret += '''
//								«IF consumption==1»
//									read_non_blocking(&«channelName»,&«implPort»);
//								«ELSE»
//									for(int i=0;i<«consumption»;++i){
//										read_non_blocking(&«channelName»,«implPort»+i);
//									}
//								«ENDIF»					
//							'''
//						} else {
//							ret+=
//							'''
//							«implPort» Not conenced to Channel 
//							'''
//						}
//
//					} else {
//						var channelName = "Not found Channel!"
//						ret += '''
//							read_non_blocking (Not found Channel!, «implPort»)
//						'''
//					}
//				}
//
//			}
//
//		}
//		return ret
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
	
	private def String getExternSDFChannel(Vertex actor){
	
	 	var SDFChannelNameSet=Query.findInputSDFChannelName(actor)
	 	SDFChannelNameSet.addAll(Query.findOutputSDFChannelName(actor))   		
		'''
		«FOR sdfchannelName:SDFChannelNameSet SEPARATOR""AFTER""»
		
		«ENDFOR»
		'''
	}

}
