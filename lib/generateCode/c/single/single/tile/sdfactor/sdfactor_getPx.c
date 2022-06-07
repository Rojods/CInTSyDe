/* Includes */

#include "../../datatype/datatype_definition.h"
#include "../../circular_fifo_lib/circular_fifo_lib.h"
#include "sdfactor_getPx.h"

/*
========================================
Declare Extern Channal Variables
========================================
*/
/* Input FIFO */
extern circular_fifo_DoubleType fifo_GrayScaleToGetPx;
extern spinlock spinlock_GrayScaleToGetPx;	

/* Output FIFO */
extern circular_fifo_DoubleType fifo_gysig;
extern spinlock spinlock_gysig;
extern circular_fifo_DoubleType fifo_gxsig;
extern spinlock spinlock_gxsig;
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
		for(int i=0;i<6;++i){
			#if GYSIG_BLOCKING==0
			write_non_blocking_DoubleType(&fifo_gysig,imgBlockY[i]);
			#else
			write_blocking_DoubleType(&fifo_gysig,imgBlockY[i],&spinlock_gysig);
			#endif
		}
		
		for(int i=0;i<6;++i){
			#if GXSIG_BLOCKING==0
			write_non_blocking_DoubleType(&fifo_gxsig,imgBlockX[i]);
			#else
			write_blocking_DoubleType(&fifo_gxsig,imgBlockX[i],&spinlock_gxsig);
			#endif
		}
		
	
	}
