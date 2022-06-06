	/* Includes-------------------------- */
	
	#include "../../datatype/datatype_definition.h"
	#include "../../circular_fifo_lib/circular_fifo_lib.h"
	#include <cheap_s.h>
	#include "sdfactor_Abs.h"
	
	/*
	========================================
	Declare Extern Channal Variables
	========================================
	*/
	/* Input FIFO */
	extern volatile cheap const fifo_admin_GrayScaleToAbs;
	extern volatile UInt16 * const fifo_data_GrayScaleToAbs;	
					
	circular_fifo_UInt16 fifo_AbsY;
	spinlock spinlock_AbsY={.flag=0};
	
	circular_fifo_UInt16 fifo_AbsX;
	spinlock spinlock_AbsX={.flag=0};
	
	extern volatile cheap const fifo_admin_absysig;
	extern volatile DoubleType * const fifo_data_absysig;	
					
	extern volatile cheap const fifo_admin_absxsig;
	extern volatile DoubleType * const fifo_data_absxsig;	
					
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
ArrayXOfArrayXOfDoubleType system_img_sink_address = system_img_sink_global; 
DoubleType resy; 
DoubleType resx; 
	
	/* Read From Input Port  */
				int ret=0;
	{
		volatile DoubleType *tmp_ptrs[1];
		while ((cheap_claim_tokens (fifo_admin_absxsig, (volatile void **) tmp_ptrs, 1)) < 1)
	 		cheap_release_all_claimed_tokens (fifo_admin_absxsig);
	 		 		
		resx=*tmp_ptrs[0];
		cheap_release_spaces (fifo_admin_absxsig, 1);
	}
	{
		volatile DoubleType *tmp_ptrs[1];
		while ((cheap_claim_tokens (fifo_admin_absysig, (volatile void **) tmp_ptrs, 1)) < 1)
	 		cheap_release_all_claimed_tokens (fifo_admin_absysig);
	 		 		
		resy=*tmp_ptrs[0];
		cheap_release_spaces (fifo_admin_absysig, 1);
	}
	{
		volatile UInt16 *tmp_ptrs[2];
		while ((cheap_claim_tokens (fifo_admin_GrayScaleToAbs, (volatile void **) tmp_ptrs, 2)) < 2)
			 cheap_release_all_claimed_tokens (fifo_admin_GrayScaleToAbs);								
		
		for(int i=0;i<2;++i){
			dims[i]=*tmp_ptrs[i];	
		}
		
		cheap_release_spaces (fifo_admin_GrayScaleToAbs, 1);
	}
	#if ABSX_BLOCKING==0
	ret=read_non_blocking_UInt16(&fifo_AbsX,&offsetX);
	if(ret==-1){
		//printf("fifo_AbsX read error\n");
	}
	
	#else
	read_blocking_UInt16(&fifo_AbsX,&offsetX,&spinlock_AbsX);
	#endif
	#if ABSY_BLOCKING==0
	ret=read_non_blocking_UInt16(&fifo_AbsY,&offsetY);
	if(ret==-1){
		//printf("fifo_AbsY read error\n");
	}
	
	#else
	read_blocking_UInt16(&fifo_AbsY,&offsetY,&spinlock_AbsY);
	#endif

	
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
				#if ABSX_BLOCKING==0
				write_non_blocking_UInt16(&fifo_AbsX,offsetX);
				#else
				write_blocking_UInt16(&fifo_AbsX,offsetX,&spinlock_AbsX);
				#endif
				#if ABSY_BLOCKING==0
				write_non_blocking_UInt16(&fifo_AbsY,offsetY);
				#else
				write_blocking_UInt16(&fifo_AbsY,offsetY,&spinlock_AbsY);
				#endif

	}
