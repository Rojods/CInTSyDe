#include "../inc/circular_fifo_lib.h"
type buffer_absysig[2];
int buffer_absysig_size = 2;
circular_fifo_type fifo_absysig;
spinlock spinlock_absysig={.flag=0};

