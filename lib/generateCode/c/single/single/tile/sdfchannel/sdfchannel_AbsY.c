
#include "sdfchannel_AbsY.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
void* buffer_AbsY[2];
size_t buffer_AbsY_size = 2;
circular_fifo  fifo_AbsY;
spinlock spinlock_AbsY={.flag=0};
