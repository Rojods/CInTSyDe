#include "../inc/config.h"
#include "../inc/circular_fifo_lib.h"
	
	void* buffer_absxsig[2];
	size_t buffer_absxsig_size = 2;
	ref_fifo  fifo_absxsig;
	spinlock spinlock_absxsig={.flag=0};
