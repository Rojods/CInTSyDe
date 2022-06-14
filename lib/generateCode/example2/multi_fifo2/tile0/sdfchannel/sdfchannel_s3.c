
#include "../../datatype/datatype_definition.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
#include "sdfchannel_s3.h"
#include <cheap_s.h>

	/* Channel On One Processor */
		circular_fifo fifo_s3;
		volatile UInt32 buffer_s3[3];
		int channel_s3_size=2;
		/*Because of circular fifo, the buffer_size=channel_size+1 */
		int buffer_s3_size = 3;						
