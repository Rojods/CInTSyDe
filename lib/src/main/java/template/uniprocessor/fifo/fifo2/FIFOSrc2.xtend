package template.uniprocessor.fifo.fifo2
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
class FIFOSrc2  implements InitTemplate {

	new() {

	}
	override savePath() {
		return "/circular_fifo_lib/circular_fifo_lib.c"
	}
	override create() {
		'''

			/*
			*******************************************************
						copy by reference
			*******************************************************
			*/

			#include "../datatype/datatype_definition.h"
			#include "../circular_fifo_lib/circular_fifo_lib.h"
			
			
			void init(circular_fifo* fifo_ptr, void** buffer, size_t size){
				
				fifo_ptr->front=0;
				fifo_ptr->rear=0;
				fifo_ptr->buffer=buffer;
				fifo_ptr->size=size;
			}
			void read_non_blocking(circular_fifo* fifo_ptr, void** dst){
				if(fifo_ptr->front==fifo_ptr->rear){
				 	//empty
				 	return;
				}else{
					*dst = fifo_ptr->buffer[fifo_ptr->front];					
					fifo_ptr->front= (fifo_ptr->front+1)%fifo_ptr->size;
					return;
				}
			}
			void write_non_blocking(circular_fifo* channel, void* src){
			    if((channel->rear+1)%channel->size == channel->front){
					//full
					return ;
				}else{
					channel->buffer[channel->rear] = src;
					channel->rear= (channel->rear+1)%channel->size;
					return;
				}					
			}		
						
		'''
	}

	



}
