package template.RTOS.subsystem
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb
import java.util.stream.Collectors
import template.Template
import utils.Global
import utils.Name
import utils.Save
import generator.generator
import utils.Query

class StartTaskSrc implements Template {

	override create() {
		Save.save(path(),createSource())
	}

	
	def String createSource(){
		var sdfChannelSet = Global.model.vertexSet()
								.stream()
								.filter([v|SDFChannel.conforms(v)])
								.collect(Collectors.toSet())
		var sdfCombSet = Global.model.vertexSet()
								.stream()
								.filter([v|SDFComb.conforms(v)])
								.collect(Collectors.toSet())	
		var system_in_out = Global.model.vertexSet()
								.stream()
								.filter([v|v.hasTrait("impl::TokenizableDataBlock")])
								.filter([v|!v.hasTrait("moc::sdf::SDFChannel")])
								.collect(Collectors.toSet())			
		'''
			#include "../include/freertos_StartTask.h"
			«FOR channel :sdfChannelSet SEPARATOR "" AFTER"\n"  »
				#include "../include/freertos_sdfchannel_«Name.name(channel)».h"
			«ENDFOR»
			«FOR sdf :sdfCombSet SEPARATOR "" AFTER"\n"  »
				#include "../include/freertos_sdfcomb_«Name.name(sdf)».h"
			«ENDFOR»
			#include "../include/freertos_config.h"
			/*******************
			*	Task stack     *
			********************/
			StackType_t task_StartTask_stk[TASK_STACKSIZE]; 
			StaticTask_t tcb_start;
			«FOR sdf :sdfCombSet SEPARATOR "" AFTER"\n"  »
				extern StackType_t task_«Name.name(sdf)»_stk[TASK_STACKSIZE];
				extern StaticTask_t tcb_«Name.name(sdf)»;
			«ENDFOR»
			/*******************
			*	 Message Queue *
			********************/
			«FOR channel :sdfChannelSet SEPARATOR "\n" AFTER"\n"  »
				extern int queue_length_«Name.name(channel)»;
				extern long item_size_«Name.name(channel)»;
				extern QueueHandle_t msg_queue_«Name.name(channel)»;
			«ENDFOR»
			«FOR e :system_in_out SEPARATOR "" AFTER"\n"  »
				extern QueueHandle_t msg_queue_«Name.name(e)»;
			«ENDFOR»			
			
			/**************************
			*			Soft Timer and semaphore
			***************************/
			«FOR sdf :sdfCombSet SEPARATOR "" AFTER"\n"  »
				extern TimerHandle_t task_timer_«Name.name(sdf)»;
			«ENDFOR»			
			«FOR sdf :sdfCombSet SEPARATOR "" AFTER"\n"  »
				extern  SemaphoreHandle_t task_sem_«Name.name(sdf)»;
			«ENDFOR»			
			
			
			void StartTask(void* pdata){
				/*
					Message Queue creation
				*/
				«FOR channel :sdfChannelSet SEPARATOR "" AFTER"\n"  »
					«var name =Name.name(channel) »
					msg_queue_«name»=xQueueCreate(queue_length_«name»,item_size_«name»);
				«ENDFOR»	
				/*
					Soft Timer amd Semephore Initilization
				*/				
				«FOR sdf :sdfCombSet SEPARATOR "" AFTER"\n"  »
					task_timer_«Name.name(sdf)»=xTimerCreate(
															"timer_«Name.name(sdf)»"
															, pdMS_TO_TICKS(«Query.getWCET(sdf,Global.model)»)
															, pdTRUE
															,0
															,timer_«Name.name(sdf)»_callback
															);
					
					task_sem_«Name.name(sdf)»=xSemaphoreCreateBinary();
				«ENDFOR»				
				
				/*
					Tasks Initilization
				*/
				«FOR sdf :sdfCombSet SEPARATOR "\n" AFTER"\n"  »
					xTaskCreateStatic(task_«Name.name(sdf)»
										,"«Name.name(sdf)»"
										,TASK_STACKSIZE
										,NULL
										,configMAX_PRIORITIES-2
										,task_«Name.name(sdf)»_stk,
										&tcb_«Name.name(sdf)»
										);
				«ENDFOR»				
				
				/*
					Start the soft timer
				*/
				«FOR sdf :sdfCombSet SEPARATOR "" AFTER"\n"  »
					xTimerStart(task_timer_«Name.name(sdf)», portMAX_DELAY);
				«ENDFOR»					
				
				vTaskDelete(NULL); 
			}		
		'''
	}
	

	private def String path(){
		return generator.root+"/source/freertos_StartTask.c"
	}		
}
