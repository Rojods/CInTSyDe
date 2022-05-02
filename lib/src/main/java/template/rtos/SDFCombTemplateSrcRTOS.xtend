package template.rtos

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
import forsyde.io.java.core.EdgeInfo
import java.util.stream.Collectors
import forsyde.io.java.core.EdgeTrait

@FileTypeAnno(type=FileType.C_SOURCE)
class SDFCombTemplateSrcRTOS implements ActorTemplate {
	Set<Vertex> implActorSet
	Set<Vertex> inputSDFChannelSet
	Set<Vertex> outputSDFChannelSet
	override create(Vertex actor) {
		implActorSet = VertexAcessor.getMultipleNamedPort(Generator.model, actor, "combFunctions",
			VertexTrait.IMPL_ANSICBLACKBOXEXECUTABLE, VertexPortDirection.OUTGOING)
		var name = actor.getIdentifier()
		this.inputSDFChannelSet = Query.findInputSDFChannels(Generator.model,actor)
		this.outputSDFChannelSet = Query.findOutputSDFChannels(Generator.model,actor)
		

		'''
			#include "../inc/config.h"
			/*
			==============================================
				Define Task Stack
			==============================================
			*/
			StackType_t task_«name»_stk[«name.toUpperCase()»_STACKSIZE];
			StaticTask_t tcb_«name»;
			
			/*
			==============================================
				Declare Extern Message Queue Handler
			==============================================
			*/
			/* Input Message Queue */
			«FOR sdfchannel : this.inputSDFChannelSet SEPARATOR "" AFTER ""»
				extern QueueHandle_t msg_queue_«sdfchannel.getIdentifier()»;
			«ENDFOR»
			/* Output Message Quueue */
			«FOR sdfchannel : this.outputSDFChannelSet SEPARATOR "" AFTER ""»
				«IF !inputSDFChannelSet.contains(sdfchannel)»
					extern QueueHandle_t msg_queue_«sdfchannel.getIdentifier()»;
				«ENDIF»
			«ENDFOR»
			/*
			==============================================
				Define Soft Timer and Soft Timer Semaphore
			==============================================
			*/
			
			SemaphoreHandle_t timer_sem_«name»;
			TimerHandle_t timer_«name»;
			static void timer_«name»_callback(TimerHandle_t xTimer);
			/*
			==============================================
				Define Task Function
			==============================================
			*/
			void task_«name»(void* pdata){
				/* Initilize Memory           */
				«initMemory()»
				while(1){
					/* Read From»hannel      */
					«read(actor)»
					/* Inline Code            */
					«getInlineCode()»
					/* Write To Channel       */
					«write(actor)»
					/* Pend Timer's Semaphore */	
					xSemaphoreTake(task_sem_«name», portMAX_DELAY);	
				
				}
				
				
			}
			
			/*
			=============================================
			Soft Timer Callback Function
			=============================================
			*/
			void timer_«name»_callback(TimerHandle_t xTimer){
				xSemaphoreGive(task_sem_«name»);
			}
		'''
	}

	/**
	 * initMemory is copied from initMemory in SDFCombTemplateSrc class
	 */
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
						«portToTypeNameHashMap.get(port)»  «port»;
						«var tmp=variableNameRecord.add(port)»
					«ENDIF»
				«ENDFOR»
			'''
		}
		return ret
	}

	/**
	 * copied and modified from method read in SDFCombTemplateSrc class
	 */
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

	/**
	 * copied and modified from method write in SDFCombTemplateSrc class
	 */
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

	/**
	 * This method is same as getInlineCode in SDFCombTemplateSrc class
	 */
	private def String getInlineCode() {

		'''
			«FOR impl : implActorSet SEPARATOR "" AFTER ""»
				//in combFunction «impl.getIdentifier()»
				«Query.getInlineCode(impl)»
			«ENDFOR»		
		'''

	}
}
