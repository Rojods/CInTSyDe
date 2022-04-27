#ifndef                   OUTPUTIMAGE_H_
#define                   OUTPUTIMAGE_H_

#include <stdlib.h>
#include <stdint.h>	
#include <stdio.h> 
#include "spinlock.h"
/*
define token 
*/
typedef uint32_t token_outputImage ;	

/*
this is a circular buffer for outputImage
*/
typedef struct 
{
    token_outputImage* buffer;
    
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
}circularFIFO_outputImage;

void init_circularFIFO_outputImage(circularFIFO_outputImage* channel ,token_outputImage* buffer,size_t size);
int read_circularFIFO_non_blocking_outputImage(circularFIFO_outputImage* channel, token_outputImage* data);
int write_circularFIFO_non_blocking_outputImage(circularFIFO_outputImage* channel, token_outputImage value);
int read_circularFIFO_blocking_outputImage(circularFIFO_outputImage* channel, token_outputImage* data,spinlock* lock);
int write_circularFIFO_blocking_outputImage(circularFIFO_outputImage* channel, token_outputImage value,spinlock* lock);
			
#endif		


