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
					
	#endif
