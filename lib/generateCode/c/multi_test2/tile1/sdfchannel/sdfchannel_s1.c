
#include "../../circular_fifo_lib/spinlock.h"
#include "../../datatype/datatype_definition.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
#include "sdfchannel_s1.h"
#include <cheap_s.h>

	/* Channel Between Two Processors */
	 volatile cheap const fifo_admin_s1=(cheap) S1_ADDR;
	 volatile uint32 * const fifo_data_s1=(uint32  *)((cheap) S1_ADDR +1);			 
	 unsigned int buffer_s1_size=1;
	 unsigned int token_s1_size=4	;
