#include "../inc/circular_fifo_lib.h"
UInt16 buffer_AbsY[2];
int buffer_AbsY_size = 2;
circular_fifo_UInt16 fifo_AbsY;
spinlock spinlock_AbsY={.flag=0};	

