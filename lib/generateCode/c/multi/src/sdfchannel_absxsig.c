			#include "../inc/config.h"
			#if SINGLECORE==1
			#include "../inc/circular_fifo_lib.h"
				volatile DoubleType buffer_absxsig[2];
				int channel_absxsig_size=1;
				/*Because of circular fifo, the buffer_size=channel_size+1 */
				int buffer_absxsig_size = 2;
				circular_fifo_DoubleType fifo_absxsig;
				spinlock spinlock_absxsig={.flag=0};
			#endif
////////////////////////////////////////////////////////////////////			
			#if MULTICORE==1
volatile cheap const fifo_admin_absxsig;
unsigned int buffer_absxsig_size=1;
unsigned int token_absxsig_size=1	;
volatile DoubleType buffer_absxsig[1];			
			#endif
