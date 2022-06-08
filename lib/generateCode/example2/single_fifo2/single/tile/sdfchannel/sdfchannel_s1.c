
#include "sdfchannel_s1.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
circular_fifo fifo_s1;
volatile UInt32 buffer_s1[2];
int channel_s1_size=1;
/*Because of circular fifo, the buffer_size=channel_size+1 */
int buffer_s1_size = 2;


