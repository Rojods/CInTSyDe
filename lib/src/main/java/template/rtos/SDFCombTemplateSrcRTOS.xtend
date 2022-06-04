package template.rtos

import fileAnnotation.FileType
import fileAnnotation.FileTypeAnno
import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.core.Vertex
import forsyde.io.java.core.VertexAcessor
import forsyde.io.java.core.VertexAcessor.VertexPortDirection
import forsyde.io.java.core.VertexTrait
import forsyde.io.java.typed.viewers.moc.sdf.SDFActor
import forsyde.io.java.typed.viewers.typing.TypedOperation
import generator.Generator
import java.util.HashSet
import java.util.Set
import java.util.stream.Collectors
import template.templateInterface.ActorTemplate
import utils.Query

@FileTypeAnno(type=FileType.C_SOURCE)
class SDFCombTemplateSrcRTOS implements ActorTemplate {
	Set<Vertex> implActorSet
	Set<Vertex> inputSDFChannelSet
	Set<Vertex> outputSDFChannelSet

	override create(Vertex actor) {
		var model = Generator.model
		implActorSet = VertexAcessor.getMultipleNamedPort(Generator.model, actor, "combFunctions",
			VertexTrait.IMPL_ANSICBLACKBOXEXECUTABLE, VertexPortDirection.OUTGOING)
		var name = actor.getIdentifier()
		this.inputSDFChannelSet = Query.findInputSDFChannels(Generator.model, actor)
		this.outputSDFChannelSet = Query.findOutputSDFChannels(Generator.model, actor)
		var Set<Vertex> datablock
		datablock = Query.findAllExternalDataBlocks(model, SDFActor.safeCast(actor).get())

		'''
				#include "../inc/config.h"
				#include "../inc/datatype_definition.h"
				#include "../inc/sdfcomb_«name».h"
				#include "FreeRTOS.h"
				#include "semphr.h"
				#include "timers.h"	
				#include "queue.h"
				/*
				==============================================
				Define Task Stack
				==============================================
				*/
				#if FREERTOS==1
				StackType_t task_«name»_stk[«name.toUpperCase()»_STACKSIZE];
				StaticTask_t tcb_«name»;
				#endif
				/*
				==============================================
					Declare Extern Message Queue Handler
				==============================================
				*/
				/* Input Message Queue */
				#if FREERTOS==1
				«FOR sdfchannel : this.inputSDFChannelSet SEPARATOR "" AFTER ""»
					extern QueueHandle_t msg_queue_«sdfchannel.getIdentifier()»;
				«ENDFOR»
				/* Output Message Quueue */
				«FOR sdfchannel : this.outputSDFChannelSet SEPARATOR "" AFTER ""»
					«IF !inputSDFChannelSet.contains(sdfchannel)»
						extern QueueHandle_t msg_queue_«sdfchannel.getIdentifier()»;
					«ENDIF»
				«ENDFOR»
				#endif
				/*
				==============================================
					Define Soft Timer and Soft Timer Semaphore
				==============================================
				*/
				#if FREERTOS==1
				SemaphoreHandle_t timer_sem_«name»;
				TimerHandle_t timer_«name»;
				#endif
				/*
				==============================================
					 Task Function
				==============================================
				*/
				void task_«name»(void* pdata){
					/* Initilize Memory           */
					«initMemory(model,actor)»
					while(1){
				/*
				==============================================
					Read From SDF Channels
				==============================================	
				*/
						«read(model,actor)»
			
						#if defined(TESTING)
						«IF name=="GrayScale"»
				HAL_GPIO_WritePin(GPIOA,GPIO_PIN_5,1);
						«ELSEIF name=="getPx"»
				HAL_GPIO_WritePin(GPIOC,GPIO_PIN_9,1);
						«ELSEIF name=="Gx"»
				HAL_GPIO_WritePin(GPIOC,GPIO_PIN_8,1);
						«ELSEIF name=="Gy"»
				HAL_GPIO_WritePin(GPIOC,GPIO_PIN_6,1);
						«ELSEIF name=="Abs"»
				HAL_GPIO_WritePin(GPIOA,GPIO_PIN_5,1);
						«ENDIF»
						#endif
«««				/*
«««				==============================================
«««					Get External Datablock's locks
«««				==============================================	
«««				*/					
«««						«IF datablock.size()!=0»
«««							«FOR data:datablock»
«««								#if «data.getIdentifier().toUpperCase()»_BLOCKING==1
«««								extern SemaphoreHandle_t datablock_sem_«data.getIdentifier()»;
«««								xSemaphoreTake(datablock_sem_«data.getIdentifier()», portMAX_DELAY);
«««								#endif
«««							«ENDFOR»
«««						«ENDIF»		
						
				/*
				==============================================
					Inline Code
				==============================================	
				*/
						«getInlineCode()»
						
						«IF name=="GrayScale"»
							HAL_Delay(1000);
							HAL_GPIO_WritePin(GPIOA,GPIO_PIN_5,0);
						«ELSEIF name=="getPx"»
							HAL_Delay(1000);
							HAL_GPIO_WritePin(GPIOC,GPIO_PIN_9,0);					
						«ELSEIF name=="Gx"»
							HAL_Delay(1000);
							HAL_GPIO_WritePin(GPIOC,GPIO_PIN_8,0);	
						«ELSEIF name=="Gy"»
							HAL_Delay(1000);
							HAL_GPIO_WritePin(GPIOC,GPIO_PIN_6,0);		
						«ELSEIF name=="Abs"»	
							HAL_Delay(1000);
							HAL_GPIO_WritePin(GPIOA,GPIO_PIN_5,0);
						«ENDIF»					
						
						
				/*
				==============================================
					Write To SDF Channels
				==============================================	
				*/
						«write(actor)»
						
				/*
«««				==============================================
«««					Release External Datablock's locks
«««				==============================================	
«««				*/	
«««						«FOR data : datablock»
«««							#if «data.getIdentifier().toUpperCase()»_BLOCKING==1
«««							xSemaphoreGive(datablock_sem_«data.getIdentifier()»);
«««							#endif
«««						«ENDFOR»
«««				/*
				==============================================
					Pend Timer's Semaphore
				==============================================	
				*/	
						xSemaphoreTake(timer_sem_«name», portMAX_DELAY);	
					
					}
					
					
				}
				
				/*
				=============================================
				Soft Timer Callback Function
				=============================================
				*/
				#if FREERTOS==1
				void timer_«name»_callback(TimerHandle_t xTimer){
					xSemaphoreGive(timer_sem_«name»);
				}
				#endif
		'''
	}

