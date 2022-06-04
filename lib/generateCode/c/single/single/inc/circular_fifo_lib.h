#ifndef CIRCULAR_FIFO_LIB_H_
#define CIRCULAR_FIFO_LIB_H_
#include "config.h"			
#include "datatype_definition.h"
#include "spinlock.h"	
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
