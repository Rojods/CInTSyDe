#include "../inc/circular_fifo_lib.h"
UInt16 buffer_AbsX[2];
int buffer_AbsX_size = 2;
circular_fifo_UInt16 fifo_AbsX;
spinlock spinlock_AbsX={.flag=0};			

