#ifndef                   ABSXSIG_H_
#define                   ABSXSIG_H_

#include <stdlib.h>
#include <stdint.h>	
#include <stdio.h> 
#include "spinlock.h"
/*
define token 
*/
typedef uint8_t token_absxsig ;	

/*
this is a circular buffer for absxsig
*/
typedef struct 
{
    token_absxsig* buffer;
    
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
}circularFIFO_absxsig;

void init_circularFIFO_absxsig(circularFIFO_absxsig* channel ,token_absxsig* buffer,size_t size);
int read_circularFIFO_non_blocking_absxsig(circularFIFO_absxsig* channel, token_absxsig* data);
int write_circularFIFO_non_blocking_absxsig(circularFIFO_absxsig* channel, token_absxsig value);
int read_circularFIFO_blocking_absxsig(circularFIFO_absxsig* channel, token_absxsig* data,spinlock* lock);
int write_circularFIFO_blocking_absxsig(circularFIFO_absxsig* channel, token_absxsig value,spinlock* lock);
			
#endif		


