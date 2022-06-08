
#include "sdfchannel_absxsig.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
circular_fifo fifo_absxsig;
volatile DoubleType buffer_absxsig[2];
int channel_absxsig_size=1;
/*Because of circular fifo, the buffer_size=channel_size+1 */
int buffer_absxsig_size = 2;


