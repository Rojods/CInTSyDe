#include "../inc/sdfchannel_inputImage.h"
#include <stdio.h>
volatile spinlock spinlock_inputImage={.flag=0};
unsigned long buffersize_inputImage = 1000001;
volatile token_inputImage arr_inputImage[1000001];
circularFIFO_inputImage channel_inputImage;

void init_circularFIFO_inputImage(circularFIFO_inputImage* channel ,token_inputImage* buffer,size_t size){
		  
		    channel->buffer = buffer;
		    channel->size=size;
		    channel->front = 0;
		    channel->rear = 0;			
		}

	/* 
	read a token from channel.
	src: is channel inputImage
	dst:data
	*/
inline int read_circularFIFO_non_blocking_inputImage(circularFIFO_inputImage* channel, token_inputImage* data){
		if(channel->front==channel->rear){
		    	//empty 
		    	return -1;
		    			
		   }else{
		    	*data = channel->buffer[channel->front];
		    	//printf("buffer inputImage: before read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
		    	channel->front= (channel->front+1)%channel->size;
		    	//printf("buffer inputImage: after read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
		    	return 0;
		    }
	}
	
	/*
		write a token to _circular inputImage.
	
			
	*/
inline int write_circularFIFO_non_blocking_inputImage(circularFIFO_inputImage* channel, token_inputImage value){
	    /*if the buffer is full*/
	    if((channel->rear+1)%channel->size == channel->front){
	        //full!
	        //discard the data
	        //printf("buffer full error\n!");
	        return -1;
	     }else{
	        channel->buffer[channel->rear] = value;
	       //printf("buffer inputImage:before write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
	        channel->rear= (channel->rear+1)%channel->size;
	        //printf("buffer inputImage:after write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
	        return 0;
	    }			
		
	}	
	
inline	int read_circularFIFO_blocking_inputImage(circularFIFO_inputImage* channel, token_inputImage* data,spinlock* lock){
		spinlock_get(lock);
		if(channel->front==channel->rear){
		    	//empty 
		    	spinlock_release(lock);
		    	return -1;
		    			
		   }else{
		    	*data = channel->buffer[channel->front];
		    	//printf("buffer inputImage: before read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
		    	channel->front= (channel->front+1)%channel->size;
		    	//printf("buffer inputImage: after read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
		    	spinlock_release(lock);
		    	return 0;
		    }
	}

inline int write_circularFIFO_blocking_inputImage(circularFIFO_inputImage* channel, token_inputImage value,spinlock* lock){
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
	      //printf("buffer inputImage:before write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
	       channel->rear= (channel->rear+1)%channel->size;
	       //printf("buffer inputImage:after write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
	       spinlock_release(lock);
	       return 0;
	   }				
}			
