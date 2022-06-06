
#include "../../circular_fifo_lib/spinlock.h"
#include "../../datatype/datatype_definition.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
#include "sdfchannel_GrayScaleToGetPx.h"
#include <cheap_s.h>

	/* Channel On One Processor */
	volatile DoubleType buffer_GrayScaleToGetPx[7];
	int channel_GrayScaleToGetPx_size=6;
	int buffer_GrayScaleToGetPx_size = 7; //Because of circular fifo, the buffer_size=channel_size+1 
	circular_fifo_DoubleType fifo_GrayScaleToGetPx;
	spinlock spinlock_GrayScaleToGetPx={.flag=0};
