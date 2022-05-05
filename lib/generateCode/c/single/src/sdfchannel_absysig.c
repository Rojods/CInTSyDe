#include "../inc/circular_fifo_lib.h"
	DoubleType buffer_absysig[2];
	int buffer_absysig_size = 2;
	circular_fifo_DoubleType fifo_absysig;
	spinlock spinlock_absysig={.flag=0};

