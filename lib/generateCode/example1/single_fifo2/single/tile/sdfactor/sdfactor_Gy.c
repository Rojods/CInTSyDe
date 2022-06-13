/* Includes */

#include "../../datatype/datatype_definition.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
#include "sdfactor_Gy.h"

/*
========================================
Declare Extern Channal Variables
========================================
*/
/* Input FIFO */
extern circular_fifo fifo_gysig;

/* Output FIFO */
extern circular_fifo fifo_absysig;
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
	
	void actor_Gy(){
		
		/*  initialize memory*/

	DoubleType gy; 
	Array6OfDoubleType imgBlockY; 
	/* Read From Input Port  */
	int ret=0;
	read_fifo(&fifo_gysig,(void*)imgBlockY,6);
	
		
	/* Inline Code           */
	/* in combFunction GyImpl */
	gy=0;
	gy=gy+imgBlockY[0];
	gy=gy+2.0*imgBlockY[1];
	gy=gy+imgBlockY[2];
	gy=gy-imgBlockY[3];
	gy=gy-2.0*imgBlockY[4];
	gy=gy-imgBlockY[5];
		
	/* Write To Output Ports */
	 
	write_fifo(&fifo_absysig,(void*)&gy,1);
	
	}
