
#include "../../datatype/datatype_definition.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
#include "sdfchannel_s6.h"
#include <cheap_s.h>

	/* Channel On One Processor */
		circular_fifo fifo_s6;
		volatile UInt32 buffer_s6[3];
		int channel_s6_size=2;
		/*Because of circular fifo, the buffer_size=channel_size+1 */
		int buffer_s6_size = 3;						
