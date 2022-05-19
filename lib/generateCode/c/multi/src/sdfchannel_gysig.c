			#include "../inc/config.h"
			#if SINGLECORE==1
			#include "../inc/circular_fifo_lib.h"
				volatile DoubleType buffer_gysig[7];
				int channel_gysig_size=6;
				/*Because of circular fifo, the buffer_size=channel_size+1 */
				int buffer_gysig_size = 7;
				circular_fifo_DoubleType fifo_gysig;
				spinlock spinlock_gysig={.flag=0};
			#endif
////////////////////////////////////////////////////////////////////			
			#if MULTICORE==1
volatile cheap const fifo_admin_gysig;
unsigned int buffer_gysig_size=6;
unsigned int token_gysig_size=1	;
volatile DoubleType buffer_gysig[6];			
			#endif
