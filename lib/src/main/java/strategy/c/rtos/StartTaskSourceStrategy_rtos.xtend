package strategy.c.rtos
import strategy.Strategy


import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import java.util.stream.Collectors
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb
import util.Name
import util.Save
import util.Query
class StartTaskSourceStrategy_rtos implements Strategy {
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
			#include "../include/StartTask.h"
			/*******************
			*	Task stack     *
			********************/
			OS_STK task_StartTask_stk[TASK_STACKSIZE]; 
			
			«FOR sdf :sdfCombSet SEPARATOR "" AFTER"\n"  »
			extern OS_STK task_«Name.name(sdf)»_stk[TASK_STACKSIZE];
			«ENDFOR»
			/*******************
			*	 Message Queue *
			********************/
			«FOR channel :sdfChannelSet SEPARATOR "\n" AFTER"\n"  »
			extern void* msg_arr_«Name.name(channel)»[];
			extern int buffersize_«Name.name(channel)»;
			extern OS_EVENT* msg_queue_«Name.name(channel)»;
			«ENDFOR»
			
			
			/**************************
			*			Soft Timer ans semaphore
			***************************/
			«FOR sdf :sdfCombSet SEPARATOR "" AFTER"\n"  »
			extern OS_TMR* task_timer_«Name.name(sdf)»;
			«ENDFOR»			
			«FOR sdf :sdfCombSet SEPARATOR "" AFTER"\n"  »
			extern OS_EVENT* task_sem_«Name.name(sdf)»;
			«ENDFOR»			
			
			
			void StartTask(void* pdata){
				/*
					Message Queue Initilization
				*/
				«FOR channel :sdfChannelSet SEPARATOR "" AFTER"\n"  »
				msg_queue_«Name.name(channel)»=OSQCreate(&msg_arr_«Name.name(channel)»[0],buffersize_«Name.name(channel)»);
				«ENDFOR»	
				/*
					Soft Timer amd Semephore Initilization
				*/				
				«FOR sdf :sdfCombSet SEPARATOR "" AFTER"\n"  »
				task_timer_«Name.name(sdf)»=OSTmrCreate(
											0,
											«Query.getWCET(sdf,model)»,
											OS_TMR_OPT_PERIODIC,
											task_«Name.name(sdf)»_callback,
											NULL,
											NULL,
											NULL);
				
				task_sem_«Name.name(sdf)»=OSSemCreate(0);
											
				«ENDFOR»				
				
				/*
					Tasks Initilization
				*/
				«FOR sdf :sdfCombSet SEPARATOR "\n" AFTER"\n"  »
				OSTaskCreate(task_«Name.name(sdf)»,//task
							NULL,//pdata
							(void*)&task_«Name.name(sdf)»_stk[TASK_STACKSIZE-1],	//ptos
							«Query.getFiringSlot(SDFComb.enforce(sdf))»+5,			//prio
							«Query.getFiringSlot(SDFComb.enforce(sdf))»+5,			//id
							(void*)&task_«Name.name(sdf)»_stk[0], //pbos
							TASK_STACKSIZE,	//stk_size
							NULL, //pnext
							OS_TASK_OPT_STK_CHK //opt							
				);
				«ENDFOR»				
				
				/*
					Start the soft timer
				*/
				«FOR sdf :sdfCombSet SEPARATOR "" AFTER"\n"  »
				OSTmrStart(task_timer_«Name.name(sdf)»,NULL);
				«ENDFOR»					
				
				OSTaskDel( OS_PRIO_SELF);
			}		
		'''
	}
	

	private def String path(){
		return root+"/source/StartTask.c"
	}		
}