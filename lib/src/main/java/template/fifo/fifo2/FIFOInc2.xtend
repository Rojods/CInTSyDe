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
					int front;
					int rear;
					int capacity; // capacity= the max number of token + 1
					int token_size; // size of one token
					int count;
					
				}circular_fifo;
				
				void init_fifo(circular_fifo* fifo_ptr, void* buf, int token_number, int token_size);
				void read_fifo(circular_fifo* channel, void* dst, int number);
				void write_fifo(circular_fifo* channel,void* src, int number);	
«««				void PRINT(circular_fifo * fifo);			
			«««				void read_non_blocking(circular_fifo* fifo_ptr,void* dst);
		
				#endif
		'''
	}


}
