/* Includes */

#include "../../datatype/datatype_definition.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
#include "sdfactor_Abs.h"

/*
========================================
Declare Extern Channal Variables
========================================
*/
/* Input FIFO */
extern circular_fifo fifo_GrayScaleToAbs;
extern circular_fifo fifo_AbsY;
extern circular_fifo fifo_AbsX;
extern circular_fifo fifo_absysig;
extern circular_fifo fifo_absxsig;

/* Output FIFO */
/*
========================================
	Declare Extern Global Variables
========================================
*/			
	extern ArrayXOfArrayXOfDoubleType system_img_sink_global;
	extern Array1000OfArrayOfDouble outputImage;
	
	/*
	========================================
			Actor Function
	========================================
	*/	
	
	void actor_Abs(){
		
		/*  initialize memory*/

	UInt16 offsetX; 
	UInt16 offsetY; 
	Array2OfUInt16 dims; 
	DoubleType resy; 
	DoubleType resx; 
	ArrayXOfArrayXOfDoubleType system_img_sink_address = system_img_sink_global; 
	/* Read From Input Port  */
	int ret=0;
	read_fifo(&fifo_absxsig,(void*)&resx,1);
	read_fifo(&fifo_absysig,(void*)&resy,1);
	read_fifo(&fifo_GrayScaleToAbs,(void*)dims,2);
	read_fifo(&fifo_AbsX,(void*)&offsetX,1);
	read_fifo(&fifo_AbsY,(void*)&offsetY,1);
	
		
	/* Inline Code           */
	/* in combFunction AbsImpl */
	if(resx<0.0)resx=-resx;
	if(resy<0.0)resy=-resy;
	if(offsetX>=dims[0]-2){
	offsetY+=1;
	offsetX=0;
	}
	if(offsetY>=dims[1]-2){
	offsetY=0;
	}
	system_img_sink_address[offsetX][offsetY]=resx+resy;
		
	/* Write To Output Ports */
	 
	write_fifo(&fifo_AbsX,(void*)&offsetX,1);
	 
	write_fifo(&fifo_AbsY,(void*)&offsetY,1);
	
	}
