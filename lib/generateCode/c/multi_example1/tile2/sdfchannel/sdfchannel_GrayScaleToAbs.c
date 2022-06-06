
#include "../../circular_fifo_lib/spinlock.h"
#include "../../datatype/datatype_definition.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
#include "sdfchannel_GrayScaleToAbs.h"
#include <cheap_s.h>

	/* Channel Between Two Processors */
	 volatile cheap const fifo_admin_GrayScaleToAbs=(cheap) GRAYSCALETOABS_ADDR;
	 volatile UInt16 * const fifo_data_GrayScaleToAbs=(UInt16  *)((cheap) GRAYSCALETOABS_ADDR +1);			 
	 unsigned int buffer_GrayScaleToAbs_size=2;
	 unsigned int token_GrayScaleToAbs_size=2;
	 
