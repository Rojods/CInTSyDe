#include "../inc/config.h"
#include "../inc/spinlock.h"
#include "../inc/datatype_definition.h"
#include "../inc/circular_fifo_lib.h"
	/* Channel On One Processor */
	volatile UInt16 buffer_GrayScaleY[2];
	unsigned int channel_GrayScaleY_size = 1;
	unsigned int buffer_GrayScaleY_size = 2; // Because of circular fifo, the buffer_size=channel_size+1 
	circular_fifo_UInt16 fifo_GrayScaleY;
	spinlock spinlock_GrayScaleY={.flag=0};	
