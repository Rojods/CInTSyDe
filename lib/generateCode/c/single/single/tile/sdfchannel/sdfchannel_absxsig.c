
#include "sdfchannel_absxsig.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
void* buffer_absxsig[2];
size_t buffer_absxsig_size = 2;
circular_fifo  fifo_absxsig;
spinlock spinlock_absxsig={.flag=0};
