
#include "../../circular_fifo_lib/spinlock.h"
#include "../../datatype/datatype_definition.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
#include "sdfchannel_s3.h"
#include <cheap_s.h>

	/* Channel On One Processor */
		volatile UInt32 buffer_s3[3];
		int channel_s3_size=2;
		int buffer_s3_size = 3; //Because of circular fifo, the buffer_size=channel_size+1 
		circular_fifo_UInt32 fifo_s3;
