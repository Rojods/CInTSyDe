/* Includes-------------------------- */
#include "../inc/datatype_definition.h"
/*
========================================
	Declare Extern Channal Variables
========================================
*/
extern fifo_GrayScaleX;
extern fifo_GrayScaleY;

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
	UInt16 offsetY; 
	ArrayXOfArrayXOfDoubleType system_img_source_address; 
	UInt16 dimY; 
	UInt16 dimX; 
	Array2OfUInt16 dimsOut; 
	Array6OfDoubleType gray; 
	
	/* Read From Input Port  */
	error system_img_source_address;
	/* Inline Code           */
	//in combFunction GrayScaleImpl
	gray[0]=0.3125*system_img_source_address[offsetY+0][offsetX+0]+0.5625*system_img_source_address[offsetY+0][offsetX+1]+0.125*system_img_source_address[offsetY+0][offsetX+2];
	gray[1]=0.3125*system_img_source_address[offsetY+0][offsetX+2]+0.5625*system_img_source_address[offsetY+0][offsetX+3]+0.125*system_img_source_address[offsetY+0][offsetX+4];
	gray[2]=0.3125*system_img_source_address[offsetY+1][offsetX+0]+0.5625*system_img_source_address[offsetY+1][offsetX+1]+0.125*system_img_source_address[offsetY+1][offsetX+2];
	gray[3]=0.3125*system_img_source_address[offsetY+1][offsetX+2]+0.5625*system_img_source_address[offsetY+1][offsetX+3]+0.125*system_img_source_address[offsetY+1][offsetX+4];
	gray[4]=0.3125*system_img_source_address[offsetY+2][offsetX+0]+0.5625*system_img_source_address[offsetY+2][offsetX+1]+0.125*system_img_source_address[offsetY+2][offsetX+2];
	gray[5]=0.3125*system_img_source_address[offsetY+2][offsetX+2]+0.5625*system_img_source_address[offsetY+2][offsetX+3]+0.125*system_img_source_address[offsetY+2][offsetX+4];
	if(offsetX>=dimX-2){offsetY+=1;
	offsetX=0;
	}if(offsetY>=dimY-2){offsetY=0;
	}dimsOut[0]=dimX;
	dimsOut[1]=dimY;

	/* Write To Output Ports */
	write_non_blocking(&fifo_GrayScaleX,&offsetX);
	for(int i=0;i<2;++i){
		write_non_blocking(&fifo_GrayScaleToAbs,&dimsOut[i]);
	}
	for(int i=0;i<6;++i){
		write_non_blocking(&fifo_GrayScaleToGetPx,&gray[i]);
	}
	write_non_blocking(&fifo_GrayScaleY,&offsetY);
}
