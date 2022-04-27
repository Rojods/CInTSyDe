#ifndef                   INPUTIMAGE_H_
#define                   INPUTIMAGE_H_

#include <stdlib.h>
#include <stdint.h>	
#include <stdio.h> 
#include "spinlock.h"
/*
define token 
*/
typedef uint32_t token_inputImage ;	

/*
this is a circular buffer for inputImage
*/
typedef struct 
{
    token_inputImage* buffer;
    
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
}circularFIFO_inputImage;

void init_circularFIFO_inputImage(circularFIFO_inputImage* channel ,token_inputImage* buffer,size_t size);
int read_circularFIFO_non_blocking_inputImage(circularFIFO_inputImage* channel, token_inputImage* data);
int write_circularFIFO_non_blocking_inputImage(circularFIFO_inputImage* channel, token_inputImage value);
int read_circularFIFO_blocking_inputImage(circularFIFO_inputImage* channel, token_inputImage* data,spinlock* lock);
int write_circularFIFO_blocking_inputImage(circularFIFO_inputImage* channel, token_inputImage value,spinlock* lock);
			
#endif		


