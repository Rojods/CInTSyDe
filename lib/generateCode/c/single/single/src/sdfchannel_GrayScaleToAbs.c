			#include "../inc/config.h"
			#if SINGLECORE==1
			#include "../inc/circular_fifo_lib.h"
				volatile UInt16 buffer_GrayScaleToAbs[3];
				int channel_GrayScaleToAbs_size=2;
				/*Because of circular fifo, the buffer_size=channel_size+1 */
				int buffer_GrayScaleToAbs_size = 3;
				circular_fifo_UInt16 fifo_GrayScaleToAbs;
				spinlock spinlock_GrayScaleToAbs={.flag=0};
			#endif
////////////////////////////////////////////////////////////////////			
			#if MULTICORE==1
volatile cheap const fifo_admin_GrayScaleToAbs;
unsigned int buffer_GrayScaleToAbs_size=2;
unsigned int token_GrayScaleToAbs_size=1	;
volatile UInt16 buffer_GrayScaleToAbs[2];			
			#endif
