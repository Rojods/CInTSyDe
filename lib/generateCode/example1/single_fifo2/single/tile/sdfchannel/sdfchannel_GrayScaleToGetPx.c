
#include "sdfchannel_GrayScaleToGetPx.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
circular_fifo fifo_GrayScaleToGetPx;
volatile DoubleType buffer_GrayScaleToGetPx[7];
int channel_GrayScaleToGetPx_size=6;
/*Because of circular fifo, the buffer_size=channel_size+1 */
int buffer_GrayScaleToGetPx_size = 7;


