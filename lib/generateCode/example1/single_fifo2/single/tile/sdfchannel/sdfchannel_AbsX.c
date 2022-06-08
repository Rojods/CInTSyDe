
#include "sdfchannel_AbsX.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
circular_fifo fifo_AbsX;
volatile UInt16 buffer_AbsX[2];
int channel_AbsX_size=1;
/*Because of circular fifo, the buffer_size=channel_size+1 */
int buffer_AbsX_size = 2;
