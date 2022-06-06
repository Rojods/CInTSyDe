
#include "sdfchannel_GrayScaleToAbs.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
void* buffer_GrayScaleToAbs[3];
size_t buffer_GrayScaleToAbs_size = 3;
circular_fifo  fifo_GrayScaleToAbs;
spinlock spinlock_GrayScaleToAbs={.flag=0};
