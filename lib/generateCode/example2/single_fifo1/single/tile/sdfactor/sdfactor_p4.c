/* Includes */

#include "../../datatype/datatype_definition.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
#include "sdfactor_p4.h"

/*
========================================
Declare Extern Channal Variables
========================================
*/
/* Input FIFO */
extern circular_fifo_UInt32 fifo_s2;

/* Output FIFO */
extern circular_fifo_UInt32 fifo_s4;
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
	read_fifo_UInt32(&fifo_s2, &s2,1);
	
		
	/* Inline Code           */
	/* in combFunction p4Body */
	s4=s2;
	intout[3];
	out[0]=s2;
	out[1]=s2+1;
	out[2]=s2+2;
		
	/* Write To Output Ports */
	write_fifo_UInt32(&fifo_s4,&s4,1);
	 
	
}
