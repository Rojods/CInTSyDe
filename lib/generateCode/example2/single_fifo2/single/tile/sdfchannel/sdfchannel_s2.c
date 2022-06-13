
#include "sdfchannel_s2.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
		circular_fifo fifo_s2;
		volatile UInt32 buffer_s2[2];
		int channel_s2_size=1;
		/*Because of circular fifo, the buffer_size=channel_size+1 */
		int buffer_s2_size = 2;
		
			
