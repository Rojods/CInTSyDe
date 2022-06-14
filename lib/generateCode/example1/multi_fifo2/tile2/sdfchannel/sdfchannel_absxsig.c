
#include "../../datatype/datatype_definition.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
#include "sdfchannel_absxsig.h"
#include <cheap_s.h>

	/* Channel On One Processor */
		circular_fifo fifo_absxsig;
		volatile DoubleType buffer_absxsig[2];
		int channel_absxsig_size=1;
		/*Because of circular fifo, the buffer_size=channel_size+1 */
		int buffer_absxsig_size = 2;						
