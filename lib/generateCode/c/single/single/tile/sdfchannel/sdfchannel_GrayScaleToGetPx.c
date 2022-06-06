
#include "sdfchannel_GrayScaleToGetPx.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
void* buffer_GrayScaleToGetPx[7];
size_t buffer_GrayScaleToGetPx_size = 7;
circular_fifo  fifo_GrayScaleToGetPx;
spinlock spinlock_GrayScaleToGetPx={.flag=0};
