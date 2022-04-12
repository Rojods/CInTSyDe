
#include "../include/sdfchannel_absxsig.h"
#include <stdio.h>

spinlock spinlock_absxsig={.flag=0};

/*
	init the circular buffer
*/
inline static struct circular_buf_absxsig* circular_buf_init(token_absxsig * buffer, size_t size){
    struct circular_buf_absxsig * channel = (struct circular_buf_absxsig *)malloc(sizeof(struct circular_buf_absxsig));
    
    channel->buffer = buffer;
    channel->size=size;
    channel->front = 0;
    channel->rear = 0;
    //channel->count=0;
    return channel;
}		

/*destroy*/
inline static void circular_buf_destroy(struct circular_buf_absxsig* p){
	free(p);
}

/*
read one token from buffer
src: channel
dst: data
*/
inline static int circular_buf_get( struct circular_buf_absxsig*   channel, token_absxsig* data ){
     /*if the buffer is empty*/
     
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
write one token to buffer
dst: channel
*/
inline static int circular_buf_put( struct circular_buf_absxsig*   channel, token_absxsig data ){
    /*if the buffer is full*/
    if((channel->rear+1)%channel->size == channel->front){
        //full!
        //discard the data
        printf("buffer full error\n!");
        return -1;
     }else{
        channel->buffer[channel->rear] = data;
       //printf("buffer absxsig:before write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
        channel->rear= (channel->rear+1)%channel->size;
        //printf("buffer absxsig:after write, front: %d, rear %d size:%d\n",channel->front,channel->rear,channel->size);
        return 0;
    }
}		

/* 
	create a Channel
*/
circularFIFO_absxsig init_circularFIFO_absxsig(token_absxsig* buffer,size_t size){
	return circular_buf_init(buffer,size);
}


void destroy_channel_absxsig(circularFIFO_absxsig channel){
	circular_buf_destroy(channel);
}

/* 
read a token from channel.
src: is channel absxsig
dst:data
*/
int read_circularFIFO_non_blocking_absxsig(circularFIFO_absxsig channel, token_absxsig* data){
	return circular_buf_get(channel,data);
}

/*
	write a token to _circular absxsig.

		
*/
int write_circularFIFO_non_blocking_absxsig(circularFIFO_absxsig channel, token_absxsig value){
	return circular_buf_put(channel,value);
}	

int read_circularFIFO_blocking_absxsig(circularFIFO_absxsig channel, token_absxsig* data,spinlock* lock){
	int ret = 0;
	
	spinlock_get(lock);
	ret = circular_buf_get(channel,data);
	spinlock_release(lock);
	return ret;
}

/*
	write a token to _circular absxsig.

		
*/
int write_circularFIFO_blocking_absxsig(circularFIFO_absxsig channel, token_absxsig value,spinlock* lock){
	int ret = 0;
	spinlock_get(lock);
	ret=circular_buf_put(channel,value);
	spinlock_release(lock);
	return ret;
}			
