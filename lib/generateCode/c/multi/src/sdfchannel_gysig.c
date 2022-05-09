#include "../inc/circular_fifo_lib.h"
volatile DoubleType buffer_gysig[7];
int channel_gysig_size=6;
/*
	Because of circular fifo, the 
	buffer_size=channel_size+1
*/
int buffer_gysig_size = 7;
circular_fifo_DoubleType fifo_gysig;
spinlock spinlock_gysig={.flag=0};

