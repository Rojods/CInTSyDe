package strategy.c.rtos

import strategy.Strategy

import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import java.util.stream.Collectors
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb
import util.Name
import util.Save
import util.Query

class MainStrategy_rtos implements Strategy {
	String root
	ForSyDeSystemGraph model	
	new(ForSyDeSystemGraph model) {
		this.model=model
	}	
	override create() {
		Save.save(path(),main())
	}
	
	override setSaveRoot(String root) {
		this.root=root
	}
	
	def String main(){
		var sdfChannelSet = model.vertexSet()
								.stream()
								.filter([v|SDFChannel.conforms(v)])
								.collect(Collectors.toSet())
		var sdfCombSet = model.vertexSet()
								.stream()
								.filter([v|SDFComb.conforms(v)])
								.collect(Collectors.toSet())		
		'''
			
			#include "./include/StartTask.h"
			#include "./include/config.h";
			/*******************
			*	StartTask stack*
			********************/
			extern OS_STK task_StartTask_stk[TASK_STACKSIZE]; 

			
			int main(){
				OSInit();


				OSTaskCreateExt(StartTask,
							NULL,
							(void*)&task_StartTask_stk[TASK_STACKSIZE-1],	//ptos
							4,												//prio
							4,												//id
							(void*)&task_StartTask_stk[0],                  //pbos
							TASK_STACKSIZE									//stk_size
							NULL,											//pnext
							OS_TASK_OPT_STK_CHK								//opt
				);
				
				
				OSStart();
				
				return 0;
			}		
		'''
	}
	

	private def String path(){
		return root+"/main.c"
	}		
}