
#include "../../datatype/datatype_definition.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
#include "sdfchannel_s2.h"
#include <cheap_s.h>

	/* Channel Between Two Processors */
	 volatile cheap const fifo_admin_s2=(cheap) S2_ADDR;
	 volatile UInt32 * const fifo_data_s2=(UInt32  *)((cheap) S2_ADDR +1);			 
	 unsigned int buffer_s2_size=1;
	 unsigned int token_s2_size=1;
	 
	
