#include "../inc/circular_fifo_lib.h"
UInt16 buffer_GrayScaleX[2];
int buffer_GrayScaleX_size = 2;
circular_fifo_type fifo_GrayScaleX;
spinlock spinlock_GrayScaleX={.flag=0};			

