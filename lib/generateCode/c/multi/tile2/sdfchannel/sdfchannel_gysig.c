
#include "../../circular_fifo_lib/spinlock.h"
#include "../../datatype/datatype_definition.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
#include "sdfchannel_gysig.h"
#include <cheap_s.h>

	/* Channel Between Two Processors */
	 volatile cheap const fifo_admin_gysig=(cheap) GYSIG_ADDR;
	 volatile DoubleType * const fifo_data_gysig=(DoubleType  *)((cheap) GYSIG_ADDR +1);			 
	 unsigned int buffer_gysig_size=6;
	 unsigned int token_gysig_size=1	;
