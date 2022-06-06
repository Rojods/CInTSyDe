package template.uniprocessor.fifo.fifo1

import template.templateInterface.InitTemplate

import generator.Generator
import forsyde.io.java.core.VertexTrait
import java.util.stream.Collectors
import fileAnnotation.FileTypeAnno
import fileAnnotation.FileType
import java.util.Set
import java.util.HashSet
import utils.Query
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import forsyde.io.java.core.Vertex

@FileTypeAnno(type=FileType.C_SOURCE)
class FIFOSrc1 implements InitTemplate {
	Set<Vertex> typeVertexSet
	new() {
		val model = Generator.model
		typeVertexSet=model.vertexSet().stream()
			.filter([v|SDFChannel.conforms(v)])
			.map([v|Query.findSDFChannelDataType(model,v)])
			.map([s|Query.findVertexByName(model,s)])
			.collect(Collectors.toSet())
		if(typeVertexSet.contains(null)){
			typeVertexSet.remove(null)
		}	
	}
	override savePath() {
		return "/circular_fifo_lib/circular_fifo_lib.c"
	}
	override create() {
		'''

			
			/*
			*******************************************************
				This file contains the function definition for 
				token types: «FOR typeVertex : typeVertexSet SEPARATOR ", " AFTER ""»«typeVertex.getIdentifier()»«ENDFOR»
				For each token type, there are five functions:
				init_channel_typeName(...)
				read_non_blocking_typeName(...)
				read_blocking_typeName(...)
				write_non_blocking_typeName(...)
				write_blocking_typeName(...)
			*******************************************************
			*/
			#include "../datatype/datatype_definition.h"
			#include "circular_fifo_lib.h"
			#include <string.h>
			
	
			«FOR typeVertex : typeVertexSet SEPARATOR "" AFTER ""»
				«produce(typeVertex )»	
			«ENDFOR»
			
		'''
	}

