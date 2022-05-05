#include "../inc/circular_fifo_lib.h"
	DoubleType buffer_absxsig[2];
	int buffer_absxsig_size = 2;
	circular_fifo_DoubleType fifo_absxsig;
	spinlock spinlock_absxsig={.flag=0};

