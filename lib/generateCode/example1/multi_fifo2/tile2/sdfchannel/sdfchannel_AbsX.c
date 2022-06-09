
#include "../../datatype/datatype_definition.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
#include "sdfchannel_AbsX.h"
#include <cheap_s.h>

	/* Channel On One Processor */
		circular_fifo fifo_AbsX;
		volatile UInt16 buffer_AbsX[2];
		int channel_AbsX_size=1;
		/*Because of circular fifo, the buffer_size=channel_size+1 */
		int buffer_AbsX_size = 2;						
