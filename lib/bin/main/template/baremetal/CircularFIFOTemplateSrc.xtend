package template.baremetal

import template.templateInterface.InitTemplate
import generator.Generator
import forsyde.io.java.core.VertexTrait
import java.util.stream.Collectors
import fileAnnotation.FileTypeAnno
import fileAnnotation.FileType
import java.util.Set
import java.util.HashSet
@FileTypeAnno(type=FileType.C_SOURCE)
class CircularFIFOTemplateSrc implements InitTemplate {
	private Set<VertexTrait> primitiveTraitSet

	new() {
		primitiveTraitSet = new HashSet
		primitiveTraitSet.add(VertexTrait.TYPING_DATATYPES_FLOAT)
		primitiveTraitSet.add(VertexTrait.TYPING_DATATYPES_DOUBLE)
		primitiveTraitSet.add(VertexTrait.TYPING_DATATYPES_INTEGER)
	}

	override create() {
		'''
			#include "../inc/datatype_definition.h"
			#include "../inc/circular_fifo_lib.h"
			«FOR primitiveTrait : primitiveTraitSet SEPARATOR"" AFTER""»
			«primitiveChannelDefinition(primitiveTrait)»	
			«ENDFOR»			
		'''
	}

	def primitiveChannelDefinition(VertexTrait trait) {
		'''
			«var typeVertexSet=Generator.model.vertexSet().stream().filter([v|v.hasTrait(trait)]).collect(Collectors.toSet())»
			«FOR typeVertex : typeVertexSet SEPARATOR "" AFTER ""»
				«val type = typeVertex.getIdentifier()»
				/*
				=============================================================
								«type» Channel Definition
				=============================================================
				*/				
				void init_channel_«type»(circular_channel_«type» *channel ,«type»* buffer, size_t size){
				    channel->buffer = buffer;
				    channel->size=size;
				    channel->front = 0;
				    channel->rear = 0;			
				}
			
				int read_non_blocking_«type»(circular_channel_«type» *channel, «type» *data){
					if(channel->front==channel->rear){
					    	//empty 
					    	return -1;
					    			
					   }else{
					    	*data = channel->buffer[channel->front];
					    	//printf("buffer «type»: before read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
					    	channel->front= (channel->front+1)%channel->size;
					    	//printf("buffer «type»: after read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
					    	return 0;
					    }
				}
				int read_blocking_«type»(circular_channel_«type»* channel,«type»* data,spinlock* lock){
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
			
				int write_non_blocking_«type»(circular_channel_«type»* channel, «type» value){
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
			
				int write_blocking_«type»(circular_channel_«type»* channel, «type» value,spinlock* lock){
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
					
			«ENDFOR»		
		'''
	}

	override getFileName() {
		return "circular_fifo_lib"
	}

}