	def produce(Vertex typeVertex ) {
		
		'''
			
			«val type = typeVertex.getIdentifier()»
			«IF ! typeVertex.hasTrait(VertexTrait.TYPING_DATATYPES_ARRAY)»
				/*
				=============================================================
								«type» Channel Definition
				=============================================================
				*/				
				void init_channel_«type»(circular_fifo_«type» *channel ,«type»* buffer, size_t size){
				    channel->buffer = buffer;
				    channel->size=size;
				    channel->front = 0;
				    channel->rear = 0;			
				}
				
						int read_non_blocking_«type»(circular_fifo_«type» *channel, «type» *data){
							if(channel->front==channel->rear){
							    	//empty 
							    	return -1;
							    			
							   }else{
							    	*data = channel->buffer[channel->front];
							    	channel->front= (channel->front+1)%channel->size;
							    	return 0;
							    }
						}
						int read_blocking_«type»(circular_fifo_«type»* channel,«type»* data,spinlock* lock){
							spinlock_get(lock);
							if(channel->front==channel->rear){
							    	//empty 
							    	spinlock_release(lock);
							    	return -1;
							    			
							   }else{
							    	*data = channel->buffer[channel->front];
							    	//printf("buffer «type»: before read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
							    	channel->front= (channel->front+1)%channel->size;
							    	//printf("buffer «type»: after read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
							    	spinlock_release(lock);
							    	return 0;
							    }
						}				
				
						int write_non_blocking_«type»(circular_fifo_«type»* channel, «type» value){
						    /*if the buffer is full*/
						    if((channel->rear+1)%channel->size == channel->front){
						        //full!
						        //discard the data
						        //printf("buffer full error\n!");
						        return -1;
						     }else{
						        channel->buffer[channel->rear] = value;
						       //printf("buffer «type»:before write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
						        channel->rear= (channel->rear+1)%channel->size;
						        //printf("buffer «type»:after write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
						        return 0;
						    }			
						
						}	
				
						int write_blocking_«type»(circular_fifo_«type»* channel, «type» value,spinlock* lock){
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
							      //printf("buffer «type»:before write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
							       channel->rear= (channel->rear+1)%channel->size;
							       //printf("buffer «type»:after write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
							       spinlock_release(lock);
							       return 0;
							   }				
						}
				«ELSEIF isOneDimension(typeVertex)&&Query.getMaximumElems(typeVertex)>0»
				/*
				=============================================================
								«type» Channel Definition
				=============================================================
				*/				
				void init_channel_«type»(circular_fifo_«type» *channel ,«type»* buffer, size_t size){
				    channel->buffer = buffer;
				    channel->size=size;
				    channel->front = 0;
				    channel->rear = 0;			
				}
				
							int read_non_blocking_«type»(circular_fifo_«type» *channel, «type» *data){
								if(channel->front==channel->rear){
								    	//empty 
								    	return -1;
								    			
								   }else{
								     	for(int i=0;i < «Query.getMaximumElems(typeVertex)»; ++i){
								     		(*data)[i]=channel->buffer[channel->front][i];
								     	}
								    	channel->front= (channel->front+1)%channel->size;
								    	return 0;
								    }
							}
							int read_blocking_«type»(circular_fifo_«type»* channel,«type»* data,spinlock* lock){
								spinlock_get(lock);
								if(channel->front==channel->rear){
								    	//empty 
								    	spinlock_release(lock);
								    	return -1;
								    			
								   }else{
								     	for(int i=0;i < «Query.getMaximumElems(typeVertex)»; ++i){
								     		(*data)[i]=channel->buffer[channel->front][i];
								     	}
								    	//printf("buffer «type»: before read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
								    	channel->front= (channel->front+1)%channel->size;
								    	//printf("buffer «type»: after read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
								    	spinlock_release(lock);
								    	return 0;
								    }
							}				
				
							int write_non_blocking_«type»(circular_fifo_«type»* channel, «type» value){
							    /*if the buffer is full*/
							    if((channel->rear+1)%channel->size == channel->front){
							        //full!
							        //discard the data
							        //printf("buffer full error\n!");
							        return -1;
							     }else{
							     	for(int i=0;i < «Query.getMaximumElems(typeVertex)»; ++i){
							     		channel->buffer[channel->rear][i] = value[i];
							     	}
							     	  
							     	 //printf("buffer «type»:before write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
							     	  channel->rear= (channel->rear+1)%channel->size;
							     	  //printf("buffer «type»:after write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
							     	  return 0;
							    }			
							
							}	
				
							int write_blocking_«type»(circular_fifo_«type»* channel, «type» value,spinlock* lock){
								spinlock_get(lock);
								
								   /*if the buffer is full*/
								   if((channel->rear+1)%channel->size == channel->front){
								       //full!
								       //discard the data
								       //printf("buffer full error\n!");
								       spinlock_release(lock);
								       return -1;
								    }else{
								     	for(int i=0;i < «Query.getMaximumElems(typeVertex)»; ++i){
								     		channel->buffer[channel->rear][i] = value[i];
								     	}
								     	//printf("buffer «type»:before write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
								     	 channel->rear= (channel->rear+1)%channel->size;
								     	 //printf("buffer «type»:after write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
								     	 spinlock_release(lock);
								     	 return 0;
								   }				
							}
					«ELSEIF isOneDimension(typeVertex)&&Query.getMaximumElems(typeVertex)<0»
				/*
				=============================================================
								«type» Channel Definition
				=============================================================
				*/				
				void init_channel_«type»(circular_fifo_«type» *channel ,«type»* buffer, size_t size){
				    channel->buffer = buffer;
				    channel->size=size;
				    channel->front = 0;
				    channel->rear = 0;			
				}
				
								int read_non_blocking_«type»(circular_fifo_«type» *channel, «type» *data){
									if(channel->front==channel->rear){
									    	//empty 
									    	return -1;
									    			
									   }else{
									    	*data = channel->buffer[channel->front];
									    	channel->front= (channel->front+1)%channel->size;
									    	return 0;
									    }
								}
								int read_blocking_«type»(circular_fifo_«type»* channel,«type»* data,spinlock* lock){
									spinlock_get(lock);
									if(channel->front==channel->rear){
									    	//empty 
									    	spinlock_release(lock);
									    	return -1;
									    			
									   }else{
									    	*data = channel->buffer[channel->front];
									    	//printf("buffer «type»: before read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
									    	channel->front= (channel->front+1)%channel->size;
									    	//printf("buffer «type»: after read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
									    	spinlock_release(lock);
									    	return 0;
									    }
								}				
				
								int write_non_blocking_«type»(circular_fifo_«type»* channel, «type» value){
								    /*if the buffer is full*/
								    if((channel->rear+1)%channel->size == channel->front){
								        //full!
								        //discard the data
								        return -1;
								     }else{
								        channel->buffer[channel->rear] = value;
								       //printf("buffer «type»:before write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
								        channel->rear= (channel->rear+1)%channel->size;
								        //printf("buffer «type»:after write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
								        return 0;
								    }			
								
								}	
				
								int write_blocking_«type»(circular_fifo_«type»* channel, «type» value,spinlock* lock){
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
									      //printf("buffer «type»:before write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
									       channel->rear= (channel->rear+1)%channel->size;
									       //printf("buffer «type»:after write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
									       spinlock_release(lock);
									       return 0;
									   }				
								}		
						«ENDIF»
			
		'''
	}


	def isOneDimension(Vertex v){
		var inner =Query.getInnerType(Generator.model,v)
		var innerVertex = Query.findVertexByName(Generator.model,inner)
		if(innerVertex.hasTrait(VertexTrait.TYPING_DATATYPES_ARRAY)){
			return false
		}else{
			return true
		}
		
	}

}
