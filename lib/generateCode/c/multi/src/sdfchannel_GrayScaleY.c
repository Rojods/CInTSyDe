#include "../inc/circular_fifo_lib.h"
UInt16 buffer_GrayScaleY[2];
int buffer_GrayScaleY_size = 2;
circular_fifo_UInt16 fifo_GrayScaleY;
spinlock spinlock_GrayScaleY={.flag=0};	

