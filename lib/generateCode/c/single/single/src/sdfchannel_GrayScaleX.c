#include "../inc/config.h"
#include "../inc/circular_fifo_lib.h"
void* buffer_GrayScaleX[2];
size_t buffer_GrayScaleX_size = 2;
circular_fifo  fifo_GrayScaleX;
spinlock spinlock_GrayScaleX={.flag=0};
