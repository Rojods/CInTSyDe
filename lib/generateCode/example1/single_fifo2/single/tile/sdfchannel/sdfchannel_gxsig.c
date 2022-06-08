
#include "sdfchannel_gxsig.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
circular_fifo fifo_gxsig;
volatile DoubleType buffer_gxsig[7];
int channel_gxsig_size=6;
/*Because of circular fifo, the buffer_size=channel_size+1 */
int buffer_gxsig_size = 7;


