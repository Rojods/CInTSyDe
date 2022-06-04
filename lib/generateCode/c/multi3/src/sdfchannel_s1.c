#include "../inc/config.h"
#include "../inc/spinlock.h"
#include "../inc/datatype_definition.h"
#include "../inc/circular_fifo_lib.h"
#include <cheap_s.h>
#define tile0_comm1 0x80020000
	/* Channel Between Two Processors */
	 volatile cheap const fifo_admin_s1=(cheap) S1_ADDR;
	 volatile uint32 * const fifo_data_s1=(uint32  *)((cheap) S1_ADDR +1);			 
	 unsigned int buffer_s1_size=1;
	 unsigned int token_s1_size=4	;
