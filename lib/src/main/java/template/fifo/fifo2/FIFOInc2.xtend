package template.fifo.fifo2


import template.templateInterface.InitTemplate


class FIFOInc2 implements InitTemplate{
	new() {		
	}
	override savePath() {
		return "/circular_fifo_lib/circular_fifo_lib.h"
	}
	override create() {
		'''
			#ifndef CIRCULAR_FIFO_LIB_H_
			#define CIRCULAR_FIFO_LIB_H_
			
				#include "../datatype/datatype_definition.h"
				//#include "../circular_fifo_lib/spinlock.h"	
				#include <string.h>
				/*
				*******************************************************
							copy by value
				*******************************************************
				*/
				typedef struct{
					void* buffer;
					size_t front;
					size_t rear;
					size_t capacity; // the max number of token
					size_t token_size; // size of one token
					size_t count;
					
				}circular_fifo;
				
				void init(circular_fifo* fifo_ptr, void* buf, size_t token_number, size_t token_size);
				void read_fifo(circular_fifo* channel, void* dst, size_t number);
				void write_fifo(circular_fifo* channel,void* src, size_t number);	
				void PRINT(circular_fifo * fifo);			
			«««				void read_non_blocking(circular_fifo* fifo_ptr,void* dst);
«»			
				#endif
		'''
	}


}
