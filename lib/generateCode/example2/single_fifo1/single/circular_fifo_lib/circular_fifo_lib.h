#ifndef CIRCULAR_FIFO_LIB_H_
#define CIRCULAR_FIFO_LIB_H_


/*
************************************************************
This header file defines all the prototype of token types in
SDFChannels
************************************************************
*/


#include "../datatype/datatype_definition.h"

//#include "spinlock.h"	

/*
=============================================================
			If Token type is UInt32 
=============================================================
*/
typedef struct 
{
    UInt32* buffer;
    size_t front;
    size_t rear;
	size_t size;
	size_t count;	    
}circular_fifo_UInt32;

void init_channel_UInt32(circular_fifo_UInt32 *channel ,UInt32* buffer, size_t size);

void read_fifo_UInt32(circular_fifo_UInt32* channel,UInt32* dst, size_t number);
void write_fifo_UInt32(circular_fifo_UInt32* channel,UInt32* src, size_t number);
void PRINT_UInt32(circular_fifo_UInt32 * fifo);



#endif
