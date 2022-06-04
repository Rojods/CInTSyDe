	/* Includes-------------------------- */
	#include "../inc/config.h"
	#include "../inc/datatype_definition.h"
	#include "../inc/circular_fifo_lib.h"
	#include <cheap_s.h>
	#include "../inc/sdfcomb_p1.h"
	
	/*
	========================================
	Declare Extern Channal Variables
	========================================
	*/
	/* Input FIFO */
	/* Output FIFO */
	extern volatile cheap const fifo_admin_s1;
	extern volatile uint32 * const fifo_data_s1;	
					
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
void actor_p1(){

/*  initialize memory*/
uint32 a; 
	
	/* Read From Input Port  */
				int ret=0;

	
	/* Inline Code           */
				/* in combFunction p1impl */
				a=5;
	
	/* Write To Output Ports */
				{
					volatile uint32 *tmp_ptrs[1];
					while ((cheap_claim_spaces (fifo_admin_s1, (volatile void **) &tmp_ptrs[0], 1)) < 1)
						cheap_release_all_claimed_spaces (fifo_admin_s1);
					
					*tmp_ptrs[0]=a;
					
					cheap_release_tokens (fifo_admin_s1, 1);
				}

	}
