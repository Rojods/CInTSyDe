	/* Includes-------------------------- */
	#include "../inc/config.h"
	#include "../inc/datatype_definition.h"
	#include "../inc/circular_fifo_lib.h"
	#include <cheap_s.h>
	#include "../inc/sdfcomb_Gy.h"
	
	/*
	========================================
	Declare Extern Channal Variables
	========================================
	*/
	/* Input FIFO */
	extern volatile cheap const fifo_admin_gysig;
	extern volatile DoubleType * const fifo_data_gysig;	
					
	/* Output FIFO */
	extern volatile cheap const fifo_admin_absysig;
	extern volatile DoubleType * const fifo_data_absysig;	
					
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
				{
					volatile DoubleType *tmp_ptrs[1];
					while ((cheap_claim_spaces (fifo_admin_absysig, (volatile void **) &tmp_ptrs[0], 1)) < 1)
						cheap_release_all_claimed_spaces (fifo_admin_absysig);
					
					*tmp_ptrs[0]=gy;
					
					cheap_release_tokens (fifo_admin_absysig, 1);
				}

	}
