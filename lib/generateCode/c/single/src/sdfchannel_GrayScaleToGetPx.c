#include "../inc/circular_fifo_lib.h"
DoubleType buffer_GrayScaleToGetPx[1];
int buffer_GrayScaleToGetPx_size = 1;
circular_fifo_DoubleType fifo_GrayScaleToGetPx;
spinlock spinlock_GrayScaleToGetPx={.flag=0};	

