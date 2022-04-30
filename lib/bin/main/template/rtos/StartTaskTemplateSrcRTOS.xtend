package template.rtos

import template.templateInterface.InitTemplate
import fileAnnotation.*
import generator.Generator
import utils.Query

@FileTypeAnno(type=FileType.C_SOURCE)
class StartTaskTemplateSrcRTOS implements InitTemplate {
	override String create() {
		'''
			#include "../inc/config.h"
			/*
			=================================================
				Define Task Stack
			=================================================
			*/
			StackType_t task_StartTask_stk[TASK_STACKSIZE]; 
			StaticTask_t tcb_start;
			/*
			=================================================
				Declare Extern Task Stack
			=================================================
			*/		
			/*
			=================================================
				Declare Extern Message Queue
			=================================================
			*/	
			/*
			=================================================
				Declare Extern Software Timer and Semaphore
			=================================================
			*/	
			void init_msg_queue();
			void init_soft_timer();
			void init_semaphore();
			void init_actor_task();		
			void timer_start(){
				/* Initilize Message Queue     */
				init_msg_queue();
				
				/* Initilize Software Timer    */
				init_soft_timer();
				
				/* Initilize Timer's Semaphore */
				init_semaphore();
				
				/* Initilize Actor Tasks       */
				init_actor_task();
				
				/* Start Software Timer        */
				timer_start();
				
				/* Delete start task           */
				vTaskDelete(NULL); 
				
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
					task_sem_«name»=xSemaphoreCreateBinary();
								
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
					xTimerStart(task_timer_«name», portMAX_DELAY);		
				«ENDFOR»				
			}
			
		'''
	}

	override getFileName() {
		return "start_task"
	}

}
