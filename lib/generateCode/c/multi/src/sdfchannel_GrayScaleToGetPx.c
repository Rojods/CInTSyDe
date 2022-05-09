#include "../inc/circular_fifo_lib.h"
volatile DoubleType buffer_GrayScaleToGetPx[2];
int channel_GrayScaleToGetPx_size = 1;
/*
	Because of circular fifo, the 
	buffer_size=channel_size+1
*/
int buffer_GrayScaleToGetPx_size = 2;
circular_fifo_DoubleType fifo_GrayScaleToGetPx;
spinlock spinlock_GrayScaleToGetPx={.flag=0};	

