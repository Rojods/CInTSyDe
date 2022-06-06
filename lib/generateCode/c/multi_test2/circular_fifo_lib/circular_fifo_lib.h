#ifndef CIRCULAR_FIFO_LIB_H_
#define CIRCULAR_FIFO_LIB_H_


/*
************************************************************
This header file defines all the prototype of token types in
SDFChannels
************************************************************
*/


#include "../datatype/datatype_definition.h"

#include "spinlock.h"	

/*
=============================================================
			If Token type is uint32 
=============================================================
*/
typedef struct 
{
    uint32* buffer;
    size_t front;
    size_t rear;
	size_t size;	    
}circular_fifo_uint32;

void init_channel_uint32(circular_fifo_uint32 *channel ,uint32* buffer, size_t size);
int read_non_blocking_uint32(circular_fifo_uint32* src,uint32* dst );
int read_blocking_uint32(circular_fifo_uint32* src,uint32* dst,spinlock *lock);
int write_non_blocking_uint32(circular_fifo_uint32* dst,uint32 src );
int write_blocking_uint32(circular_fifo_uint32* dst,uint32 src,spinlock *lock);	





#endif
