
#include "sdfchannel_GrayScaleY.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
void* buffer_GrayScaleY[2];
size_t buffer_GrayScaleY_size = 2;
circular_fifo  fifo_GrayScaleY;
spinlock spinlock_GrayScaleY={.flag=0};
