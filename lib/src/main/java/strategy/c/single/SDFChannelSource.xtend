package strategy.c.single

import strategy.Strategy
import forsyde.io.java.core.Vertex
import forsyde.io.java.core.ForSyDeSystemGraph
import util.Name
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import util.Save

class SDFChannelSource implements Strategy{
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
				v| Save.save(path(v) ,v.createSource())
			]
			)		
	}
	
	override setSaveRoot(String root) {
		this.root=root
	}
	def String createSource(Vertex vertex) {
		var name = vertex.getIdentifier()
		'''
			
			#include "../include/sdfchannel_«name.replace("/","_")».h"
			#include <stdio.h>
			
			spinlock spinlock_«name»={.flag=0};
			
			/*
				init the circular buffer
			*/
			inline static struct circular_buf_«name»* circular_buf_init(token_«name» * buffer, size_t size){
			    struct circular_buf_«name» * channel = (struct circular_buf_«name» *)malloc(sizeof(struct circular_buf_«name»));
			    
			    channel->buffer = buffer;
			    channel->size=size;
			    channel->front = 0;
			    channel->rear = 0;
			    //channel->count=0;
			    return channel;
			}		
			
			/*destroy*/
			inline static void circular_buf_destroy(struct circular_buf_«name»* p){
				free(p);
			}
			
			/*
			read one token from buffer
			src: channel
			dst: data
			*/
			inline static int circular_buf_get( struct circular_buf_«name»*   channel, token_«name»* data ){
			     /*if the buffer is empty*/
			     
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
			write one token to buffer
			dst: channel
			*/
			inline static int circular_buf_put( struct circular_buf_«name»*   channel, token_«name» data ){
			    /*if the buffer is full*/
			    if((channel->rear+1)%channel->size == channel->front){
			        //full!
			        //discard the data
			        printf("buffer full error\n!");
			        return -1;
			     }else{
			        channel->buffer[channel->rear] = data;
			       //printf("buffer «name»:before write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
			        channel->rear= (channel->rear+1)%channel->size;
			        //printf("buffer «name»:after write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
			        return 0;
			    }
			}		
			
			/* 
				create a Channel
			*/
			circularFIFO_«name» init_circularFIFO_«name»(token_«name»* buffer,size_t size){
				return circular_buf_init(buffer,size);
			}
			
			
			void destroy_channel_«name»(circularFIFO_«name» channel){
				circular_buf_destroy(channel);
			}
			
			/* 
			read a token from channel.
			src: is channel «name»
			dst:data
			*/
			int read_circularFIFO_non_blocking_«name»(circularFIFO_«name» channel, token_«name»* data){
				return circular_buf_get(channel,data);
			}
			
			/*
				write a token to _circular «name».
			
					
			*/
			int write_circularFIFO_non_blocking_«name»(circularFIFO_«name» channel, token_«name» value){
				return circular_buf_put(channel,value);
			}	
			
			int read_circularFIFO_blocking_«name»(circularFIFO_«name» channel, token_«name»* data,spinlock* lock){
				int ret = 0;
				
				spinlock_get(lock);
				ret = circular_buf_get(channel,data);
				spinlock_release(lock);
				return ret;
			}
			
			/*
				write a token to _circular «name».
			
					
			*/
			int write_circularFIFO_blocking_«name»(circularFIFO_«name» channel, token_«name» value,spinlock* lock){
				int ret = 0;
				spinlock_get(lock);
				ret=circular_buf_put(channel,value);
				spinlock_release(lock);
				return ret;
			}			
		'''
	}
	
	private def String path(Vertex vertex){
		return root+"/source/sdfchannel_"+Name.name(vertex)+".c"
	}
	
}
