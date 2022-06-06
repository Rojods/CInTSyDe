
#include "../../circular_fifo_lib/spinlock.h"
#include "../../datatype/datatype_definition.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
#include "sdfchannel_absxsig.h"
#include <cheap_s.h>

	/* Channel On One Processor */
	volatile DoubleType buffer_absxsig[2];
	int channel_absxsig_size=1;
	int buffer_absxsig_size = 2; //Because of circular fifo, the buffer_size=channel_size+1 
	circular_fifo_DoubleType fifo_absxsig;
	spinlock spinlock_absxsig={.flag=0};
