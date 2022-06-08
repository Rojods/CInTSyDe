
#include "sdfchannel_gysig.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
circular_fifo fifo_gysig;
volatile DoubleType buffer_gysig[7];
int channel_gysig_size=6;
/*Because of circular fifo, the buffer_size=channel_size+1 */
int buffer_gysig_size = 7;


