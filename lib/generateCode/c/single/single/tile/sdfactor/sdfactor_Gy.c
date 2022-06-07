/* Includes */

#include "../../datatype/datatype_definition.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
#include "sdfactor_Gy.h"

/*
========================================
Declare Extern Channal Variables
========================================
*/
/* Input FIFO */
extern circular_fifo_DoubleType fifo_gysig;
extern spinlock spinlock_gysig;	

/* Output FIFO */
extern circular_fifo_DoubleType fifo_absysig;
extern spinlock spinlock_absysig;
/*
========================================
	Declare Extern Global Variables
========================================
*/			
	
	/*
	========================================
		Actor Function
	========================================
	*/	
			
void actor_Gy(){
	
	/*  initialize memory*/
				DoubleType gy; 
				Array6OfDoubleType imgBlockY; 
		/* Read From Input Port  */
		int ret=0;
		for(int i=0;i<6;++i){
			#if GYSIG_BLOCKING==0
			ret=read_non_blocking_DoubleType(&fifo_gysig,&imgBlockY[i]);
			if(ret==-1){
				printf("fifo_gysig read error\n");
			}
			#else
			read_blocking_DoubleType(&fifo_gysig,&imgBlockY[i],&spinlock_gysig);
			#endif
		}
		
	
		
		/* Inline Code           */
		/* in combFunction GyImpl */
		gy=0;
		gy=gy+imgBlockY[0];
		gy=gy+2.0*imgBlockY[1];
		gy=gy+imgBlockY[2];
		gy=gy-imgBlockY[3];
		gy=gy-2.0*imgBlockY[4];
		gy=gy-imgBlockY[5];
		
		/* Write To Output Ports */
		#if ABSYSIG_BLOCKING==0
		write_non_blocking_DoubleType(&fifo_absysig,gy);
		#else
		write_blocking_DoubleType(&fifo_absysig,gy,&spinlock_absysig);
		#endif
	
	}
