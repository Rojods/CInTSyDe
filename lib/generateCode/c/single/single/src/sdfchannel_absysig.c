			#include "../inc/config.h"
			#if SINGLECORE==1
			#include "../inc/circular_fifo_lib.h"
				volatile DoubleType buffer_absysig[2];
				int channel_absysig_size=1;
				/*Because of circular fifo, the buffer_size=channel_size+1 */
				int buffer_absysig_size = 2;
				circular_fifo_DoubleType fifo_absysig;
				spinlock spinlock_absysig={.flag=0};
			#endif
////////////////////////////////////////////////////////////////////			
			#if MULTICORE==1
volatile cheap const fifo_admin_absysig;
unsigned int buffer_absysig_size=1;
unsigned int token_absysig_size=1	;
volatile DoubleType buffer_absysig[1];			
			#endif
