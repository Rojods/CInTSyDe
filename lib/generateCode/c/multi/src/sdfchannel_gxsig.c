#include "../inc/config.h"
#include "../inc/spinlock.h"
#include "../inc/datatype_definition.h"
#include "../inc/circular_fifo_lib.h"
	/* Channel Between Two Processors */
	 volatile cheap const fifo_admin_gxsig;
	 volatile DoubleType * const fifo_data_gxsig;
	// volatile token_t *fifo_ptrs[6];				 
	 unsigned int buffer_gxsig_size=6;
	 unsigned int token_gxsig_size=1	;
