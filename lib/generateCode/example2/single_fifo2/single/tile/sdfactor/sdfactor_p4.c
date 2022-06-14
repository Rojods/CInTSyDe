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
extern circular_fifo fifo_s2;

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
	read_fifo(&fifo_s2,(void*)&s2,1);
	
		
	/* Inline Code           */
	/* in combFunction p4Body */
	s4=s2;
	int out[3];
	out[0]=s2;
	out[1]=s2+1;
	out[2]=s2+2;
		
	/* Write To Output Ports */
	 
	write_fifo(&fifo_s4,(void*)&s4,1);
	printf("out: %d,%d,%d\n",out[0],out[1],out[2]);
	}
