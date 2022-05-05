#include "../inc/circular_fifo_lib.h"
UInt16 buffer_GrayScaleToAbs[1];
int buffer_GrayScaleToAbs_size = 1;
circular_fifo_UInt16 fifo_GrayScaleToAbs;
spinlock spinlock_GrayScaleToAbs={.flag=0};	

