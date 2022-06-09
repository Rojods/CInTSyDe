
#include "sdfchannel_s4.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
volatile UInt32 buffer_s4[2];
int channel_s4_size=1;
/*Because of circular fifo, the buffer_size=channel_size+1 */
int buffer_s4_size = 2;
circular_fifo_UInt32 fifo_s4;

