/* Includes-------------------------- */
#include "../inc/datatype_definition.h"
/*
========================================
	Declare Extern Channal Variables
========================================
*/

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
	read_non_blocking(&channel,&offsetXIn);
	read_non_blocking(&channel,&offsetYIn);
	read_non_blocking(&channel,&resy);
	read_non_blocking(&channel,&resx);
	for(int i=0;i<2;++i){
		read_non_blocking(&channel,dimsIn+i);
	}
	/* Inline Code           */
	//in combFunction AbsImpl
	if(resx<0.0)resx=-resx;
	if(resy<0.0)resy=-resy;
	if(offsetX>=dims[0]-2){offsetY+=1;
	offsetX=0;
	}if(offsetY>=dims[1]-2){offsetY=0;
	}system_img_sink_address[offsetX][offsetY]=resx+resy;

	/* Write To Output Ports */
	write(offsetYOut_channel);
	write(offsetXOut_channel);
}
