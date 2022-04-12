
#include "../include/sdfchannel_gysig.h"
#include <stdio.h>

spinlock spinlock_gysig={.flag=0};

/*
	init the circular buffer
*/
inline static struct circular_buf_gysig* circular_buf_init(token_gysig * buffer, size_t size){
    struct circular_buf_gysig * channel = (struct circular_buf_gysig *)malloc(sizeof(struct circular_buf_gysig));
    
    channel->buffer = buffer;
    channel->size=size;
    channel->front = 0;
    channel->rear = 0;
    //channel->count=0;
    return channel;
}		

/*destroy*/
inline static void circular_buf_destroy(struct circular_buf_gysig* p){
	free(p);
}

/*
read one token from buffer
src: channel
dst: data
*/
inline static int circular_buf_get( struct circular_buf_gysig*   channel, token_gysig* data ){
     /*if the buffer is empty*/
     
     if(channel->front==channel->rear){
     	//empty 
     	return -1;
     		
     }else{
     	*data = channel->buffer[channel->front];
     	//printf("buffer gysig: before read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
     	channel->front= (channel->front+1)%channel->size;
     	//printf("buffer gysig: after read, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
     	return 0;
     }
}

/*
write one token to buffer
dst: channel
*/
inline static int circular_buf_put( struct circular_buf_gysig*   channel, token_gysig data ){
    /*if the buffer is full*/
    if((channel->rear+1)%channel->size == channel->front){
        //full!
        //discard the data
        printf("buffer full error\n!");
        return -1;
     }else{
        channel->buffer[channel->rear] = data;
       //printf("buffer gysig:before write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
        channel->rear= (channel->rear+1)%channel->size;
        //printf("buffer gysig:after write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
        return 0;
    }
}		

/* 
	create a Channel
*/
circularFIFO_gysig init_circularFIFO_gysig(token_gysig* buffer,size_t size){
	return circular_buf_init(buffer,size);
}


void destroy_channel_gysig(circularFIFO_gysig channel){
	circular_buf_destroy(channel);
}

/* 
read a token from channel.
src: is channel gysig
dst:data
*/
int read_circularFIFO_non_blocking_gysig(circularFIFO_gysig channel, token_gysig* data){
	return circular_buf_get(channel,data);
}

/*
	write a token to _circular gysig.

		
*/
int write_circularFIFO_non_blocking_gysig(circularFIFO_gysig channel, token_gysig value){
	return circular_buf_put(channel,value);
}	

int read_circularFIFO_blocking_gysig(circularFIFO_gysig channel, token_gysig* data,spinlock* lock){
	int ret = 0;
	
	spinlock_get(lock);
	ret = circular_buf_get(channel,data);
	spinlock_release(lock);
	return ret;
}

/*
	write a token to _circular gysig.

		
*/
int write_circularFIFO_blocking_gysig(circularFIFO_gysig channel, token_gysig value,spinlock* lock){
	int ret = 0;
	spinlock_get(lock);
	ret=circular_buf_put(channel,value);
	spinlock_release(lock);
	return ret;
}			
