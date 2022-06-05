#include "../inc/config.h"
#include "../inc/spinlock.h"
#include "../inc/datatype_definition.h"
#include "../inc/circular_fifo_lib.h"
#include <cheap_s.h>
#define tile0_comm1 0x80020000
	/* Channel Between Two Processors */
	 volatile cheap const fifo_admin_gxsig=(cheap) GXSIG_ADDR;
	 volatile DoubleType * const fifo_data_gxsig=(DoubleType  *)((cheap) GXSIG_ADDR +1);			 
	 unsigned int buffer_gxsig_size=6;
	 unsigned int token_gxsig_size=1	;
