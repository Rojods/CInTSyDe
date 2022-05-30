/* Includes */
#include "../inc/config.h"
#include "../inc/datatype_definition.h"
#include "../inc/circular_fifo_lib.h"
#include "../inc/sdfcomb_GrayScale.h"

/*
========================================
Declare Extern Channal Variables
========================================
*/
/* Input FIFO */

extern ref_fifo fifo_GrayScaleX;
extern spinlock spinlock_GrayScaleX;	

extern ref_fifo fifo_GrayScaleY;
extern spinlock spinlock_GrayScaleY;	
/* Output FIFO */
extern ref_fifo fifo_GrayScaleToAbs;
extern spinlock spinlock_GrayScaleToAbs;
extern ref_fifo fifo_GrayScaleToGetPx;
extern spinlock spinlock_GrayScaleToGetPx;
/*
========================================
	Declare Extern Global Variables
========================================
*/			
extern ArrayXOfArrayXOfDoubleType system_img_source_global;
extern UInt16 dimX_global;
extern UInt16 dimY_global;
	
/*
========================================
	Actor Function
========================================
*/	
/*  initialize memory*/
static	UInt16 offsetX; 
static	Array2OfUInt16 dimsOut; 
static	UInt16 offsetY; 
static	Array6OfDoubleType gray; 
void actor_GrayScale(){

	ArrayXOfArrayXOfDoubleType system_img_source_address = system_img_source_global; 
	UInt16 dimY = dimY_global; 
	UInt16 dimX = dimX_global; 
	/* Read From Input Port  */
	int ret=0;
	{
		void* tmp_addr;
		read_non_blocking(&fifo_GrayScaleX,&tmp_addr);
		offsetX= *((UInt16 *)tmp_addr);
	}
	
	{
		void* tmp_addr;
		read_non_blocking(&fifo_GrayScaleY,&tmp_addr);
		offsetY= *((UInt16 *)tmp_addr);
	}
	

	
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
		write_non_blocking(&fifo_GrayScaleToGetPx,(void*)&gray[i]);		
								
	}
	
	write_non_blocking(&fifo_GrayScaleX,(void*)&offsetX);
							
	write_non_blocking(&fifo_GrayScaleY,(void*)&offsetY);
							
	for(int i=0;i<2;++i){
		write_non_blocking(&fifo_GrayScaleToAbs,(void*)&dimsOut[i]);		
								
	}
	

}
