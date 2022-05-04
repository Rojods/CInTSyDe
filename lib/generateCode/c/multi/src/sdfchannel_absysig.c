#include "../inc/circular_fifo_lib.h"
UInt16 buffer_absysig[2];
int buffer_absysig_size = 2;
circular_fifo_UInt16 fifo_absysig;
spinlock spinlock_absysig={.flag=0};

