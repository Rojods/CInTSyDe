
#include "../../datatype/datatype_definition.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
#include "sdfchannel_GrayScaleY.h"
#include <cheap_s.h>

	/* Channel On One Processor */
		circular_fifo fifo_GrayScaleY;
		volatile UInt16 buffer_GrayScaleY[2];
		int channel_GrayScaleY_size=1;
		/*Because of circular fifo, the buffer_size=channel_size+1 */
		int buffer_GrayScaleY_size = 2;						
