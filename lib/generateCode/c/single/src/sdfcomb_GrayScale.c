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
inline void actor_GrayScale(){
	/* Initilize Memory      */
	UInt16  offsetX;
	  dimsOut;
	UInt16  offsetY;
	Array6OfDoubleType  gray;
	ArrayXOfArrayXOfDoubleType  system_img_source_address;
	UInt16  dimY;
	UInt16  dimX;
	
	/* Read From Input Port  */
	read_nonblocking(offsetXIn_channel);
	read_nonblocking(offsetYIn_channel);
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
	}[0]=dimX;
	dimsOut[1]=dimY;

	/* Write To Output Ports */
	for(int i=0;i<2;++i){
		write(dimsOut_channel);
	}
	for(int i=0;i<6;++i){
		write(gray_channel);
	}
	write(offsetYOut_channel);
	write(offsetXOut_channel);
}
