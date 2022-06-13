
		
		/*
		*******************************************************
			This file contains the function definition for 
			token types: DoubleType, UInt16
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
			DoubleType Channel Definition 
		=============================================================
		*/				
		void init_fifo_DoubleType(circular_fifo_DoubleType *channel ,DoubleType* buffer, size_t size){
		    channel->buffer = buffer;
		    channel->size=size;
		    channel->front = 0;
		    channel->rear = 0;	
		    channel->count=0;		
		}
		void read_fifo_DoubleType(circular_fifo_DoubleType* channel,DoubleType* dst, size_t number){
			
			while( channel->count < number );
			
			for(int i=0; i<number;++i){
				dst[i] = channel->buffer[channel->front];
				channel->front= (channel->front+1)%channel->size;
				--(channel->count);			
			}
		}
		
		void write_fifo_DoubleType(circular_fifo_DoubleType* channel,DoubleType* src, size_t number){
			// is full ?
			while( channel->front== ( (channel->rear+1)%channel->size) );
			
			
			for(int i=0; i<number; ++i){
			       channel->buffer[channel->rear] = src[i];
			    	channel->rear= (channel->rear+1)%channel->size;
			    	++(channel->count);	
			   }
			
		}
		
		
		/*
		=============================================================
			UInt16 Channel Definition 
		=============================================================
		*/				
		void init_fifo_UInt16(circular_fifo_UInt16 *channel ,UInt16* buffer, size_t size){
		    channel->buffer = buffer;
		    channel->size=size;
		    channel->front = 0;
		    channel->rear = 0;	
		    channel->count=0;		
		}
		void read_fifo_UInt16(circular_fifo_UInt16* channel,UInt16* dst, size_t number){
			
			while( channel->count < number );
			
			for(int i=0; i<number;++i){
				dst[i] = channel->buffer[channel->front];
				channel->front= (channel->front+1)%channel->size;
				--(channel->count);			
			}
		}
		
		void write_fifo_UInt16(circular_fifo_UInt16* channel,UInt16* src, size_t number){
			// is full ?
			while( channel->front== ( (channel->rear+1)%channel->size) );
			
			
			for(int i=0; i<number; ++i){
			       channel->buffer[channel->rear] = src[i];
			    	channel->rear= (channel->rear+1)%channel->size;
			    	++(channel->count);	
			   }
			
		}
		
		
