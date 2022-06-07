
		
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
		void init_channel_DoubleType(circular_fifo_DoubleType *channel ,DoubleType* buffer, size_t size){
		    channel->buffer = buffer;
		    channel->size=size;
		    channel->front = 0;
		    channel->rear = 0;			
		}
		
				int read_non_blocking_DoubleType(circular_fifo_DoubleType *channel, DoubleType *data){
					if(channel->front==channel->rear){
					    	//empty 
					    	return -1;
					    			
					   }else{
					    	*data = channel->buffer[channel->front];
					    	channel->front= (channel->front+1)%channel->size;
					    	return 0;
					    }
				}
				int read_blocking_DoubleType(circular_fifo_DoubleType* channel,DoubleType* data,spinlock* lock){
					spinlock_get(lock);
					if(channel->front==channel->rear){
					    	//empty 
					    	spinlock_release(lock);
					    	return -1;
					    			
					   }else{
					    	*data = channel->buffer[channel->front];
					    	//printf("buffer DoubleType: before read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
					    	channel->front= (channel->front+1)%channel->size;
					    	//printf("buffer DoubleType: after read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
					    	spinlock_release(lock);
					    	return 0;
					    }
				}				
		
				int write_non_blocking_DoubleType(circular_fifo_DoubleType* channel, DoubleType value){
				    /*if the buffer is full*/
				    if((channel->rear+1)%channel->size == channel->front){
				        //full!
				        //discard the data
				        //printf("buffer full error\n!");
				        return -1;
				     }else{
				        channel->buffer[channel->rear] = value;
				       //printf("buffer DoubleType:before write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
				        channel->rear= (channel->rear+1)%channel->size;
				        //printf("buffer DoubleType:after write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
				        return 0;
				    }			
				
				}	
		
				int write_blocking_DoubleType(circular_fifo_DoubleType* channel, DoubleType value,spinlock* lock){
					spinlock_get(lock);
					
					   /*if the buffer is full*/
					   if((channel->rear+1)%channel->size == channel->front){
					       //full!
					       //discard the data
					       //printf("buffer full error\n!");
					       spinlock_release(lock);
					       return -1;
					    }else{
					       channel->buffer[channel->rear] = value;
					      //printf("buffer DoubleType:before write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
					       channel->rear= (channel->rear+1)%channel->size;
					       //printf("buffer DoubleType:after write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
					       spinlock_release(lock);
					       return 0;
					   }				
				}
		
		
		/*
		=============================================================
						UInt16 Channel Definition
		=============================================================
		*/				
		void init_channel_UInt16(circular_fifo_UInt16 *channel ,UInt16* buffer, size_t size){
		    channel->buffer = buffer;
		    channel->size=size;
		    channel->front = 0;
		    channel->rear = 0;			
		}
		
				int read_non_blocking_UInt16(circular_fifo_UInt16 *channel, UInt16 *data){
					if(channel->front==channel->rear){
					    	//empty 
					    	return -1;
					    			
					   }else{
					    	*data = channel->buffer[channel->front];
					    	channel->front= (channel->front+1)%channel->size;
					    	return 0;
					    }
				}
				int read_blocking_UInt16(circular_fifo_UInt16* channel,UInt16* data,spinlock* lock){
					spinlock_get(lock);
					if(channel->front==channel->rear){
					    	//empty 
					    	spinlock_release(lock);
					    	return -1;
					    			
					   }else{
					    	*data = channel->buffer[channel->front];
					    	//printf("buffer UInt16: before read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
					    	channel->front= (channel->front+1)%channel->size;
					    	//printf("buffer UInt16: after read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
					    	spinlock_release(lock);
					    	return 0;
					    }
				}				
		
				int write_non_blocking_UInt16(circular_fifo_UInt16* channel, UInt16 value){
				    /*if the buffer is full*/
				    if((channel->rear+1)%channel->size == channel->front){
				        //full!
				        //discard the data
				        //printf("buffer full error\n!");
				        return -1;
				     }else{
				        channel->buffer[channel->rear] = value;
				       //printf("buffer UInt16:before write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
				        channel->rear= (channel->rear+1)%channel->size;
				        //printf("buffer UInt16:after write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
				        return 0;
				    }			
				
				}	
		
				int write_blocking_UInt16(circular_fifo_UInt16* channel, UInt16 value,spinlock* lock){
					spinlock_get(lock);
					
					   /*if the buffer is full*/
					   if((channel->rear+1)%channel->size == channel->front){
					       //full!
					       //discard the data
					       //printf("buffer full error\n!");
					       spinlock_release(lock);
					       return -1;
					    }else{
					       channel->buffer[channel->rear] = value;
					      //printf("buffer UInt16:before write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
					       channel->rear= (channel->rear+1)%channel->size;
					       //printf("buffer UInt16:after write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
					       spinlock_release(lock);
					       return 0;
					   }				
				}
		
		
