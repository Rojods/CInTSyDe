	/* Includes-------------------------- */
	
	#include "../../datatype/datatype_definition.h"
	#include "../../circular_fifo_lib/circular_fifo_lib.h"
	#include <cheap_s.h>
	#include "sdfactor_p4.h"
	
	/*
	========================================
	Declare Extern Channal Variables
	========================================
	*/
	/* Input FIFO */
	extern volatile cheap const fifo_admin_s2;
	extern volatile UInt32 * const fifo_data_s2;	
					
	/* Output FIFO */
	extern circular_fifo fifo_s4;
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
void actor_p4(){

/*  initialize memory*/
UInt32 s4; 
UInt32 s2; 
	
	/* Read From Input Port  */
				int ret=0;
	{
		volatile UInt32 *tmp_ptrs[1];
		while ((cheap_claim_tokens (fifo_admin_s2, (volatile void **) tmp_ptrs, 1)) < 1)
	 		cheap_release_all_claimed_tokens (fifo_admin_s2);
	 		 		
		s2=*tmp_ptrs[0];
		cheap_release_spaces (fifo_admin_s2, 1);
	}

	
	/* Inline Code           */
	/* in combFunction p4Body */
	s4=s2;
	intout[3];
	out[0]=s2;
	out[1]=s2+1;
	out[2]=s2+2;
	
	/* Write To Output Ports */
				write_fifo(&fifo_s4,(void*)&s4,1);

	}
