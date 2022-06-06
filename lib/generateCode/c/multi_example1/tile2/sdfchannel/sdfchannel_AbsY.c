
#include "../../circular_fifo_lib/spinlock.h"
#include "../../datatype/datatype_definition.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
#include "sdfchannel_AbsY.h"
#include <cheap_s.h>

	/* Channel On One Processor */
	volatile UInt16 buffer_AbsY[2];
	unsigned int channel_AbsY_size = 1;
	unsigned int buffer_AbsY_size = 2; // Because of circular fifo, the buffer_size=channel_size+1 
	circular_fifo_UInt16 fifo_AbsY;
	spinlock spinlock_AbsY={.flag=0};	
