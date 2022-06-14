
#include "sdfchannel_GrayScaleY.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
		circular_fifo fifo_GrayScaleY;
		volatile UInt16 buffer_GrayScaleY[2];
		int channel_GrayScaleY_size=1;
		/*Because of circular fifo, the buffer_size=channel_size+1 */
		int buffer_GrayScaleY_size = 2;
