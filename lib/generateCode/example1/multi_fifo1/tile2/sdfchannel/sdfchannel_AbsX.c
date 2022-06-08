
#include "../../circular_fifo_lib/spinlock.h"
#include "../../datatype/datatype_definition.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
#include "sdfchannel_AbsX.h"
#include <cheap_s.h>

	/* Channel On One Processor */
		volatile UInt16 buffer_AbsX[2];
		unsigned int channel_AbsX_size = 1;
		unsigned int buffer_AbsX_size = 2; // Because of circular fifo, the buffer_size=channel_size+1 
		circular_fifo_UInt16 fifo_AbsX;
		circular_fifo fifo_AbsX;
		volatile UInt16 buffer_AbsX[2];
		int channel_AbsX_size=1;
		/*Because of circular fifo, the buffer_size=channel_size+1 */
		int buffer_AbsX_size = 2;						
