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
			If Token type is DoubleType 
=============================================================
*/
typedef struct 
{
    DoubleType* buffer;
    size_t front;
    size_t rear;
	size_t size;
	size_t count;	    
}circular_fifo_DoubleType;

void init_fifo_DoubleType(circular_fifo_DoubleType *channel ,DoubleType* buffer, size_t size);

void read_fifo_DoubleType(circular_fifo_DoubleType* channel,DoubleType* dst, size_t number);
void write_fifo_DoubleType(circular_fifo_DoubleType* channel,DoubleType* src, size_t number);
void PRINT_DoubleType(circular_fifo_DoubleType * fifo);

/*
=============================================================
			If Token type is UInt16 
=============================================================
*/
typedef struct 
{
    UInt16* buffer;
    size_t front;
    size_t rear;
	size_t size;
	size_t count;	    
}circular_fifo_UInt16;

void init_fifo_UInt16(circular_fifo_UInt16 *channel ,UInt16* buffer, size_t size);

void read_fifo_UInt16(circular_fifo_UInt16* channel,UInt16* dst, size_t number);
void write_fifo_UInt16(circular_fifo_UInt16* channel,UInt16* src, size_t number);
void PRINT_UInt16(circular_fifo_UInt16 * fifo);



#endif
