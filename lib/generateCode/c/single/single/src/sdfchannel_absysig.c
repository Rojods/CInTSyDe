#include "../inc/config.h"
#include "../inc/circular_fifo_lib.h"
void* buffer_absysig[2];
size_t buffer_absysig_size = 2;
circular_fifo  fifo_absysig;
spinlock spinlock_absysig={.flag=0};
