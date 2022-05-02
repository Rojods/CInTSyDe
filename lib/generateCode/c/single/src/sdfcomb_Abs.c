/* Includes-------------------------- */
#include "../inc/datatype_definition.h"
/*
========================================
	Declare Extern Channal Variables
========================================
*/
extern fifo_GrayScaleToAbs;
extern fifo_AbsY;
extern fifo_AbsX;
extern fifo_absysig;
extern fifo_absxsig;

/*
========================================
	Actor Function
========================================
*/			
inline void actor_Abs(){
	/* Initilize Memory      */
	Array2OfUInt16 dims; 
	UInt16 offsetXIn; 
	UInt16 offsetYIn; 
	ArrayXOfArrayXOfDoubleType system_img_sink_address; 
	DoubleType resy; 
	DoubleType resx; 
	UInt16 offsetYOut; 
	UInt16 offsetXOut; 
	
	/* Read From Input Port  */
	error system_img_sink_address;
	/* Inline Code           */
	//in combFunction AbsImpl
	if(resx<0.0)resx=-resx;
	if(resy<0.0)resy=-resy;
	if(offsetX>=dims[0]-2){offsetY+=1;
	offsetX=0;
	}if(offsetY>=dims[1]-2){offsetY=0;
	}system_img_sink_address[offsetX][offsetY]=resx+resy;

	/* Write To Output Ports */
	write_non_blocking(&fifo_AbsY,&offsetYOut);
	write_non_blocking(&fifo_AbsX,&offsetXOut);
}
