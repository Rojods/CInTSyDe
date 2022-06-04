	/* Includes-------------------------- */
	#include "../inc/config.h"
	#include "../inc/datatype_definition.h"
	#include "../inc/circular_fifo_lib.h"
	#include <cheap_s.h>
	#include "../inc/sdfcomb_p2.h"
	
	/*
	========================================
	Declare Extern Channal Variables
	========================================
	*/
	/* Input FIFO */
	extern volatile cheap const fifo_admin_s1;
	extern volatile uint32 * const fifo_data_s1;	
					
	/* Output FIFO */
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
void actor_p2(){

/*  initialize memory*/
uint32 b; 
	
	/* Read From Input Port  */
				int ret=0;
	{
		volatile uint32 *tmp_ptrs[1];
		while ((cheap_claim_tokens (fifo_admin_s1, (volatile void **) tmp_ptrs, 1)) < 1)
	 		cheap_release_all_claimed_tokens (fifo_admin_s1);
	 		 		
		b=*tmp_ptrs[0];
		cheap_release_spaces (fifo_admin_s1, 1);
	}

	
	/* Inline Code           */
				/* in combFunction p2impl */
				intc=2*b;
	
	/* Write To Output Ports */

	}
