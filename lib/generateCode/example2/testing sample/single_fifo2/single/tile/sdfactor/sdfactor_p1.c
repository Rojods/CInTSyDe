/* Includes */

#include "../../datatype/datatype_definition.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
#include "sdfactor_p1.h"

/*
========================================
Declare Extern Channal Variables
========================================
*/
/* Input FIFO */
extern circular_fifo fifo_s6;
extern circular_fifo fifo_s_in;

/* Output FIFO */
extern circular_fifo fifo_s1;
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

	UInt32 s6; 
	Array2OfUInt32Type s_in; 
	UInt32 s1; 
	/* Read From Input Port  */
	int ret=0;
	read_fifo(&fifo_s_in,(void*)s_in,2);
	read_fifo(&fifo_s6,(void*)&s6,1);
	
		printf("p1 reads s_in:  %d, %d\n",s_in[0],s_in[1]);
		
	/* Inline Code           */
	/* in combFunction p1Body */
	s1=s_in[0]+s_in[1]+s6;
		
	/* Write To Output Ports */
	 
	write_fifo(&fifo_s1,(void*)&s1,1);
	
}
