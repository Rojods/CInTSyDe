
#include "../../circular_fifo_lib/spinlock.h"
#include "../../datatype/datatype_definition.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
#include "sdfchannel_absysig.h"
#include <cheap_s.h>

	/* Channel Between Two Processors */
	 volatile cheap const fifo_admin_absysig=(cheap) ABSYSIG_ADDR;
	 volatile DoubleType * const fifo_data_absysig=(DoubleType  *)((cheap) ABSYSIG_ADDR +1);			 
	 unsigned int buffer_absysig_size=1;
	 unsigned int token_absysig_size=1	;
