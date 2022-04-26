package template.RTOS.actor

import forsyde.io.java.core.EdgeTrait



import forsyde.io.java.core.Vertex
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb
import generator.generator
import java.util.TreeSet
import java.util.stream.Collectors
import template.Template
import template.nonRTOS.actor.actorhelp
import utils.Global
import utils.Name
import utils.Query
import utils.Save

import static extension utils.Query.getSDFChannelName
import static extension utils.Query.getChannelName
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel

class actorSrc  implements Template {

	
	override create() {
		var set = Global.model.vertexSet()
			.stream()
			.filter([v|SDFComb.conforms(v)]).collect(Collectors.toSet())
			
		for(Vertex v: set){
			Save.save(path(v),v.createSource())
		}	
	}
	

	
	def String createSource(Vertex vertex){
		
//		var allDataEdges = Query.allDataEdges(vertex,Global.model)
		

		
		var out= Global.model.outgoingEdgesOf(vertex).stream()
									.filter([
										edgeinfo|edgeinfo.hasTrait(EdgeTrait.MOC_SDF_SDFDATAEDGE) 
											||	edgeinfo.hasTrait(EdgeTrait.IMPL_DATAMOVEMENT)
									])
									.map([e|e.getSourcePort().get()])
									.collect(Collectors.toSet())
		
		
		var in = Global.model.incomingEdgesOf(vertex).stream()
									.filter([
										edgeinfo|edgeinfo.hasTrait(EdgeTrait.MOC_SDF_SDFDATAEDGE)
										||	edgeinfo.hasTrait(EdgeTrait.IMPL_DATAMOVEMENT)
									]
											
									)
									.map([e|e.getTargetPort().get()])
									.collect(Collectors.toSet())	

		
		var TreeSet<String> inputPorts =new TreeSet(in)
		var TreeSet<String> outputPorts = new TreeSet(out)
		var name = Name.name(vertex)
		var sdfchannels=Global.model.vertexSet()
							 .stream()
							 .filter([v|SDFChannel::conforms(v)])
							 .map([v|SDFChannel::enforce(v)])
							 .collect(Collectors.toSet())	
		'''
			#include <stdlib.h>
			#include <stdio.h>
			#include "../include/freertos_sdfcomb_«name».h"
			#include "../include/freertos_config.h"
			#include "FreeRTOS.h"
			#include "task.h"
			#include "semphr.h"
			#include "timers.h"
			«FOR ch:sdfchannels SEPARATOR " \n" AFTER "\n" »
				#include "../include/freertos_sdfchannel_«Name.name(ch)».h"
			«ENDFOR»			
			/*
			* Message Queue
			*/
			«FOR port : inputPorts SEPARATOR "" AFTER "" »
			extern QueueHandle_t msg_queue_«Query.getChannelName(vertex,port,Global.model)»;
			«ENDFOR»			
			«FOR port : outputPorts SEPARATOR "" AFTER "" »
			extern QueueHandle_t msg_queue_«Query.getChannelName(vertex,port,Global.model)»;
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
				inline static void read_channel_in_«port»(QueueHandle_t src_msg_queue, size_t num, token_«getSDFChannelName(vertex,port,Global.model)»  dst[]){
					
					for(size_t i=0;i <num;++i){
						// portMAX_DELAY: INCLUDE_vTaskSuspend is set to 1 in FreeRTOSConfig.h.
						// block forever
						xQueueReceive(src_msg_queue,dst+i, portMAX_DELAY);		
					}
				}
			«ENDFOR»	
			
			«FOR port : outputPorts SEPARATOR " \n" AFTER "\n" »
				inline static void write_channel_in_«port»(token_«getSDFChannelName(vertex,port,Global.model)» src[],size_t num,QueueHandle_t dst_msg_queue){
					
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
			inline static void combinator(«actorhelp.funcParameterSignature(vertex,inputPorts,outputPorts)»){
				printf("in actor «name»\n");
			
			}
			
						
			void task_«name»(void* pdata){
				//array aiming to storing data from input ports
				«FOR port:inputPorts  SEPARATOR "\n" AFTER "\n"»
					long «port»_rate = «Query.getPortRate(SDFComb.enforce(vertex),port)»;
					token_«vertex.getChannelName(port,Global.model)» «port»[«port»_rate];
				«ENDFOR»
				
				//array aiming to writing data to input ports
				«FOR port :outputPorts SEPARATOR "\n" AFTER "\n"»
					long «port»_rate = «Query.getPortRate(SDFComb.enforce(vertex),port)»;
					token_«vertex.getSDFChannelName(port,Global.model)» «port»[«port»_rate];
				«ENDFOR»
				while(1){
					/*
					*	read from channel
					*/
					«FOR port : inputPorts SEPARATOR "" AFTER "" »
						read_channel_in_«port»(msg_queue_«vertex.getChannelName(port,Global.model)»,«port»_rate,«port»);
					«ENDFOR»	
					/*
					*	combinator function
					*/
					combinator(«actorhelp.funcParameter(vertex,inputPorts,outputPorts)»);	
				
					/*
					*	write from channel
					*/
					«FOR port : outputPorts SEPARATOR "" AFTER "" »
						write_channel_in_«port»(«port»,«port»_rate,msg_queue_«vertex.getSDFChannelName(port,Global.model)»);
					«ENDFOR»
					
					xSemaphoreTake(task_sem_«name», portMAX_DELAY);	
						
					
				}
				
			}
			
			
			
			
		'''
	}
	
	private def String path(Vertex vertex){
		return generator.root+"/source/freertos_sdfcomb_"+Name.name(vertex)+".c"
	}		

		
}
