#include "../inc/config.h"
#include "../inc/spinlock.h"
#include "../inc/datatype_definition.h"
#include "../inc/circular_fifo_lib.h"
	/* Channel Between Two Processors */
	 volatile cheap const fifo_admin_gysig;
	 volatile DoubleType * const fifo_data_gysig;
	// volatile token_t *fifo_ptrs[6];				 
	 unsigned int buffer_gysig_size=6;
	 unsigned int token_gysig_size=1	;
