
#include "sdfchannel_s5.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
		circular_fifo fifo_s5;
		volatile UInt32 buffer_s5[3];
		int channel_s5_size=2;
		/*Because of circular fifo, the buffer_size=channel_size+1 */
		int buffer_s5_size = 3;
		
			
