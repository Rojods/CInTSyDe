#include "../inc/circular_fifo_lib.h"
volatile UInt16 buffer_AbsY[2];
int channel_AbsY_size = 1;
/*
	Because of circular fifo, the 
	buffer_size=channel_size+1
*/
int buffer_AbsY_size = 2;
circular_fifo_UInt16 fifo_AbsY;
spinlock spinlock_AbsY={.flag=0};	
