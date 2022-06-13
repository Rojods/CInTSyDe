/* Includes */

#include "../../datatype/datatype_definition.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
#include "sdfactor_p3.h"

/*
========================================
Declare Extern Channal Variables
========================================
*/
/* Input FIFO */
extern circular_fifo_UInt32 fifo_s5;
extern circular_fifo_UInt32 fifo_s3;

/* Output FIFO */
extern circular_fifo_UInt32 fifo_s6;
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
	read_fifo_UInt32(&fifo_s3, s3,2);
	read_fifo_UInt32(&fifo_s5, s5,2);
	
		
	/* Inline Code           */
	/* in combFunction p3Body */
	s6[0]=s3[0]+s3[1];
	s6[1]=s5[0]+s5[1];
		
	/* Write To Output Ports */
	write_fifo_UInt32(&fifo_s6,s6,2);
		
	
	}
