#include "../inc/config.h"
#include "../inc/spinlock.h"
#include "../inc/datatype_definition.h"
#include "../inc/circular_fifo_lib.h"
#include <cheap_s.h>
#define tile0_comm1 0x80020000
	/* Channel Between Two Processors */
	 volatile cheap const fifo_admin_gysig=(cheap) GYSIG_ADDR;
	 volatile DoubleType * const fifo_data_gysig=(DoubleType  *)((cheap) GYSIG_ADDR +1);			 
	 unsigned int buffer_gysig_size=6;
	 unsigned int token_gysig_size=1	;
