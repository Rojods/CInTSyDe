	/* Includes-------------------------- */
	
	#include "../../datatype/datatype_definition.h"
	#include "../../circular_fifo_lib/circular_fifo_lib.h"
	#include <cheap_s.h>
	#include "sdfactor_GrayScale.h"
	
	/*
	========================================
	Declare Extern Channal Variables
	========================================
	*/
	/* Input FIFO */
	extern circular_fifo fifo_GrayScaleX;
	extern circular_fifo fifo_GrayScaleY;
	/* Output FIFO */
	extern volatile cheap const fifo_admin_GrayScaleToAbs;
	extern volatile UInt16 * const fifo_data_GrayScaleToAbs;	
					
	extern circular_fifo fifo_GrayScaleToGetPx;
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
void actor_GrayScale(){

/*  initialize memory*/
UInt16 offsetX; 
Array2OfUInt16 dimsOut; 
UInt16 offsetY; 
Array6OfDoubleType gray; 
ArrayXOfArrayXOfDoubleType system_img_source_address = system_img_source_global; 
UInt16 dimY = dimY_global; 
UInt16 dimX = dimX_global; 
	
	/* Read From Input Port  */
				int ret=0;
	read_fifo(&fifo_GrayScaleX,(void*)&offsetX,1);
	read_fifo(&fifo_GrayScaleY,(void*)&offsetY,1);

	
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
				
				write_fifo(&fifo_GrayScaleToGetPx,gray,6);
				write_fifo(&fifo_GrayScaleX,(void*)&offsetX,1);
				write_fifo(&fifo_GrayScaleY,(void*)&offsetY,1);
				{
					volatile UInt16 *tmp_ptrs[2];
					while ((cheap_claim_spaces (fifo_admin_GrayScaleToAbs, (volatile void **) &tmp_ptrs[0], 2)) < 2)
						cheap_release_all_claimed_spaces (fifo_admin_GrayScaleToAbs);
					
					for(int i=0;i<2;++i){
						*tmp_ptrs[i]=dimsOut[i];
					}
					
					cheap_release_tokens (fifo_admin_GrayScaleToAbs, 2);
				}						

	}
