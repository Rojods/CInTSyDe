/*
*******************************************************
			copy by value
*******************************************************
*/

#include "../datatype/datatype_definition.h"
#include "../circular_fifo_lib/circular_fifo_lib.h"
#include <string.h>
#include <stdio.h>

void init(circular_fifo* fifo_ptr, void* buf, size_t capacity, size_t token_size){
	fifo_ptr->buffer=buf;
	
	fifo_ptr->front=0;
	fifo_ptr->rear=0;
	fifo_ptr->capacity=capacity;
	fifo_ptr->token_size=token_size;
	fifo_ptr->count=0;
}

void read_fifo(circular_fifo* channel, void* dst, size_t number){
	while(channel->count< number);
	
	char* memcpy_dst,*memcpy_src;
	for(int i=0; i<number;++i){
		memcpy_dst=(char*)dst+i*channel->token_size;
		memcpy_src=((char*)channel->buffer+channel->front*channel->token_size);
		
		memcpy(memcpy_dst, memcpy_src ,channel->token_size);
		channel->front= (channel->front+1)%channel->capacity;					
		--(channel->count);
	}
}
void write_fifo(circular_fifo* channel,void* src, size_t number){
	char* memcpy_dst,*memcpy_src;
	for(int i=0; i<number;++i){
		
		memcpy_dst=(channel->rear*channel->token_size+ (char*)channel->buffer);
		memcpy_src = (char*)src+i*channel->token_size;
		memcpy(memcpy_dst, memcpy_src, channel->token_size);
		
		channel->rear= (channel->rear+1)%channel->capacity;
		++(channel->count);
	}				
}
void PRINT(circular_fifo * fifo){
	printf("buffer addr 0x%p, front: %d , rear %d, count %d\n",fifo->buffer,fifo->front,fifo->rear,fifo->count);
}				

			
