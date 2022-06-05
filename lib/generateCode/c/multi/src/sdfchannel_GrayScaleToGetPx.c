#include "../inc/config.h"
#include "../inc/spinlock.h"
#include "../inc/datatype_definition.h"
#include "../inc/circular_fifo_lib.h"
#include <cheap_s.h>
#define tile0_comm1 0x80020000
	/* Channel On One Processor */
	volatile DoubleType buffer_GrayScaleToGetPx[7];
	int channel_GrayScaleToGetPx_size=6;
	int buffer_GrayScaleToGetPx_size = 7; //Because of circular fifo, the buffer_size=channel_size+1 
	circular_fifo_DoubleType fifo_GrayScaleToGetPx;
	spinlock spinlock_GrayScaleToGetPx={.flag=0};
