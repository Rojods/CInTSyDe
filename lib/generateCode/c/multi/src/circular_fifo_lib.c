/*
*******************************************************
	This file contains the function definition for 
	token types: Array6OfDoubleType, UInt16
	For each token type, there are five functions:
	init_channel_typeName(...)
	read_non_blocking_typeName(...)
	read_blocking_typeName(...)
	write_non_blocking_typeName(...)
	write_blocking_typeName(...)
*******************************************************
*/
#include "../inc/datatype_definition.h"
#include "../inc/circular_fifo_lib.h"


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
	    	#if defined(TEST)
	    	printf("buffer UInt16: before read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
	    	#endif
	    	channel->front= (channel->front+1)%channel->size;
	    	#if defined(TEST)
	    	printf("buffer UInt16: after read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
	    	#endif
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
	
		
