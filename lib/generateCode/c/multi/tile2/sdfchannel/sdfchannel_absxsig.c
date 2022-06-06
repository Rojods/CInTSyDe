
#include "../../circular_fifo_lib/spinlock.h"
#include "../../datatype/datatype_definition.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
#include "sdfchannel_absxsig.h"
#include <cheap_s.h>

	/* Channel Between Two Processors */
	 volatile cheap const fifo_admin_absxsig=(cheap) ABSXSIG_ADDR;
	 volatile DoubleType * const fifo_data_absxsig=(DoubleType  *)((cheap) ABSXSIG_ADDR +1);			 
	 unsigned int buffer_absxsig_size=1;
	 unsigned int token_absxsig_size=1	;
