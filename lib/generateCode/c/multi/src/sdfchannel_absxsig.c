#include "../inc/config.h"
#include "../inc/spinlock.h"
#include "../inc/datatype_definition.h"
#include "../inc/circular_fifo_lib.h"
#include <cheap_s.h>
#define tile0_comm1 0x80020000
	/* Channel Between Two Processors */
	 volatile cheap const fifo_admin_absxsig=(cheap) ABSXSIG_ADDR;
	 volatile DoubleType * const fifo_data_absxsig=(DoubleType  *)((cheap) ABSXSIG_ADDR +1);			 
	 unsigned int buffer_absxsig_size=1;
	 unsigned int token_absxsig_size=1	;
