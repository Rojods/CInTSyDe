package strategy.c.rtos.freertos
import strategy.Strategy


import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import java.util.stream.Collectors
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb
import util.Name
import util.Save
import util.Query
class StartTaskSrcFreeRTOS implements Strategy {
	String root
	ForSyDeSystemGraph model	
	new(ForSyDeSystemGraph model) {
		this.model=model
	}
	
	override create() {
		Save.save(path(),createSource())
	}
	override setSaveRoot(String root) {
		this.root=root
	}
	
	def String createSource(){
		var sdfChannelSet = model.vertexSet()
								.stream()
								.filter([v|SDFChannel.conforms(v)])
								.collect(Collectors.toSet())
		var sdfCombSet = model.vertexSet()
								.stream()
								.filter([v|SDFComb.conforms(v)])
								.collect(Collectors.toSet())		
		'''
			#include "../include/freertos_StartTask.h"
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
														, pdMS_TO_TICKS(«Query.getWCET(sdf,model)»)
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
		return root+"/source/freertos_StartTask.c"
	}		
}