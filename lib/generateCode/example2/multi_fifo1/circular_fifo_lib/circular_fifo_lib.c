
		
		/*
		*******************************************************
			This file contains the function definition for 
			token types: UInt32
			For each token type, there are five functions:
			init_channel_typeName(...)
			read_non_blocking_typeName(...)
			read_blocking_typeName(...)
			write_non_blocking_typeName(...)
			write_blocking_typeName(...)
		*******************************************************
		*/
		#include "../datatype/datatype_definition.h"
		#include "circular_fifo_lib.h"
		#include <string.h>
		

		
		/*
		=============================================================
			UInt32 Channel Definition 
		=============================================================
		*/				
		void init_fifo_UInt32(circular_fifo_UInt32 *channel ,UInt32* buffer, size_t size){
		    channel->buffer = buffer;
		    channel->size=size;
		    channel->front = 0;
		    channel->rear = 0;	
		    channel->count=0;		
		}
		void read_fifo_UInt32(circular_fifo_UInt32* channel,UInt32* dst, size_t number){
			
			while( channel->count < number );
			
			for(int i=0; i<number;++i){
				dst[i] = channel->buffer[channel->front];
				channel->front= (channel->front+1)%channel->size;
				--(channel->count);			
			}
		}
		
		void write_fifo_UInt32(circular_fifo_UInt32* channel,UInt32* src, size_t number){
			
			for(int i=0; i<number; ++i){
		        channel->buffer[channel->rear] = src[i];
		     	channel->rear= (channel->rear+1)%channel->size;
		     	++(channel->count);	
		    }
			
		}
		void PRINT_UInt32(circular_fifo_UInt32 * fifo){
			printf("buffer addr 0x%p, front: %d , rear %d, count %d\n",fifo->buffer,fifo->front,fifo->rear,fifo->count);
		}				
		
		
