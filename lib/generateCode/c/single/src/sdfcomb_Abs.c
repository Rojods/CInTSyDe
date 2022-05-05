/* Includes-------------------------- */
#include "../inc/datatype_definition.h"
/*
========================================
	Declare Extern Channal Variables
========================================
*/
/* Input FIFO */
extern fifo_GrayScaleToAbs;
extern fifo_AbsY;
extern fifo_AbsX;
extern fifo_absysig;
extern fifo_absxsig;
/* Output FIFO */
/*
========================================
	Actor Function
========================================
*/			
inline void actor_Abs(){
	/* Initilize Memory      */
	UInt16 offsetX; 
	UInt16 offsetY; 
	Array2OfUInt16 dims; 
	ArrayXOfArrayXOfDoubleType system_img_sink_address = system_img_sink; 
	DoubleType resy; 
	DoubleType resx; 
	/* Read From Input Port  */
	read_non_blocking_DoubleType(&fifo_absxsig,&resx);
	read_non_blocking_DoubleType(&fifo_absysig,&resy);
	for(int i=0;i<2;++i){
		read_non_blocking_UInt16(&fifo_GrayScaleToAbs,&dims[i]);
	}
	read_non_blocking_UInt16(&fifo_AbsX,&offsetX);
	read_non_blocking_UInt16(&fifo_AbsY,&offsetY);
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
	write_non_blocking_UInt16(&fifo_AbsX,offsetX);
	write_non_blocking_UInt16(&fifo_AbsY,offsetY);
}
