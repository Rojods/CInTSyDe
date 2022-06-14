	/* Includes-------------------------- */
	
	#include "../../datatype/datatype_definition.h"
	#include "../../circular_fifo_lib/circular_fifo_lib.h"
	#include <cheap_s.h>
	#include "sdfactor_p2.h"
	
	/*
	========================================
	Declare Extern Channal Variables
	========================================
	*/
	/* Input FIFO */
	extern circular_fifo_UInt32 fifo_s1;
	/* Output FIFO */
	extern volatile cheap const fifo_admin_s2;
	extern volatile UInt32 * const fifo_data_s2;	
					
	extern circular_fifo_UInt32 fifo_s3;
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
UInt32 s3; 
UInt32 s1; 
UInt32 s2; 
	
	/* Read From Input Port  */
				int ret=0;
	read_fifo_UInt32(&fifo_s1, &s1,1);

	
	/* Inline Code           */
	/* in combFunction p2Body */
	s2=s1;
	s3=s1+1;
	
	/* Write To Output Ports */
				{
					volatile UInt32 *tmp_ptrs[1];
					while ((cheap_claim_spaces (fifo_admin_s2, (volatile void **) &tmp_ptrs[0], 1)) < 1)
						cheap_release_all_claimed_spaces (fifo_admin_s2);
					
					*tmp_ptrs[0]=s2;
					
					cheap_release_tokens (fifo_admin_s2, 1);
				}
				write_fifo_UInt32(&fifo_s3,&s3,1);

	}
