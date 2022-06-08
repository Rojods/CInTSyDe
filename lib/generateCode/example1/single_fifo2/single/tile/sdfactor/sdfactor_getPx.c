/* Includes */

#include "../../datatype/datatype_definition.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
#include "sdfactor_getPx.h"

/*
========================================
Declare Extern Channal Variables
========================================
*/
/* Input FIFO */
extern circular_fifo fifo_GrayScaleToGetPx;

/* Output FIFO */
extern circular_fifo fifo_gysig;
extern circular_fifo fifo_gxsig;
/*
========================================
	Declare Extern Global Variables
========================================
*/			
extern Array1000OfArrayOfDouble inputImage;
	
/*
========================================
		Actor Function
========================================
*/	

void actor_getPx(){
	
	/*  initialize memory*/

	Array6OfDoubleType gray; 
	Array6OfDoubleType imgBlockY; 
	Array6OfDoubleType imgBlockX; 
	/* Read From Input Port  */
	int ret=0;
	read_fifo(&fifo_GrayScaleToGetPx,(void*)gray,6);
	
		
	/* Inline Code           */
	/* in combFunction getPxImpl1 */
	imgBlockX[0]=gray[0];
	imgBlockX[1]=gray[1];
	imgBlockX[2]=gray[2];
	imgBlockX[3]=gray[3];
	imgBlockX[4]=gray[4];
	imgBlockX[5]=gray[5];
	/* in combFunction getPxImpl2 */
	imgBlockY[0]=gray[0];
	imgBlockY[1]=gray[1];
	imgBlockY[2]=gray[2];
	imgBlockY[3]=gray[3];
	imgBlockY[4]=gray[4];
	imgBlockY[5]=gray[5];
		
	/* Write To Output Ports */
	write_fifo(&fifo_gysig,imgBlockY,6);
		
	write_fifo(&fifo_gxsig,imgBlockX,6);
		
	
}
