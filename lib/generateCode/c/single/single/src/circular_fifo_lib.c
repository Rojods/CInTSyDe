/*
*******************************************************
			copy by value
*******************************************************
*/
#include "../inc/config.h"
#include "../inc/datatype_definition.h"
#include "../inc/circular_fifo_lib.h"


void init(circular_fifo* fifo_ptr, void* buf, size_t token_number, size_t token_size){
	fifo_ptr->front=0;
	fifo_ptr->rear=0;
	fifo_ptr->token_number=token_number;
	fifo_ptr->token_size=token_size;
}
void read_non_blocking(circular_fifo* fifo_ptr,void* dst){
	if(fifo_ptr->front==fifo_ptr->rear){
		//empty
		return;
	}else{
		
		memcpy(dst,(void*)(fifo_ptr->buffer+fifo_ptr->front*fifo_ptr->token_size),fifo_ptr->token_size);
		fifo_ptr->front= (fifo_ptr->front+1)%fifo_ptr->token_number;
		
	}
}
void write_non_blocking(circular_fifo* channel,void* src){
	if((channel->rear+1)%channel->token_number == channel->front){
		//full	
	}else{
		memcpy((void*)(channel->rear*channel->token_size+channel->buffer), src , channel->token_size);
		channel->rear= (channel->rear+1)%channel->token_number;
	}
}	
			
