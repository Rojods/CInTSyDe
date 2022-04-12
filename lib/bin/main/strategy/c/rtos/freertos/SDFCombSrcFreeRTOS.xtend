package strategy.c.rtos.freertos

import strategy.Strategy
import strategy.SDFCombFather
import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.core.Vertex
import util.Name
import java.util.stream.Collectors
import forsyde.io.java.core.EdgeTrait
import java.util.TreeSet
import static extension util.Query.getSDFChannelName
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb
import util.Save
import util.Query

class SDFCombSrcFreeRTOS extends SDFCombFather implements Strategy {
	
	new(ForSyDeSystemGraph model) {
		super(model)
	}
	
	override create() {
		var set = model.vertexSet()
			.stream()
			.filter([v|SDFComb.conforms(v)]).collect(Collectors.toSet())
			
		for(Vertex v: set){
			Save.save(path(v),v.createSource())
		}	
	}
	
	override setSaveRoot(String root) {
		this.root=root
	}
	
	def String createSource(Vertex vertex){
		
		var allDataEdges = Query.allDataEdges(vertex,model)
		

		
		var out= model.outgoingEdgesOf(vertex).stream()
									.filter([edgeinfo|edgeinfo.hasTrait(EdgeTrait.MOC_SDF_SDFDATAEDGE)])
									.map([e|e.getSourcePort().get()])
									.collect(Collectors.toSet())

		
		var in = model.incomingEdgesOf(vertex).stream()
									.filter([edgeinfo|edgeinfo.hasTrait(EdgeTrait.MOC_SDF_SDFDATAEDGE)])
									.map([e|e.getTargetPort().get()])
									.collect(Collectors.toSet())

		
		var TreeSet<String> inputPorts =new TreeSet(in)
		var TreeSet<String> outputPorts = new TreeSet(out)
		var name = Name.name(vertex)
		'''
			#include <stdlib.h>
			#include <stdio.h>
			#include "../include/freertos_sdfcomb_«name».h"
			
			/*
			* Message Queue
			*/
			«FOR e: allDataEdges SEPARATOR "" AFTER "\n"»
			extern QueueHandle_t msg_queue_«e»;
			«ENDFOR»
			
			
			/*
			* Task Stack
			*/
			StackType_t task_«name»_stk[TASK_STACKSIZE];
			StaticTask_t tcb_«name»;
			/*
			* Soft Timer and Semaphore
			*/
			SemaphoreHandle_t task_sem_«name»;
			TimerHandle_t task_timer_«name»;
			
			«FOR port : inputPorts SEPARATOR " \n" AFTER "\n" »
				inline static void read_channel_in_«port»(QueueHandle_t src_msg_queue, size_t num, token_«getSDFChannelName(vertex,port,model)»  dst[]){
					
					for(size_t i=0;i <num;++i){
						// portMAX_DELAY: INCLUDE_vTaskSuspend is set to 1 in FreeRTOSConfig.h.
						// block forever
						xQueueReceive(src_msg_queue,dst+i, portMAX_DELAY);		
					}
				}
			«ENDFOR»	
			
			«FOR port : outputPorts SEPARATOR " \n" AFTER "\n" »
				inline static void write_channel_in_«port»(token_«getSDFChannelName(vertex,port,model)» src[],size_t num,QueueHandle_t dst_msg_queue){
					
					for(size_t i=0 ; i < num ;++i){
						// portMAX_DELAY: INCLUDE_vTaskSuspend is set to 1 in FreeRTOSConfig.h.
						// block forever
						BaseType_t ret=	xQueueSend(dst_msg_queue,src+i,portMAX_DELAY);
					}
				}
			«ENDFOR»			
			void timer_«name»_callback(TimerHandle_t xTimer){
				xSemaphoreGive(task_sem_«name»);
			}				
			
			//void func_actorName_combinator(portName[], portName_rate ....)
			inline static void combinator(«funcParameterSignature(vertex,inputPorts,outputPorts)»){
				printf("in actor «name»\n");
			
			}
			
						
			void task_«name»(void* pdata){
				//array aiming to storing data from input ports
				«FOR port:inputPorts  SEPARATOR "\n" AFTER "\n"»
					long «port»_rate = «Query.getPortRate(SDFComb.enforce(vertex),port)»;
					token_«vertex.getSDFChannelName(port,model)» «port»[«port»_rate];
				«ENDFOR»
				
				//array aiming to writing data to input ports
				«FOR port :outputPorts SEPARATOR "\n" AFTER "\n"»
					long «port»_rate = «Query.getPortRate(SDFComb.enforce(vertex),port)»;
					token_«vertex.getSDFChannelName(port,model)» «port»[«port»_rate];
				«ENDFOR»
				while(1){
					/*
					*	read from channel
					*/
					«FOR port : inputPorts SEPARATOR "" AFTER "\n" »
					read_channel_in_«port»(msg_queue_«vertex.getSDFChannelName(port,model)»,«port»_rate,«port»);
					«ENDFOR»	
					/*
					*	combinator function
					*/
					combinator(«funcParameter(vertex,inputPorts,outputPorts)»);	
				
					/*
					*	write from channel
					*/
					«FOR port : outputPorts SEPARATOR "" AFTER "\n" »
					write_channel_in_«port»(«port»,«port»_rate,msg_queue_«vertex.getSDFChannelName(port,model)»);
					«ENDFOR»
									
					xSemaphoreTake(task_sem_«name», portMAX_DELAY);	
						
					
				}
				
			}

			


		'''
	}
	
	private def String path(Vertex vertex){
		return root+"/source/freertos_sdfcomb_"+Name.name(vertex)+".c"
	}		
	
}
