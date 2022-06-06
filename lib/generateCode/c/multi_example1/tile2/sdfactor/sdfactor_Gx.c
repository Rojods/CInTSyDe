	/* Includes-------------------------- */
	
	#include "../../datatype/datatype_definition.h"
	#include "../../circular_fifo_lib/circular_fifo_lib.h"
	#include <cheap_s.h>
	#include "sdfactor_Gx.h"
	
	/*
	========================================
	Declare Extern Channal Variables
	========================================
	*/
	/* Input FIFO */
	extern volatile cheap const fifo_admin_gxsig;
	extern volatile DoubleType * const fifo_data_gxsig;	
					
	/* Output FIFO */
	extern circular_fifo_DoubleType fifo_absxsig;
	extern spinlock spinlock_absxsig;
	
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
void actor_Gx(){

/*  initialize memory*/
DoubleType gx; 
Array6OfDoubleType imgBlockX; 
	
	/* Read From Input Port  */
				int ret=0;
	{
		volatile DoubleType *tmp_ptrs[6];
		while ((cheap_claim_tokens (fifo_admin_gxsig, (volatile void **) tmp_ptrs, 6)) < 6)
			 cheap_release_all_claimed_tokens (fifo_admin_gxsig);								
		
		for(int i=0;i<6;++i){
			imgBlockX[i]=*tmp_ptrs[i];	
		}
		
		cheap_release_spaces (fifo_admin_gxsig, 1);
	}

	
	/* Inline Code           */
				/* in combFunction GxImpl */
				gx=0;
				gx=gx-imgBlockX[0];
				gx=gx+imgBlockX[1];
				gx=gx-2.0*imgBlockX[2];
				gx=gx+2.0*imgBlockX[3];
				gx=gx-imgBlockX[4];
				gx=gx+imgBlockX[5];
	
	/* Write To Output Ports */
				#if ABSXSIG_BLOCKING==0
				write_non_blocking_DoubleType(&fifo_absxsig,gx);
				#else
				write_blocking_DoubleType(&fifo_absxsig,gx,&spinlock_absxsig);
				#endif

	}
