
#include "../../datatype/datatype_definition.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
#include "sdfchannel_AbsY.h"
#include <cheap_s.h>

	/* Channel On One Processor */
		circular_fifo fifo_AbsY;
		volatile UInt16 buffer_AbsY[2];
		int channel_AbsY_size=1;
		/*Because of circular fifo, the buffer_size=channel_size+1 */
		int buffer_AbsY_size = 2;						
