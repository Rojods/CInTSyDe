
#include "../../datatype/datatype_definition.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
#include "sdfchannel_GrayScaleX.h"
#include <cheap_s.h>

	/* Channel On One Processor */
		circular_fifo fifo_GrayScaleX;
		volatile UInt16 buffer_GrayScaleX[2];
		int channel_GrayScaleX_size=1;
		/*Because of circular fifo, the buffer_size=channel_size+1 */
		int buffer_GrayScaleX_size = 2;						
