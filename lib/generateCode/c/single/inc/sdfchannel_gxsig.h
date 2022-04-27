#ifndef                   GXSIG_H_
#define                   GXSIG_H_

#include <stdlib.h>
#include <stdint.h>	
#include <stdio.h> 
#include "spinlock.h"
/*
define token 
*/
typedef uint8_t token_gxsig ;	

/*
this is a circular buffer for gxsig
*/
typedef struct 
{
    token_gxsig* buffer;
    
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
}circularFIFO_gxsig;

void init_circularFIFO_gxsig(circularFIFO_gxsig* channel ,token_gxsig* buffer,size_t size);
int read_circularFIFO_non_blocking_gxsig(circularFIFO_gxsig* channel, token_gxsig* data);
int write_circularFIFO_non_blocking_gxsig(circularFIFO_gxsig* channel, token_gxsig value);
int read_circularFIFO_blocking_gxsig(circularFIFO_gxsig* channel, token_gxsig* data,spinlock* lock);
int write_circularFIFO_blocking_gxsig(circularFIFO_gxsig* channel, token_gxsig value,spinlock* lock);
			
#endif		


