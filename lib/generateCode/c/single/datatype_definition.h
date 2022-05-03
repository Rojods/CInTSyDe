#ifndef DATATYPE_DEFINITION_
#define DATATYPE_DEFINITION_
#include <stdio.h>
#include "spinlock.h"

/*
==============================================================
		TYPING_DATATYPES_DOUBLE
==============================================================
*/
typedef double DoubleType;
typedef double Double;

/*
==============================================================
		TYPING_DATATYPES_FLOAT
==============================================================
*/

/*
==============================================================
		TYPING_DATATYPES_INTEGER
==============================================================
*/
typedef unsigned short UInt16;
typedef unsigned int UInt32;

/*
==============================================================
		TYPING_DATATYPES_ARRAY
==============================================================
*/
typedef Double Array1000OfDouble[1000];
typedef Array1000OfDouble Array1000OfArrayOfDouble[1000];
typedef DoubleType Array6OfDoubleType[6];
typedef UInt16 Array2OfUInt16[2];
typedef DoubleType *ArrayXOfDoubleType;
typedef ArrayXOfDoubleType *ArrayXOfArrayXOfDoubleType;

typedef Double Array100OfDouble[100];
typedef Array100OfDouble Array100OfArrayOfDouble[100];
	typedef struct 
	{
	    Array6OfDoubleType* buffer;
	    size_t front;
	    size_t rear;
		size_t size;	    
	}circular_fifo_Array6OfDoubleType;

/*
=============================================================
				Array6OfDoubleType Channel Definition
=============================================================
*/				
void init_channel_Array6OfDoubleType(circular_fifo_Array6OfDoubleType *channel ,Array6OfDoubleType* buffer, size_t size){
    channel->buffer = buffer;
    channel->size=size;
    channel->front = 0;
    channel->rear = 0;			
}
			
int read_non_blocking_Array6OfDoubleType(circular_fifo_Array6OfDoubleType *channel, Array6OfDoubleType *data){
	if(channel->front==channel->rear){
	    	//empty 
	    	return -1;
	    			
	   }else{
	     	for(int i=0;i < 6; ++i){
	     		(*data)[i]=channel->buffer[channel->front][i];
	     	}
	    	#if defined(TEST)
	    	printf("buffer Array6OfDoubleType: before read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
	    	#endif
	    	channel->front= (channel->front+1)%channel->size;
	    	#if defined(TEST)
	    	printf("buffer Array6OfDoubleType: after read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
	    	#endif
	    	return 0;
	    }
}
int read_blocking_Array6OfDoubleType(circular_fifo_Array6OfDoubleType* channel,Array6OfDoubleType* data,spinlock* lock){
	spinlock_get(lock);
	if(channel->front==channel->rear){
	    	//empty 
	    	spinlock_release(lock);
	    	return -1;
	    			
	   }else{
	     	for(int i=0;i < 6; ++i){
	     		(*data)[i]=channel->buffer[channel->front][i];
	     	}
	    	//printf("buffer Array6OfDoubleType: before read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
	    	channel->front= (channel->front+1)%channel->size;
	    	//printf("buffer Array6OfDoubleType: after read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
	    	spinlock_release(lock);
	    	return 0;
	    }
}				
			
int write_non_blocking_Array6OfDoubleType(circular_fifo_Array6OfDoubleType* channel, Array6OfDoubleType value){
    /*if the buffer is full*/
    if((channel->rear+1)%channel->size == channel->front){
        //full!
        //discard the data
        //printf("buffer full error\n!");
        return -1;
     }else{
     	for(int i=0;i < 6; ++i){
     		channel->buffer[channel->rear][i] = value[i];
     	}
        
       //printf("buffer Array6OfDoubleType:before write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
        channel->rear= (channel->rear+1)%channel->size;
        //printf("buffer Array6OfDoubleType:after write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
        return 0;
    }			

}	
			
int write_blocking_Array6OfDoubleType(circular_fifo_Array6OfDoubleType* channel, Array6OfDoubleType value,spinlock* lock){
	spinlock_get(lock);
	
	   /*if the buffer is full*/
	   if((channel->rear+1)%channel->size == channel->front){
	       //full!
	       //discard the data
	       //printf("buffer full error\n!");
	       spinlock_release(lock);
	       return -1;
	    }else{
	     	for(int i=0;i < 6; ++i){
	     		channel->buffer[channel->rear][i] = value[i];
	     	}
	      //printf("buffer Array6OfDoubleType:before write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
	       channel->rear= (channel->rear+1)%channel->size;
	       //printf("buffer Array6OfDoubleType:after write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
	       spinlock_release(lock);
	       return 0;
	   }				
}				
#endif
