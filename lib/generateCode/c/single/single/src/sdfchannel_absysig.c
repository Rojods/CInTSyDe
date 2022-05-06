#include "../inc/circular_fifo_lib.h"
volatile DoubleType buffer_absysig[2];
int channel_absysig_size=1;
/*
	Because of circular fifo, the 
	buffer_size=channel_size+1
*/
int buffer_absysig_size = 2;
circular_fifo_DoubleType fifo_absysig;
spinlock spinlock_absysig={.flag=0};

