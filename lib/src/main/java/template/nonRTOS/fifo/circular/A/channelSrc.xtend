package template.nonRTOS.fifo.circular.A

import forsyde.io.java.core.Vertex

import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import generator.generator
import template.Template
import utils.Global
import utils.Name
import utils.Query
import utils.Save

class channelSrc implements Template{

	new(){
		
	}
	
	override create(){
		Global.model.vertexSet()
					.stream()
					.filter([v|v.hasTrait("impl::TokenizableDataBlock")])
					.forEach([v|Save.save(path(v),v.src())])
//		 Global.model.vertexSet()
//			.stream()
//			.filter([v|SDFChannel::conforms(v)])
//			.forEach([
//				v| Save.save(path(v) ,v.src())
//			]
//			)	



	}
	
	def String src(Vertex vertex) {
		var name = vertex.getIdentifier()
		
		'''
			
			#include "../inc/sdfchannel_«name.replace("/","_")».h"
			
			#include <stdio.h>
			
			volatile spinlock spinlock_«name»={.flag=0};
			«var long buffersize=Query.getBufferSize(vertex)»
		
			unsigned long buffersize_«name» = «buffersize+1»;
			volatile token_«name» arr_«name»[«buffersize+1»];
			circularFIFO_«name» channel_«name»;
			
			
			void init_circularFIFO_«name»(circularFIFO_«name»* channel ,token_«name»* buffer,size_t size){
					  
					    channel->buffer = buffer;
					    channel->size=size;
					    channel->front = 0;
					    channel->rear = 0;			
					}
			
				/* 
				read a token from channel.
				src: is channel «name»
				dst:data
				*/
			inline int read_circularFIFO_non_blocking_«name»(circularFIFO_«name»* channel, token_«name»* data){
					if(channel->front==channel->rear){
					    	//empty 
					    	return -1;
					    			
					   }else{
					    	*data = channel->buffer[channel->front];
					    	//printf("buffer «name»: before read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
					    	channel->front= (channel->front+1)%channel->size;
					    	//printf("buffer «name»: after read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
					    	return 0;
					    }
				}
				
				/*
					write a token to _circular «name».
				
						
				*/
			inline int write_circularFIFO_non_blocking_«name»(circularFIFO_«name»* channel, token_«name» value){
				    /*if the buffer is full*/
				    if((channel->rear+1)%channel->size == channel->front){
				        //full!
				        //discard the data
				        //printf("buffer full error\n!");
				        return -1;
				     }else{
				        channel->buffer[channel->rear] = value;
				       //printf("buffer «name»:before write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
				        channel->rear= (channel->rear+1)%channel->size;
				        //printf("buffer «name»:after write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
				        return 0;
				    }			
					
				}	
				
			inline	int read_circularFIFO_blocking_«name»(circularFIFO_«name»* channel, token_«name»* data,spinlock* lock){
					spinlock_get(lock);
					if(channel->front==channel->rear){
					    	//empty 
					    	spinlock_release(lock);
					    	return -1;
					    			
					   }else{
					    	*data = channel->buffer[channel->front];
					    	//printf("buffer «name»: before read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
					    	channel->front= (channel->front+1)%channel->size;
					    	//printf("buffer «name»: after read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
					    	spinlock_release(lock);
					    	return 0;
					    }
				}
			
			inline int write_circularFIFO_blocking_«name»(circularFIFO_«name»* channel, token_«name» value,spinlock* lock){
				spinlock_get(lock);
				
				   /*if the buffer is full*/
				   if((channel->rear+1)%channel->size == channel->front){
				       //full!
				       //discard the data
				       //printf("buffer full error\n!");
				       spinlock_release(lock);
				       return -1;
				    }else{
				       channel->buffer[channel->rear] = value;
				      //printf("buffer «name»:before write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
				       channel->rear= (channel->rear+1)%channel->size;
				       //printf("buffer «name»:after write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
				       spinlock_release(lock);
				       return 0;
				   }				
			}			
		'''
	}
	
	private def String path(Vertex vertex){
		return generator.root+"/src/sdfchannel_"+Name.name(vertex)+".c"
	}
	
}
