package template.rtos

import fileAnnotation.FileType

import fileAnnotation.FileTypeAnno
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import forsyde.io.java.typed.viewers.moc.sdf.SDFActor
import forsyde.io.java.typed.viewers.values.IntegerValue
import generator.Generator
import java.util.HashMap
import java.util.stream.Collectors
import template.templateInterface.InitTemplate
import utils.Query

@FileTypeAnno(type=FileType.C_SOURCE)
class StartTaskTemplateSrcRTOS implements InitTemplate {
	override savePath() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	override String create() {
		var model= Generator.model
		var integerValues = model.vertexSet().stream()
		.filter([v|IntegerValue.conforms(v)])
		.map([v|IntegerValue.safeCast(v).get()])
		.collect(Collectors.toSet())
		'''
			#include "../inc/config.h"
			#include "../inc/datatype_definition.h"
			#include "FreeRTOS.h"
			#include "semphr.h"
			#include "timers.h"	
			#include "queue.h"
			«FOR actor:model.vertexSet().stream().filter([v|SDFActor.conforms(v)]).collect(Collectors.toSet())»
			#include "../inc/sdfcomb_«actor.getIdentifier()».h"
			«ENDFOR»
			/*
			=================================================
				Define StartTask Stack
			=================================================
			*/
			StackType_t task_StartTask_stk[STARTTASK_STACKSIZE]; 
			StaticTask_t tcb_start;
			/*
			=================================================
				Declare Extern Values
			=================================================
			*/		
			«FOR value:integerValues »
			extern int «value.getIdentifier()»;
			«ENDFOR»			
			
			
			static void init_msg_queue();
			static void init_soft_timer();
			static void init_semaphore();
			static void init_actor_task();
			static void timer_start();
					
			void init_subsystem(){
				/* Initialize Message Queue     */
				init_msg_queue();
				
				/* Initialize Software Timer    */
				init_soft_timer();
				
				/* Initialize Timer's Semaphore */
				init_semaphore();
				
				/* Initialize Actor Tasks       */
				init_actor_task();
				
				/* Start Software Timer        */
				timer_start();
				
				/* Suspend All the Created Tasks */
				
				//vTaskStartScheduler();
				//vTaskDelete(NULL); 
				
			}
			static void init_msg_queue(){
				«FOR sdfchannel : Generator.sdfchannelSet SEPARATOR "" AFTER ""»
					«var sdfname = sdfchannel.getIdentifier()»
					/* channel «sdfname» */
					extern QueueHandle_t msg_queue_«sdfname»;
					extern int queue_length_«sdfname»;
					extern long item_size_«sdfname»;
					
				«ENDFOR»			
				«FOR sdfchannel : Generator.sdfchannelSet SEPARATOR "" AFTER ""»
					«var sdfname = sdfchannel.getIdentifier()»
					msg_queue_«sdfname»=xQueueCreate(queue_length_«sdfname»,item_size_«sdfname»);
				«ENDFOR»
				
			«FOR channel : Generator.sdfchannelSet»
				«var sdfchannel=SDFChannel.safeCast(channel).get()»
				«IF sdfchannel.getNumOfInitialTokens()!==null&&sdfchannel.getNumOfInitialTokens()>0»
					«var b = (sdfchannel.getProperties().get("__initialTokenValues_ordering__").unwrap() as HashMap<String,Integer>) »					
					«FOR k:b.keySet()»
					xQueueSend(msg_queue_«sdfchannel.getIdentifier()»,&«k»,portMAX_DELAY);
					«ENDFOR»
				«ENDIF»
			«ENDFOR»						
			}
			static void init_soft_timer(){
			«FOR actor : Generator.sdfcombSet SEPARATOR "" AFTER ""»
				«var name = actor.getIdentifier()»
					/* actor «name»*/
					extern TimerHandle_t task_timer_«name»;
					task_timer_«name»=xTimerCreate(
															"timer_«name»"
															, pdMS_TO_TICKS(«Query.getWCET(actor,Generator.model)»)
															, pdTRUE
															,0
															,timer_«name»_callback
															);
																		
			«ENDFOR»			
			}
			static void init_semaphore(){
			«FOR actor : Generator.sdfcombSet SEPARATOR "" AFTER ""»
				«var name = actor.getIdentifier()»
					/* actor «name»*/
					extern SemaphoreHandle_t timer_sem_«name»;
					timer_sem_«name»=xSemaphoreCreateBinary();
								
			«ENDFOR»				
			}
			static void init_actor_task(){
				«FOR actor : Generator.sdfcombSet SEPARATOR "" AFTER ""»
					«var name=actor.getIdentifier()»
					/* actor «name»*/
					extern StackType_t task_«name»_stk[];
					extern StaticTask_t tcb_«name»;
					xTaskCreateStatic(task_«name»
										,"«name»"
										,«name.toUpperCase()»_STACKSIZE
										,NULL
										,configMAX_PRIORITIES-2
										,task_«name»_stk,
										&tcb_«name»
										);	
												
				«ENDFOR»
			}
			static void timer_start(){
				«FOR actor : Generator.sdfcombSet SEPARATOR "" AFTER ""»
					«var name=actor.getIdentifier()»
					extern TimerHandle_t task_timer_«name»;
					xTimerStart(task_timer_«name», portMAX_DELAY);		
				«ENDFOR»				
			}
			
		'''
	}



}
