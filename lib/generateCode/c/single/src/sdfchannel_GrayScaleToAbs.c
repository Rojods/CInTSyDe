#include "../inc/circular_fifo_lib.h"
type buffer_GrayScaleToAbs[2];
int buffer_GrayScaleToAbs_size = 2;
circular_fifo_type fifo_GrayScaleToAbs;
spinlock spinlock_GrayScaleToAbs={.flag=0};			

