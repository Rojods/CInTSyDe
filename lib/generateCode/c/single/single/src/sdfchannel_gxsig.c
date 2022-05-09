#include "../inc/circular_fifo_lib.h"
volatile DoubleType buffer_gxsig[7];
int channel_gxsig_size=6;
/*
	Because of circular fifo, the 
	buffer_size=channel_size+1
*/
int buffer_gxsig_size = 7;
circular_fifo_DoubleType fifo_gxsig;
spinlock spinlock_gxsig={.flag=0};

