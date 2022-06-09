
#include "sdfchannel_s_in.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
volatile UInt32 buffer_s_in[21];
int channel_s_in_size=20;
/*Because of circular fifo, the buffer_size=channel_size+1 */
int buffer_s_in_size = 21;
circular_fifo_UInt32 fifo_s_in;

