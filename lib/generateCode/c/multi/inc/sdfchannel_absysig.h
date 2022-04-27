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
this is a circular buffer for absysig
*/
typedef struct 
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
}circularFIFO_absysig;

void init_circularFIFO_absysig(circularFIFO_absysig* channel ,token_absysig* buffer,size_t size);
int read_circularFIFO_non_blocking_absysig(circularFIFO_absysig* channel, token_absysig* data);
int write_circularFIFO_non_blocking_absysig(circularFIFO_absysig* channel, token_absysig value);
int read_circularFIFO_blocking_absysig(circularFIFO_absysig* channel, token_absysig* data,spinlock* lock);
int write_circularFIFO_blocking_absysig(circularFIFO_absysig* channel, token_absysig value,spinlock* lock);
			
#endif		