	/**
	 * initMemory is copied from initMemory in SDFCombTemplateSrc class
	 */
	def String initMemory(ForSyDeSystemGraph model, Vertex actor) {

		var impls = Query.findCombFuntionVertex(model, actor)
		var Set<String> variableNameRecord = new HashSet
		var String ret = ""

		for (String impl : impls) {
			var actorimpl = model.queryVertex(impl).get()
			var Set<String> ports = new HashSet
			ports.addAll(Query.findImplInputPorts(actorimpl))
			ports.addAll(Query.findImplOutputPorts(actorimpl))
			for (String port : ports) {
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
		}
		return ret
	}

	/**
	 * copied and modified from method read in SDFCombTemplateSrc class
	 */
	def String read(ForSyDeSystemGraph model, Vertex actor) {
		var Set<Vertex> impls = SDFActor.safeCast(actor).get().getCombFunctionsPort(model).stream().map([ e |
			e.getViewedVertex()
		]).collect(Collectors.toSet())
		var Set<String> variableNameRecord = new HashSet
		var String ret = ""
		for (Vertex impl : impls) {

			var inputPorts = TypedOperation.safeCast(impl).get().getInputPorts()
			for (String port : inputPorts) {
				if (!variableNameRecord.contains(port) && Query.isSystemChannel(model, impl, port) === null) {
					// var datatype = Query.findImplPortDataType(model, impl, port)
					var actorPortName = Query.findActorPortConnectedToImplInputPort(model, actor, impl, port)
					var sdfchannelName = Query.findInputSDFChannelConnectedToActorPort(model, actor, actorPortName)
					var datatype = Query.findSDFChannelDataType(model, model.queryVertex(sdfchannelName).get())
					// println(actor.getIdentifier()+" -->   "+actorPortName)
					// println(actor)
					var consumption = SDFActor.safeCast(actor).get().getConsumption().get(actorPortName)

					if (consumption == 1) {
						ret += '''
							#if FREERTOS==1
							xQueueReceive(msg_queue_«sdfchannelName»,&«port»,portMAX_DELAY);
							#endif
						'''
					} else {
						ret += '''
							for(int i=0;i<«consumption»;++i){
								#if FREERTOS==1
								xQueueReceive(msg_queue_«sdfchannelName»,&«port»[i],portMAX_DELAY);
								#endif
							}
						'''
					}
					variableNameRecord.add(port)
				}
			}

		}
		return ret;
	}

	/**
	 * copied and modified from method write in SDFCombTemplateSrc class
	 */
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
					// var datatype = Query.findImplPortDataType(model, actorimpl, outport)
					var actorPortName = Query.findActorPortConnectedToImplOutputPort(model, actor, actorimpl, outport)
					var sdfchannelName = Query.findOutputSDFChannelConnectedToActorPort(model, actor, actorPortName)
					var datatype = Query.findSDFChannelDataType(model, model.queryVertex(sdfchannelName).get())
					try {
						var production = SDFActor.enforce(actor).getProduction().get(actorPortName)
						if (production == 1) {
							ret += '''
								#if FREERTOS==1
								xQueueSend(msg_queue_«sdfchannelName»,&«outport»,portMAX_DELAY);
								#endif
							'''
						} else {
							ret += '''
								for(int i=0;i<«production»;++i){
									#if FREERTOS==1
									xQueueSend(msg_queue_«sdfchannelName»,«outport»+i,portMAX_DELAY);
									#endif
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
