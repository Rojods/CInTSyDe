	/* Includes-------------------------- */
	#include "../inc/config.h"
	#include "../inc/datatype_definition.h"
	#include "../inc/circular_fifo_lib.h"
	#include <cheap_s.h>
	#include "../inc/sdfcomb_Gx.h"
	
	/*
	========================================
	Declare Extern Channal Variables
	========================================
	*/
	/* Input FIFO */
	extern volatile cheap const fifo_admin_gxsig;
	extern volatile DoubleType * const fifo_data_gxsig;	
					
	/* Output FIFO */
	extern volatile cheap const fifo_admin_absxsig;
	extern volatile DoubleType * const fifo_data_absxsig;	
					
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
			imgBlockX[i]=tmp_ptrs[i];	
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
				{
					volatile DoubleType *tmp_ptrs[1];
					while ((cheap_claim_spaces (fifo_admin_absxsig, (volatile void **) &tmp_ptrs[0], 1)) < 1)
						cheap_release_all_claimed_spaces (fifo_admin_absxsig);
					
					*tmp_ptrs[0]=gx;
					
					cheap_release_tokens (fifo_admin_absxsig, 1);
				}

	}
