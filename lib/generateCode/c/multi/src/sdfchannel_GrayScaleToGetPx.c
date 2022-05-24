#include "../inc/config.h"
#include "../inc/spinlock.h"
#include "../inc/datatype_definition.h"
#include "../inc/circular_fifo_lib.h"
	/* Channel Between Two Processors */
	 volatile cheap const fifo_admin_GrayScaleToGetPx;
	 volatile DoubleType * const fifo_data_GrayScaleToGetPx;
	// volatile token_t *fifo_ptrs[6];				 
	 unsigned int buffer_GrayScaleToGetPx_size=6;
	 unsigned int token_GrayScaleToGetPx_size=1	;
