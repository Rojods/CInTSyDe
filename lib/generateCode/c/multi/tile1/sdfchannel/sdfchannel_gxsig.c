
#include "../../circular_fifo_lib/spinlock.h"
#include "../../datatype/datatype_definition.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
#include "sdfchannel_gxsig.h"
#include <cheap_s.h>

	/* Channel Between Two Processors */
	 volatile cheap const fifo_admin_gxsig=(cheap) GXSIG_ADDR;
	 volatile DoubleType * const fifo_data_gxsig=(DoubleType  *)((cheap) GXSIG_ADDR +1);			 
	 unsigned int buffer_gxsig_size=6;
	 unsigned int token_gxsig_size=1	;
