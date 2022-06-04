#include "../inc/config.h"
#include "../inc/circular_fifo_lib.h"
					
	void* buffer_s1[2];
	size_t buffer_s1_size = 2;
	ref_fifo  fifo_s1;
	spinlock spinlock_s1={.flag=0};
