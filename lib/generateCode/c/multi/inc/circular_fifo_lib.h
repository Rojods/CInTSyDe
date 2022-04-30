#ifndef CIRCULAR_FIFO_LIB_H_
#define CIRCULAR_FIFO_LIB_H_
#include "datatype_definition.h"
#include "spinlock.h"

/*
=============================================================
				DoubleType Prototype
=============================================================
*/
typedef struct 
{
    DoubleType* buffer;
    size_t front;
    size_t rear;
size_t size;	    
}circular_channel_DoubleType;

void init_channel_DoubleType(circular_channel_DoubleType *channel ,DoubleType* buffer, size_t size);
int read_non_blocking_DoubleType(circular_channel_DoubleType* channel,DoubleType* dst );
int read_blocking_DoubleType(circular_channel_DoubleType* ptr,DoubleType* dst,spinlock *lock);
int write_non_blocking_DoubleType(circular_channel_DoubleType* ptr,DoubleType* src );
int write_blocking_DoubleType(circular_channel_DoubleType* ptr,DoubleType* src,spinlock *lock);	
	
/*
=============================================================
				Double Prototype
=============================================================
*/
typedef struct 
{
    Double* buffer;
    size_t front;
    size_t rear;
size_t size;	    
}circular_channel_Double;

void init_channel_Double(circular_channel_Double *channel ,Double* buffer, size_t size);
int read_non_blocking_Double(circular_channel_Double* channel,Double* dst );
int read_blocking_Double(circular_channel_Double* ptr,Double* dst,spinlock *lock);
int write_non_blocking_Double(circular_channel_Double* ptr,Double* src );
int write_blocking_Double(circular_channel_Double* ptr,Double* src,spinlock *lock);	
	
/*
=============================================================
				UInt16 Prototype
=============================================================
*/
typedef struct 
{
    UInt16* buffer;
    size_t front;
    size_t rear;
size_t size;	    
}circular_channel_UInt16;

void init_channel_UInt16(circular_channel_UInt16 *channel ,UInt16* buffer, size_t size);
int read_non_blocking_UInt16(circular_channel_UInt16* channel,UInt16* dst );
int read_blocking_UInt16(circular_channel_UInt16* ptr,UInt16* dst,spinlock *lock);
int write_non_blocking_UInt16(circular_channel_UInt16* ptr,UInt16* src );
int write_blocking_UInt16(circular_channel_UInt16* ptr,UInt16* src,spinlock *lock);	
	
/*
=============================================================
				UInt32 Prototype
=============================================================
*/
typedef struct 
{
    UInt32* buffer;
    size_t front;
    size_t rear;
size_t size;	    
}circular_channel_UInt32;

void init_channel_UInt32(circular_channel_UInt32 *channel ,UInt32* buffer, size_t size);
int read_non_blocking_UInt32(circular_channel_UInt32* channel,UInt32* dst );
int read_blocking_UInt32(circular_channel_UInt32* ptr,UInt32* dst,spinlock *lock);
int write_non_blocking_UInt32(circular_channel_UInt32* ptr,UInt32* src );
int write_blocking_UInt32(circular_channel_UInt32* ptr,UInt32* src,spinlock *lock);	
	
#endif
