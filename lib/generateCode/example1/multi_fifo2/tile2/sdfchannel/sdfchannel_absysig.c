
#include "../../circular_fifo_lib/spinlock.h"
#include "../../datatype/datatype_definition.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
#include "sdfchannel_absysig.h"
#include <cheap_s.h>

	/* Channel On One Processor */
		circular_fifo fifo_absysig;
		volatile DoubleType buffer_absysig[2];
		int channel_absysig_size=1;
		/*Because of circular fifo, the buffer_size=channel_size+1 */
		int buffer_absysig_size = 2;						
