#include "../inc/circular_fifo_lib.h"
	DoubleType buffer_gysig[7];
	int buffer_gysig_size = 7;
	circular_fifo_DoubleType fifo_gysig;
	spinlock spinlock_gysig={.flag=0};

