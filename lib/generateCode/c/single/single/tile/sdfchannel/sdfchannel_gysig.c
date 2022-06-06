
#include "sdfchannel_gysig.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
void* buffer_gysig[7];
size_t buffer_gysig_size = 7;
circular_fifo  fifo_gysig;
spinlock spinlock_gysig={.flag=0};
