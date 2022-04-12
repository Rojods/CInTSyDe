	#ifndef                   GYSIG_H_
	#define                   GYSIG_H_
	
	#include <stdlib.h>
	#include <stdint.h>	
	#include <stdio.h> 
	#include "spinlock.h"
	/*
	define token 
	*/
	typedef uint8_t token_gysig ;	
	
	/*
	struct circular_buf_gysig: this is a circular buffer for gysig
	*/
	typedef struct circular_buf_gysig
	{
	    token_gysig* buffer;
	    
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

   
	}* circularFIFO_gysig;
	
	/* 
		create a _circular
		channel_channelName
	*/
	circularFIFO_gysig init_circularFIFO_gysig(token_gysig* buffer,size_t size);
	
	
	void destroy_circularFIFO_gysig(circularFIFO_gysig channel);
	
	/* 
		read a token from channel.
		The token is channel gysig

	*/
	int read_circularFIFO_non_blocking_gysig(circularFIFO_gysig channel, token_gysig* data);
	
	/*
		write a token to _circular gysig.
	*/
	int write_circularFIFO_non_blocking_gysig(circularFIFO_gysig channel, token_gysig value);


	/* 
		read a token from channel.
		The token is channel gysig

	*/
	int read_circularFIFO_blocking_gysig(circularFIFO_gysig channel, token_gysig* data,spinlock* lock);
	
	/*
		write a token to _circular gysig.
	*/
	int write_circularFIFO_blocking_gysig(circularFIFO_gysig channel, token_gysig value,spinlock* lock);				
	#endif		
	
	
