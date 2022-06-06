	/* Includes-------------------------- */
	
	#include "../../datatype/datatype_definition.h"
	#include "../../circular_fifo_lib/circular_fifo_lib.h"
	#include <cheap_s.h>
	#include "sdfactor_Gy.h"
	
	/*
	========================================
	Declare Extern Channal Variables
	========================================
	*/
	/* Input FIFO */
	extern volatile cheap const fifo_admin_gysig;
	extern volatile DoubleType * const fifo_data_gysig;	
					
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
	{
		volatile DoubleType *tmp_ptrs[6];
		while ((cheap_claim_tokens (fifo_admin_gysig, (volatile void **) tmp_ptrs, 6)) < 6)
			 cheap_release_all_claimed_tokens (fifo_admin_gysig);								
		
		for(int i=0;i<6;++i){
			imgBlockY[i]=*tmp_ptrs[i];	
		}
		
		cheap_release_spaces (fifo_admin_gysig, 1);
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
