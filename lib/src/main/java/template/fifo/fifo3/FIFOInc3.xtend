package template.fifo.fifo3


import template.templateInterface.InitTemplate


class FIFOInc3 implements InitTemplate{
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
				#include "../circular_fifo_lib/spinlock.h"	
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
					size_t token_number; // the number of tokens in fifo
					size_t token_size; // size of one token
					
				}circular_fifo;
				
				void init(circular_fifo* fifo_ptr, void* buf, size_t token_number, size_t token_size);
				void read_non_blocking(circular_fifo* fifo_ptr,void* dst);
				void write_non_blocking(circular_fifo* fifo_ptr,void* src);	
			
					#endif
		'''
	}


}
