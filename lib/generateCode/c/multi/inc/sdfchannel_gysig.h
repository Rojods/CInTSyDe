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
this is a circular buffer for gysig
*/
typedef struct 
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
}circularFIFO_gysig;

void init_circularFIFO_gysig(circularFIFO_gysig* channel ,token_gysig* buffer,size_t size);
int read_circularFIFO_non_blocking_gysig(circularFIFO_gysig* channel, token_gysig* data);
int write_circularFIFO_non_blocking_gysig(circularFIFO_gysig* channel, token_gysig value);
int read_circularFIFO_blocking_gysig(circularFIFO_gysig* channel, token_gysig* data,spinlock* lock);
int write_circularFIFO_blocking_gysig(circularFIFO_gysig* channel, token_gysig value,spinlock* lock);
			
#endif		


