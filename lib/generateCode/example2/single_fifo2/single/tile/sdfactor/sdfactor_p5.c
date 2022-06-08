/* Includes */

#include "../../datatype/datatype_definition.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
#include "sdfactor_p5.h"

/*
========================================
Declare Extern Channal Variables
========================================
*/
/* Input FIFO */
extern circular_fifo fifo_s4;

/* Output FIFO */
extern circular_fifo fifo_s5;
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
	read_fifo(&fifo_s4,(void*)&s4,1);
	
		
	/* Inline Code           */
	/* in combFunction p5Body */
	s5=s4+1;
		
	/* Write To Output Ports */
	 
	write_fifo(&fifo_s5,(void*)&s5,1);
	
}
