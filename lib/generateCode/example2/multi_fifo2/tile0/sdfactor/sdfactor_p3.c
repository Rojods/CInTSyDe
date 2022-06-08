	/* Includes-------------------------- */
	
	#include "../../datatype/datatype_definition.h"
	#include "../../circular_fifo_lib/circular_fifo_lib.h"
	#include <cheap_s.h>
	#include "sdfactor_p3.h"
	
	/*
	========================================
	Declare Extern Channal Variables
	========================================
	*/
	/* Input FIFO */
	extern volatile cheap const fifo_admin_s5;
	extern volatile UInt32 * const fifo_data_s5;	
					
	extern circular_fifo fifo_s3;
	/* Output FIFO */
	extern circular_fifo fifo_s6;
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
void actor_p3(){

/*  initialize memory*/
Array2OfUInt32Type s3; 
Array2OfUInt32Type s5; 
Array2OfUInt32Type s6; 
	
	/* Read From Input Port  */
				int ret=0;
	read_fifo(&fifo_s3,(void*)s3,2);
	
	
	{
		volatile UInt32 *tmp_ptrs[2];
		while ((cheap_claim_tokens (fifo_admin_s5, (volatile void **) tmp_ptrs, 2)) < 2)
			 cheap_release_all_claimed_tokens (fifo_admin_s5);								
		
		for(int i=0;i<2;++i){
			s5[i]=*tmp_ptrs[i];	
		}
		
		cheap_release_spaces (fifo_admin_s5, 1);
	}

	
	/* Inline Code           */
	/* in combFunction p3Body */
	s6[0]=s3[0]+s3[1];
	s6[1]=s5[0]+s5[1];
	
	/* Write To Output Ports */
				
				write_fifo(&fifo_s6,s6,2);

	}
