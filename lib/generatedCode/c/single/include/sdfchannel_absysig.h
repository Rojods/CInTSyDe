	#ifndef                   ABSYSIG_H_
	#define                   ABSYSIG_H_
	
	#include <stdlib.h>
	#include <stdint.h>	
	#include <stdio.h> 
	#include "spinlock.h"
	/*
	define token 
	*/
	typedef uint8_t token_absysig ;	
	
	/*
	struct circular_buf_absysig: this is a circular buffer for absysig
	*/
	typedef struct circular_buf_absysig
	{
	    token_absysig* buffer;
	    
	    /*
	    front: the position of the begining
	    */
	    size_t front;
	    
	    /*
	    rear: the position just after the last element
	    */
	    size_t rear;
	    
	    /*
		the size of this buffer
		 */
		size_t size;	    

   
	}* circularFIFO_absysig;
	
	/* 
		create a _circular
		channel_channelName
	*/
	circularFIFO_absysig init_circularFIFO_absysig(token_absysig* buffer,size_t size);
	
	
	void destroy_circularFIFO_absysig(circularFIFO_absysig channel);
	
	/* 
		read a token from channel.
		The token is channel absysig

	*/
	int read_circularFIFO_non_blocking_absysig(circularFIFO_absysig channel, token_absysig* data);
	
	/*
		write a token to _circular absysig.
	*/
	int write_circularFIFO_non_blocking_absysig(circularFIFO_absysig channel, token_absysig value);


	/* 
		read a token from channel.
		The token is channel absysig

	*/
	int read_circularFIFO_blocking_absysig(circularFIFO_absysig channel, token_absysig* data,spinlock* lock);
	
	/*
		write a token to _circular absysig.
	*/
	int write_circularFIFO_blocking_absysig(circularFIFO_absysig channel, token_absysig value,spinlock* lock);				
	#endif		
	
	
