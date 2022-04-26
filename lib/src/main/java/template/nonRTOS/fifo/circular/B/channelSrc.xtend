package template.nonRTOS.fifo.circular.B


import forsyde.io.java.core.Vertex
import forsyde.io.java.core.ForSyDeSystemGraph
import utils.*
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import java.util.Set
import template.Template

class channelSrc implements Template{
	ForSyDeSystemGraph model
	String root
	new(ForSyDeSystemGraph model){
		this.model=model
	}
	
	override create(){
		 model.vertexSet()
			.stream()
			.filter([v|SDFChannel::conforms(v)])
			.forEach([
				v| Save.save(path(v) ,v.src())
			]
			)		
	}
	

	def String src(Vertex v) {
		Set<String> types
		'''
			
			#include "../include/channels_types.h"
			#include <stdio.h>	
			«FOR type:types SEPARATOR " \n" AFTER "\n" »
				
				void init_circularFIFO_«type»(circularFIFO_«type»* channel ,«type»* buffer,size_t size){
				  
				    channel->buffer = buffer;
				    channel->size=size;
				    channel->front = 0;
				    channel->rear = 0;			
				}
			
				int read_circularFIFO_non_blocking_«type»(circularFIFO_«type»* channel, «type»* data){
					if(channel->front==channel->rear){
					    	//empty 
					    	return -1
					    			
					   }else{
					    	*data = channel->buffer[channel->front];
					    	//printf("buffer «type»: before read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
					    	channel->front= (channel->front+1)%channel->size;
					    	//printf("buffer «type»: after read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
					    	return 0;
					    }
				}
				
				/*
					write a token to _circular «type».
				
						
				*/
				int write_circularFIFO_non_blocking_«type»(circularFIFO_«type»* channel, «type» value){
				    /*if the buffer is full*/
				    if((channel->rear+1)%channel->size == channel->front){
				        //full!
				        //discard the data
				        //printf("buffer full error\n!");
				        return -1;
				     }else{
				        channel->buffer[channel->rear] = data;
				       //printf("buffer «type»:before write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
				        channel->rear= (channel->rear+1)%channel->size;
				        //printf("buffer «type»:after write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
				        return 0;
				    }			
				
				}	
				
				int read_circularFIFO_blocking_«type»(circularFIFO_«type»* channel,«type»* data,spinlock* lock){
					spinlock_get(lock);
					if(channel->front==channel->rear){
					    	//empty 
					    	spinlock_release(lock);
					    	return -1
					    			
					   }else{
					    	*data = channel->buffer[channel->front];
					    	//printf("buffer «type»: before read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
					    	channel->front= (channel->front+1)%channel->size;
					    	//printf("buffer «type»: after read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
					    	spinlock_release(lock);
					    	return 0;
					    }
				}
			
				int write_circularFIFO_blocking_«type»(circularFIFO_«type»* channel, «type» value,spinlock* lock){
					spinlock_get(lock);
					
					   /*if the buffer is full*/
					   if((channel->rear+1)%channel->size == channel->front){
					       //full!
					       //discard the data
					       //printf("buffer full error\n!");
					       spinlock_release(lock);
					       return -1;
					    }else{
					       channel->buffer[channel->rear] = data;
					      //printf("buffer «type»:before write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
					       channel->rear= (channel->rear+1)%channel->size;
					       //printf("buffer «type»:after write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
					       spinlock_release(lock);
					       return 0;
					   }				
				}
					
			«ENDFOR»		
		'''
	}
	
	private def String path(Vertex vertex){
		return root+"/source/channels_types.c"
	}
	
}
