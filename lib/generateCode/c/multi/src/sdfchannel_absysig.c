#include "../inc/config.h"
#include "../inc/spinlock.h"
#include "../inc/datatype_definition.h"
#include "../inc/circular_fifo_lib.h"
#include <cheap_s.h>
#define tile0_comm1 0x80020000
	/* Channel Between Two Processors */
	 volatile cheap const fifo_admin_absysig=(cheap) ABSYSIG_ADDR;
	 volatile DoubleType * const fifo_data_absysig=(DoubleType  *)((cheap) ABSYSIG_ADDR +1);			 
	 unsigned int buffer_absysig_size=1;
	 unsigned int token_absysig_size=1	;
