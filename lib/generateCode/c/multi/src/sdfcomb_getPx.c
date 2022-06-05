	/* Includes-------------------------- */
	#include "../inc/config.h"
	#include "../inc/datatype_definition.h"
	#include "../inc/circular_fifo_lib.h"
	#include <cheap_s.h>
	#include "../inc/sdfcomb_getPx.h"
	
	/*
	========================================
	Declare Extern Channal Variables
	========================================
	*/
	/* Input FIFO */
	circular_fifo_DoubleType fifo_GrayScaleToGetPx;
	spinlock spinlock_GrayScaleToGetPx={.flag=0};
	
	/* Output FIFO */
	extern volatile cheap const fifo_admin_gysig;
	extern volatile DoubleType * const fifo_data_gysig;	
					
	extern volatile cheap const fifo_admin_gxsig;
	extern volatile DoubleType * const fifo_data_gxsig;	
					
	/*
	========================================
	Declare Extern Global Variables
	========================================
	*/			
	extern Array1000OfArrayOfDouble inputImage;
	
	/*
	========================================
	Actor Function
	========================================
	*/			
void actor_getPx(){

/*  initialize memory*/
Array6OfDoubleType gray; 
Array6OfDoubleType imgBlockY; 
Array6OfDoubleType imgBlockX; 
	
	/* Read From Input Port  */
				int ret=0;
	for(int i=0;i<6;++i){
		
		#if GRAYSCALETOGETPX_BLOCKING==0
		ret=read_non_blocking_DoubleType(&fifo_GrayScaleToGetPx,&gray[i]);
		if(ret==-1){
			printf("fifo_GrayScaleToGetPx read error\n");
		}
		#else
		read_blocking_DoubleType(&fifo_GrayScaleToGetPx,&gray[i],&spinlock_GrayScaleToGetPx);
		#endif
	}

	
	/* Inline Code           */
				/* in combFunction getPxImpl1 */
				imgBlockX[0]=gray[0];
				imgBlockX[1]=gray[1];
				imgBlockX[2]=gray[2];
				imgBlockX[3]=gray[3];
				imgBlockX[4]=gray[4];
				imgBlockX[5]=gray[5];
				/* in combFunction getPxImpl2 */
				imgBlockY[0]=gray[0];
				imgBlockY[1]=gray[1];
				imgBlockY[2]=gray[2];
				imgBlockY[3]=gray[3];
				imgBlockY[4]=gray[4];
				imgBlockY[5]=gray[5];
	
	/* Write To Output Ports */
				{
					volatile DoubleType *tmp_ptrs[6];
					while ((cheap_claim_spaces (fifo_admin_gysig, (volatile void **) &tmp_ptrs[0], 6)) < 6)
						cheap_release_all_claimed_spaces (fifo_admin_gysig);
					
					for(int i=0;i<6;++i){
						*tmp_ptrs[i]=imgBlockY[i];
					}
					
					cheap_release_tokens (fifo_admin_gysig, 6);
				}						
				{
					volatile DoubleType *tmp_ptrs[6];
					while ((cheap_claim_spaces (fifo_admin_gxsig, (volatile void **) &tmp_ptrs[0], 6)) < 6)
						cheap_release_all_claimed_spaces (fifo_admin_gxsig);
					
					for(int i=0;i<6;++i){
						*tmp_ptrs[i]=imgBlockX[i];
					}
					
					cheap_release_tokens (fifo_admin_gxsig, 6);
				}						

	}
