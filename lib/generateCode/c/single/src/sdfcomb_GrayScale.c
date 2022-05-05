/* Includes-------------------------- */
#include "../inc/datatype_definition.h"
/*
========================================
	Declare Extern Channal Variables
========================================
*/
/* Input FIFO */
extern fifo_GrayScaleX;
extern fifo_GrayScaleY;
/* Output FIFO */
extern fifo_GrayScaleToAbs;
extern fifo_GrayScaleToGetPx;
/*
========================================
	Actor Function
========================================
*/			
inline void actor_GrayScale(){
	/* Initilize Memory      */
	UInt16 offsetX; 
	Array2OfUInt16 dimsOut; 
	UInt16 offsetY; 
	Array6OfDoubleType gray; 
	ArrayXOfArrayXOfDoubleType system_img_source_address = system_img_source; 
	UInt16 dimY = dimY; 
	UInt16 dimX = dimX; 
	/* Read From Input Port  */
	read_non_blocking_UInt16(&fifo_GrayScaleX,&offsetX);
	read_non_blocking_UInt16(&fifo_GrayScaleY,&offsetY);
	/* Inline Code           */
	/* in combFunction GrayScaleImpl */
	gray[0]=0.3125*system_img_source_address[offsetY+0][offsetX+0]+0.5625*system_img_source_address[offsetY+0][offsetX+1]+0.125*system_img_source_address[offsetY+0][offsetX+2];
	gray[1]=0.3125*system_img_source_address[offsetY+0][offsetX+2]+0.5625*system_img_source_address[offsetY+0][offsetX+3]+0.125*system_img_source_address[offsetY+0][offsetX+4];
	gray[2]=0.3125*system_img_source_address[offsetY+1][offsetX+0]+0.5625*system_img_source_address[offsetY+1][offsetX+1]+0.125*system_img_source_address[offsetY+1][offsetX+2];
	gray[3]=0.3125*system_img_source_address[offsetY+1][offsetX+2]+0.5625*system_img_source_address[offsetY+1][offsetX+3]+0.125*system_img_source_address[offsetY+1][offsetX+4];
	gray[4]=0.3125*system_img_source_address[offsetY+2][offsetX+0]+0.5625*system_img_source_address[offsetY+2][offsetX+1]+0.125*system_img_source_address[offsetY+2][offsetX+2];
	gray[5]=0.3125*system_img_source_address[offsetY+2][offsetX+2]+0.5625*system_img_source_address[offsetY+2][offsetX+3]+0.125*system_img_source_address[offsetY+2][offsetX+4];
	if(offsetX>=dimX-2){
	offsetY+=1;
	offsetX=0;
	}
	if(offsetY>=dimY-2){
	offsetY=0;
	}
	dimsOut[0]=dimX;
	dimsOut[1]=dimY;

	/* Write To Output Ports */
	for(int i=0;i<6;++i){
		write_non_blocking_DoubleType(&fifo_GrayScaleToGetPx,gray[i]);
	}
	write_non_blocking_UInt16(&fifo_GrayScaleX,offsetX);
	write_non_blocking_UInt16(&fifo_GrayScaleY,offsetY);
	for(int i=0;i<2;++i){
		write_non_blocking_UInt16(&fifo_GrayScaleToAbs,dimsOut[i]);
	}
}
