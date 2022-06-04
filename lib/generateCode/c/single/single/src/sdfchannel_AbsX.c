#include "../inc/config.h"
#include "../inc/circular_fifo_lib.h"
void* buffer_AbsX[2];
size_t buffer_AbsX_size = 2;
circular_fifo  fifo_AbsX;
spinlock spinlock_AbsX={.flag=0};
