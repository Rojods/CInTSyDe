#include "../inc/circular_fifo_lib.h"
UInt16 buffer_absxsig[2];
int buffer_absxsig_size = 2;
circular_fifo_type fifo_absxsig;
spinlock spinlock_absxsig={.flag=0};

