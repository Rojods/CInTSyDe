
#include "sdfchannel_GrayScaleToAbs.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
volatile UInt16 buffer_GrayScaleToAbs[3];
int channel_GrayScaleToAbs_size=2;
/*Because of circular fifo, the buffer_size=channel_size+1 */
int buffer_GrayScaleToAbs_size = 3;
circular_fifo_UInt16 fifo_GrayScaleToAbs;

