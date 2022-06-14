/* Includes */

#include "../../datatype/datatype_definition.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
#include "sdfactor_Gx.h"

/*
========================================
Declare Extern Channal Variables
========================================
*/
/* Input FIFO */
extern circular_fifo_DoubleType fifo_gxsig;

/* Output FIFO */
extern circular_fifo_DoubleType fifo_absxsig;
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
	
	void actor_Gx(){
		
		/*  initialize memory*/

	DoubleType gx; 
	Array6OfDoubleType imgBlockX; 
	/* Read From Input Port  */
	int ret=0;
	read_fifo_DoubleType(&fifo_gxsig, imgBlockX,6);
	
		
	/* Inline Code           */
	/* in combFunction GxImpl */
	gx=0;
	gx=gx-imgBlockX[0];
	gx=gx+imgBlockX[1];
	gx=gx-2.0*imgBlockX[2];
	gx=gx+2.0*imgBlockX[3];
	gx=gx-imgBlockX[4];
	gx=gx+imgBlockX[5];
		
	/* Write To Output Ports */
	write_fifo_DoubleType(&fifo_absxsig,&gx,1);
	 
	
	}
