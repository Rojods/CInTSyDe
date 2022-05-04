#include "../inc/circular_fifo_lib.h"
Array6OfDoubleType buffer_GrayScaleToGetPx[2];
int buffer_GrayScaleToGetPx_size = 2;
circular_fifo_Array6OfDoubleType fifo_GrayScaleToGetPx;
spinlock spinlock_GrayScaleToGetPx={.flag=0};			

