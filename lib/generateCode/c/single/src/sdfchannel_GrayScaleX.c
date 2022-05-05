#include "../inc/circular_fifo_lib.h"
volatile UInt16 buffer_GrayScaleX[2];
int channel_GrayScaleX_size = 1;
/*
	Because of circular fifo, the 
	buffer_size=channel_size+1
*/
int buffer_GrayScaleX_size = 2;
circular_fifo_UInt16 fifo_GrayScaleX;
spinlock spinlock_GrayScaleX={.flag=0};	

