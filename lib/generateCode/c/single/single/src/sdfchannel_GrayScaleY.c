#include "../inc/circular_fifo_lib.h"
volatile UInt16 buffer_GrayScaleY[2];
int channel_GrayScaleY_size = 1;
/*
	Because of circular fifo, the 
	buffer_size=channel_size+1
*/
int buffer_GrayScaleY_size = 2;
circular_fifo_UInt16 fifo_GrayScaleY;
spinlock spinlock_GrayScaleY={.flag=0};	

