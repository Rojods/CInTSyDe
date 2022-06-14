/*
*******************************************************
			copy by value
*******************************************************
*/

#include "../datatype/datatype_definition.h"
#include "../circular_fifo_lib/circular_fifo_lib.h"
#include <string.h>
#include <stdio.h>

void init_fifo(circular_fifo* fifo_ptr, void* buf, int capacity, int token_size){
	fifo_ptr->buffer=buf;
	
	fifo_ptr->front=0;
	fifo_ptr->rear=0;
	fifo_ptr->capacity=capacity;
	fifo_ptr->token_size=token_size;
	fifo_ptr->count=0;
}

void read_fifo(circular_fifo* channel, void* dst, int number){
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
void write_fifo(circular_fifo* channel,void* src, int number){
	// is full?
	while(channel->front== (  (channel->rear+1)%channel->capacity ) );
	
	char* memcpy_dst,*memcpy_src;
	for(int i=0; i<number;++i){
		
		memcpy_dst=(channel->rear*channel->token_size+ (char*)channel->buffer);
		memcpy_src = (char*)src+i*channel->token_size;
		memcpy(memcpy_dst, memcpy_src, channel->token_size);
		
		channel->rear= (channel->rear+1)%channel->capacity;
		++(channel->count);
	}				
}
			
			
