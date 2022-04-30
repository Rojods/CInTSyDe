#include "../inc/circular_fifo_lib.h"
type buffer_gxsig[7];
int buffer_gxsig_size = 7;
circular_fifo_type fifo_gxsig;
spinlock spinlock_gxsig={.flag=0};

