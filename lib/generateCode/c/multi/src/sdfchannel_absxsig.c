#include "../inc/config.h"
#include "../inc/spinlock.h"
#include "../inc/datatype_definition.h"
#include "../inc/circular_fifo_lib.h"
	/* Channel Between Two Processors */
	 volatile cheap const fifo_admin_absxsig;
	 volatile DoubleType * const fifo_data_absxsig;
	// volatile token_t *fifo_ptrs[1];				 
	 unsigned int buffer_absxsig_size=1;
	 unsigned int token_absxsig_size=1	;
