	#ifndef CIRCULAR_FIFO_LIB_H_
	#define CIRCULAR_FIFO_LIB_H_
	#include "config.h"

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
			If Token type is DoubleType 
	==============================================================
	*/
	typedef struct 
	{
	    DoubleType* buffer;
	    size_t front;
	    size_t rear;
		size_t size;	    
	}circular_fifo_DoubleType;
	
	void init_channel_DoubleType(circular_fifo_DoubleType *channel ,DoubleType* buffer, size_t size);
	int read_non_blocking_DoubleType(circular_fifo_DoubleType* src,DoubleType* dst );
	int read_blocking_DoubleType(circular_fifo_DoubleType* src,DoubleType* dst,spinlock *lock);
	int write_non_blocking_DoubleType(circular_fifo_DoubleType* dst,DoubleType src );
	int write_blocking_DoubleType(circular_fifo_DoubleType* dst,DoubleType src,spinlock *lock);	
				
	/*
	=============================================================
			If Token type is UInt16 
	==============================================================
	*/
	typedef struct 
	{
	    UInt16* buffer;
	    size_t front;
	    size_t rear;
		size_t size;	    
	}circular_fifo_UInt16;
	
	void init_channel_UInt16(circular_fifo_UInt16 *channel ,UInt16* buffer, size_t size);
	int read_non_blocking_UInt16(circular_fifo_UInt16* src,UInt16* dst );
	int read_blocking_UInt16(circular_fifo_UInt16* src,UInt16* dst,spinlock *lock);
	int write_non_blocking_UInt16(circular_fifo_UInt16* dst,UInt16 src );
	int write_blocking_UInt16(circular_fifo_UInt16* dst,UInt16 src,spinlock *lock);	
				

	
	#endif
