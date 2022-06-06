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
extern circular_fifo fifo_GrayScaleToGetPx;
extern spinlock spinlock_GrayScaleToGetPx;	

/* Output FIFO */
extern circular_fifo fifo_gysig;
extern spinlock spinlock_gysig;
extern circular_fifo fifo_gxsig;
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
/*  initialize memory*/
static	Array6OfDoubleType gray; 
static	Array6OfDoubleType imgBlockY; 
static	Array6OfDoubleType imgBlockX; 
void actor_getPx(){

	/* Read From Input Port  */
	int ret=0;
	for(int i=0;i<6;++i){
		void* tmp_addr;
		read_non_blocking(&fifo_GrayScaleToGetPx,&tmp_addr);
		gray[i]= *((DoubleType *)tmp_addr);
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
		write_non_blocking(&fifo_gysig,(void*)&imgBlockY[i]);		
	}
	
	for(int i=0;i<6;++i){
		write_non_blocking(&fifo_gxsig,(void*)&imgBlockX[i]);		
	}
	

}
