	/* Includes-------------------------- */
	
	#include "../../datatype/datatype_definition.h"
	#include "../../circular_fifo_lib/circular_fifo_lib.h"
	#include <cheap_s.h>
	#include "sdfactor_p5.h"
	
	/*
	========================================
	Declare Extern Channal Variables
	========================================
	*/
	/* Input FIFO */
	extern circular_fifo_UInt32 fifo_s4;
	/* Output FIFO */
	extern volatile cheap const fifo_admin_s5;
	extern volatile UInt32 * const fifo_data_s5;	
					
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
void actor_p5(){

/*  initialize memory*/
UInt32 s4; 
UInt32 s5; 
	
	/* Read From Input Port  */
				int ret=0;
	read_fifo_UInt32(&fifo_s4, &s4,1);

	
	/* Inline Code           */
	/* in combFunction p5Body */
	s5=s4+1;
	
	/* Write To Output Ports */
				{
					volatile UInt32 *tmp_ptrs[1];
					while ((cheap_claim_spaces (fifo_admin_s5, (volatile void **) &tmp_ptrs[0], 1)) < 1)
						cheap_release_all_claimed_spaces (fifo_admin_s5);
					
					*tmp_ptrs[0]=s5;
					
					cheap_release_tokens (fifo_admin_s5, 1);
				}

	}
