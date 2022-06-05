	/* Includes-------------------------- */
	#include "../inc/config.h"
	#include "../inc/datatype_definition.h"
	#include "../inc/circular_fifo_lib.h"
	#include <cheap_s.h>
	#include "../inc/sdfcomb_GrayScale.h"
	
	/*
	========================================
	Declare Extern Channal Variables
	========================================
	*/
	/* Input FIFO */
	circular_fifo_UInt16 fifo_GrayScaleX;
	spinlock spinlock_GrayScaleX={.flag=0};
	
	circular_fifo_UInt16 fifo_GrayScaleY;
	spinlock spinlock_GrayScaleY={.flag=0};
	
	/* Output FIFO */
	extern volatile cheap const fifo_admin_GrayScaleToAbs;
	extern volatile UInt16 * const fifo_data_GrayScaleToAbs;	
					
	circular_fifo_DoubleType fifo_GrayScaleToGetPx;
	spinlock spinlock_GrayScaleToGetPx={.flag=0};
	
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
	#if GRAYSCALEX_BLOCKING==0
	ret=read_non_blocking_UInt16(&fifo_GrayScaleX,&offsetX);
	if(ret==-1){
		printf("fifo_GrayScaleX read error\n");
	}
	
	#else
	read_blocking_UInt16(&fifo_GrayScaleX,&offsetX,&spinlock_GrayScaleX);
	#endif
	#if GRAYSCALEY_BLOCKING==0
	ret=read_non_blocking_UInt16(&fifo_GrayScaleY,&offsetY);
	if(ret==-1){
		printf("fifo_GrayScaleY read error\n");
	}
	
	#else
	read_blocking_UInt16(&fifo_GrayScaleY,&offsetY,&spinlock_GrayScaleY);
	#endif

	
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
					#if GRAYSCALETOGETPX_BLOCKING==0
					write_non_blocking_DoubleType(&fifo_GrayScaleToGetPx,gray[i]);
					#else
					write_blocking_DoubleType(&fifo_GrayScaleToGetPx,gray[i],&spinlock_GrayScaleToGetPx);
					#endif
				}
				#if GRAYSCALEX_BLOCKING==0
				write_non_blocking_UInt16(&fifo_GrayScaleX,offsetX);
				#else
				write_blocking_UInt16(&fifo_GrayScaleX,offsetX,&spinlock_GrayScaleX);
				#endif
				#if GRAYSCALEY_BLOCKING==0
				write_non_blocking_UInt16(&fifo_GrayScaleY,offsetY);
				#else
				write_blocking_UInt16(&fifo_GrayScaleY,offsetY,&spinlock_GrayScaleY);
				#endif
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
