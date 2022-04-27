#include "../inc/sdfchannel_outputImage.h"
#include <stdio.h>
volatile spinlock spinlock_outputImage={.flag=0};
unsigned long buffersize_outputImage = 996005;
volatile token_outputImage arr_outputImage[996005];
circularFIFO_outputImage channel_outputImage;

void init_circularFIFO_outputImage(circularFIFO_outputImage* channel ,token_outputImage* buffer,size_t size){
		  
		    channel->buffer = buffer;
		    channel->size=size;
		    channel->front = 0;
		    channel->rear = 0;			
		}

	/* 
	read a token from channel.
	src: is channel outputImage
	dst:data
	*/
inline int read_circularFIFO_non_blocking_outputImage(circularFIFO_outputImage* channel, token_outputImage* data){
		if(channel->front==channel->rear){
		    	//empty 
		    	return -1;
		    			
		   }else{
		    	*data = channel->buffer[channel->front];
		    	//printf("buffer outputImage: before read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
		    	channel->front= (channel->front+1)%channel->size;
		    	//printf("buffer outputImage: after read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
		    	return 0;
		    }
	}
	
	/*
		write a token to _circular outputImage.
	
			
	*/
inline int write_circularFIFO_non_blocking_outputImage(circularFIFO_outputImage* channel, token_outputImage value){
	    /*if the buffer is full*/
	    if((channel->rear+1)%channel->size == channel->front){
	        //full!
	        //discard the data
	        //printf("buffer full error\n!");
	        return -1;
	     }else{
	        channel->buffer[channel->rear] = value;
	       //printf("buffer outputImage:before write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
	        channel->rear= (channel->rear+1)%channel->size;
	        //printf("buffer outputImage:after write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
	        return 0;
	    }			
		
	}	
	
inline	int read_circularFIFO_blocking_outputImage(circularFIFO_outputImage* channel, token_outputImage* data,spinlock* lock){
		spinlock_get(lock);
		if(channel->front==channel->rear){
		    	//empty 
		    	spinlock_release(lock);
		    	return -1;
		    			
		   }else{
		    	*data = channel->buffer[channel->front];
		    	//printf("buffer outputImage: before read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
		    	channel->front= (channel->front+1)%channel->size;
		    	//printf("buffer outputImage: after read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
		    	spinlock_release(lock);
		    	return 0;
		    }
	}

inline int write_circularFIFO_blocking_outputImage(circularFIFO_outputImage* channel, token_outputImage value,spinlock* lock){
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
	      //printf("buffer outputImage:before write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
	       channel->rear= (channel->rear+1)%channel->size;
	       //printf("buffer outputImage:after write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
	       spinlock_release(lock);
	       return 0;
	   }				
}			
