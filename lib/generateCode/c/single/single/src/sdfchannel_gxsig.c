#include "../inc/config.h"
#include "../inc/circular_fifo_lib.h"
void* buffer_gxsig[7];
size_t buffer_gxsig_size = 7;
circular_fifo  fifo_gxsig;
spinlock spinlock_gxsig={.flag=0};
