#include "../inc/config.h"
#include "../inc/spinlock.h"
#include "../inc/datatype_definition.h"
#include "../inc/circular_fifo_lib.h"
	/* Channel Between Two Processors */
	 volatile cheap const fifo_admin_GrayScaleToAbs;
	 volatile UInt16 * const fifo_data_GrayScaleToAbs;
	// volatile token_t *fifo_ptrs[2];				 
	 unsigned int buffer_GrayScaleToAbs_size=2;
	 unsigned int token_GrayScaleToAbs_size=1	;
