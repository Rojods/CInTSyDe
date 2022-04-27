#include "../inc/sdfchannel_gxsig.h"
#include <stdio.h>
volatile spinlock spinlock_gxsig={.flag=0};
unsigned long buffersize_gxsig = 7;
volatile token_gxsig arr_gxsig[7];
circularFIFO_gxsig channel_gxsig;

void init_circularFIFO_gxsig(circularFIFO_gxsig* channel ,token_gxsig* buffer,size_t size){
		  
		    channel->buffer = buffer;
		    channel->size=size;
		    channel->front = 0;
		    channel->rear = 0;			
		}

	/* 
	read a token from channel.
	src: is channel gxsig
	dst:data
	*/
inline int read_circularFIFO_non_blocking_gxsig(circularFIFO_gxsig* channel, token_gxsig* data){
		if(channel->front==channel->rear){
		    	//empty 
		    	return -1;
		    			
		   }else{
		    	*data = channel->buffer[channel->front];
		    	//printf("buffer gxsig: before read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
		    	channel->front= (channel->front+1)%channel->size;
		    	//printf("buffer gxsig: after read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
		    	return 0;
		    }
	}
	
	/*
		write a token to _circular gxsig.
	
			
	*/
inline int write_circularFIFO_non_blocking_gxsig(circularFIFO_gxsig* channel, token_gxsig value){
	    /*if the buffer is full*/
	    if((channel->rear+1)%channel->size == channel->front){
	        //full!
	        //discard the data
	        //printf("buffer full error\n!");
	        return -1;
	     }else{
	        channel->buffer[channel->rear] = value;
	       //printf("buffer gxsig:before write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
	        channel->rear= (channel->rear+1)%channel->size;
	        //printf("buffer gxsig:after write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
	        return 0;
	    }			
		
	}	
	
inline	int read_circularFIFO_blocking_gxsig(circularFIFO_gxsig* channel, token_gxsig* data,spinlock* lock){
		spinlock_get(lock);
		if(channel->front==channel->rear){
		    	//empty 
		    	spinlock_release(lock);
		    	return -1;
		    			
		   }else{
		    	*data = channel->buffer[channel->front];
		    	//printf("buffer gxsig: before read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
		    	channel->front= (channel->front+1)%channel->size;
		    	//printf("buffer gxsig: after read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
		    	spinlock_release(lock);
		    	return 0;
		    }
	}

inline int write_circularFIFO_blocking_gxsig(circularFIFO_gxsig* channel, token_gxsig value,spinlock* lock){
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
	      //printf("buffer gxsig:before write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
	       channel->rear= (channel->rear+1)%channel->size;
	       //printf("buffer gxsig:after write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
	       spinlock_release(lock);
	       return 0;
	   }				
}			
