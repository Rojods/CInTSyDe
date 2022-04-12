package strategy.c.rtos

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

class SDFCombSourceStrategy_rtos extends SDFCombFather implements Strategy {
	
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
			#include "../include/rtos_sdfcomb_«name».h"
			
			/*
			* Message Queue
			*/
			«FOR e: allDataEdges SEPARATOR "" AFTER "\n"»
			extern OS_EVENT* msg_queue_«e»;
			«ENDFOR»
			
			
			/*
			* Task Stack
			*/
			OS_STK task_«name»_stk[TASK_STACKSIZE];
			
			/*
			* Soft Timer and Semaphore
			*/
			OS_EVENT* task_sem_«name»;
			OS_TMR* task_timer_«name»;
			INT8U task_sem_«name»_err=OS_NO_ERR;
			
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
					read_channel_«name»_«port»(msg_queue_«vertex.getSDFChannelName(port,model)»,«port»_rate,«port»);
					«ENDFOR»	
					/*
					*	combinator function
					*/
					func_«name»_combinator(«funcParameter(vertex,inputPorts,outputPorts)»);	
				
					/*
					*	write from channel
					*/
					«FOR port : outputPorts SEPARATOR "" AFTER "\n" »
					write_channel_«name»_«port»(«port»,«port»_rate,msg_queue_«vertex.getSDFChannelName(port,model)»);
					«ENDFOR»
									
					OSSemPend(task_sem_«name»,0,&task_sem_«name»_err);	
					switch(task_sem_«name»_err){
						case: OS_ERR_EVENT_TYPE
							break;
						case:OS_ERR_PEND_ISR
							break;
						case:OS_ERR_PEND_LOCKED
							break;
						case:OS_ERR_PEVENT_NULL
							break;
						case:OS_ERR_TIMEOUT
							break;
						default:
							break;	
						
					}
				}
				
			}
			//void func_actorName_combinator(portName[], portName_rate ....)
			void func_«name»_combinator(«funcParameterSignature(vertex,inputPorts,outputPorts)»){
				printf("in actor «name»\n");
			
			}
			
			«FOR port : inputPorts SEPARATOR " \n" AFTER "\n" »
				void read_channel_«name»_«port»(OS_EVENT *  src_msg_queue, size_t num, token_«getSDFChannelName(vertex,port,model)»  dst[]){
						
				
					INT8U err;
					for(size_t i=0 ; i < num ;++i){
						void* pmsg = OSQPend(src_msg_queue,TIME_OUT,&err);
						if(err == OS_ERR_NONE){
							dst[i]=*((token_«getSDFChannelName(vertex,port,model)»)pmsg);
						}else{
							//msg not received
						}
					}
				}
			«ENDFOR»	
			
			«FOR port : outputPorts SEPARATOR " \n" AFTER "\n" »
				void write_channel_«name»_«port»(token_«getSDFChannelName(vertex,port,model)» src[],size_t num,OS_EVENT *  dst_msg_queue){
					INT8U err;
					
					for(size_t i=0 ; i < num ;++i){
						err = OSQPost(dst_msg_queue,((void*)(src+i)));
						switch(err){
							 case: OS_ERR_Q_FULL
							 	break;							 
							 case: OS_ERR_EVENT_TYPE
							 	break;								 
							 case: OS_ERR_PEVENT_NULL
							 	break;								 
							 default:
								break;
							
					}	
				}
			«ENDFOR»
				void task_«name»_callback(void *ptmr, void *callback_arg){
					
					OSSemPost(task_sem_«name»);
				}	
		'''
	}
	
	private def String path(Vertex vertex){
		return root+"/source/rtos_sdfcomb_"+Name.name(vertex)+".c"
	}		
	
}
