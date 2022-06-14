
#include "../../datatype/datatype_definition.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
#include "sdfchannel_s5.h"
#include <cheap_s.h>

	/* Channel Between Two Processors */
	 volatile cheap const fifo_admin_s5=(cheap) S5_ADDR;
	 volatile UInt32 * const fifo_data_s5=(UInt32  *)((cheap) S5_ADDR +1);			 
	 unsigned int buffer_s5_size=2;
	 unsigned int token_s5_size= sizeof(UInt32);
	
