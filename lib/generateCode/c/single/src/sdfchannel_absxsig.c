#include "../inc/sdfchannel_absxsig.h"
#include <stdio.h>
volatile spinlock spinlock_absxsig={.flag=0};
unsigned long buffersize_absxsig = 2;
volatile token_absxsig arr_absxsig[2];
circularFIFO_absxsig channel_absxsig;

void init_circularFIFO_absxsig(circularFIFO_absxsig* channel ,token_absxsig* buffer,size_t size){
		  
		    channel->buffer = buffer;
		    channel->size=size;
		    channel->front = 0;
		    channel->rear = 0;			
		}

	/* 
	read a token from channel.
	src: is channel absxsig
	dst:data
	*/
inline int read_circularFIFO_non_blocking_absxsig(circularFIFO_absxsig* channel, token_absxsig* data){
		if(channel->front==channel->rear){
		    	//empty 
		    	return -1;
		    			
		   }else{
		    	*data = channel->buffer[channel->front];
		    	//printf("buffer absxsig: before read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
		    	channel->front= (channel->front+1)%channel->size;
		    	//printf("buffer absxsig: after read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
		    	return 0;
		    }
	}
	
	/*
		write a token to _circular absxsig.
	
			
	*/
inline int write_circularFIFO_non_blocking_absxsig(circularFIFO_absxsig* channel, token_absxsig value){
	    /*if the buffer is full*/
	    if((channel->rear+1)%channel->size == channel->front){
	        //full!
	        //discard the data
	        //printf("buffer full error\n!");
	        return -1;
	     }else{
	        channel->buffer[channel->rear] = value;
	       //printf("buffer absxsig:before write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
	        channel->rear= (channel->rear+1)%channel->size;
	        //printf("buffer absxsig:after write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
	        return 0;
	    }			
		
	}	
	
inline	int read_circularFIFO_blocking_absxsig(circularFIFO_absxsig* channel, token_absxsig* data,spinlock* lock){
		spinlock_get(lock);
		if(channel->front==channel->rear){
		    	//empty 
		    	spinlock_release(lock);
		    	return -1;
		    			
		   }else{
		    	*data = channel->buffer[channel->front];
		    	//printf("buffer absxsig: before read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
		    	channel->front= (channel->front+1)%channel->size;
		    	//printf("buffer absxsig: after read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
		    	spinlock_release(lock);
		    	return 0;
		    }
	}

inline int write_circularFIFO_blocking_absxsig(circularFIFO_absxsig* channel, token_absxsig value,spinlock* lock){
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
	      //printf("buffer absxsig:before write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
	       channel->rear= (channel->rear+1)%channel->size;
	       //printf("buffer absxsig:after write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
	       spinlock_release(lock);
	       return 0;
	   }				
}			
