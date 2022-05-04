#ifndef CIRCULAR_FIFO_LIB_H_
#define CIRCULAR_FIFO_LIB_H_
/*
************************************************************
This header file defines all the prototype of token types in
SDFChannels
************************************************************
*/


#include "datatype_definition.h"
#include "spinlock.h"			
	/*
	=============================================================
				If Token type is Array6OfDoubleType 
	=============================================================
	*/
	typedef struct 
	{
	    Array6OfDoubleType* buffer;
	    size_t front;
	    size_t rear;
		size_t size;	    
	}circular_fifo_Array6OfDoubleType;
	
	void init_channel_Array6OfDoubleType(circular_fifo_Array6OfDoubleType *channel ,Array6OfDoubleType* buffer, size_t size);
	int read_non_blocking_Array6OfDoubleType(circular_fifo_Array6OfDoubleType* channel,Array6OfDoubleType* dst );
	int read_blocking_Array6OfDoubleType(circular_fifo_Array6OfDoubleType* ptr,Array6OfDoubleType* dst,spinlock *lock);
	int write_non_blocking_Array6OfDoubleType(circular_fifo_Array6OfDoubleType* ptr,Array6OfDoubleType* src );
	int write_blocking_Array6OfDoubleType(circular_fifo_Array6OfDoubleType* ptr,Array6OfDoubleType* src,spinlock *lock);	
				
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
	}circular_fifo_UInt16;
	
	void init_channel_UInt16(circular_fifo_UInt16 *channel ,UInt16* buffer, size_t size);
	int read_non_blocking_UInt16(circular_fifo_UInt16* channel,UInt16* dst );
	int read_blocking_UInt16(circular_fifo_UInt16* ptr,UInt16* dst,spinlock *lock);
	int write_non_blocking_UInt16(circular_fifo_UInt16* ptr,UInt16* src );
	int write_blocking_UInt16(circular_fifo_UInt16* ptr,UInt16* src,spinlock *lock);	
				
#endif
