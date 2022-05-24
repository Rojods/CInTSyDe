/* Includes */
#include "../inc/config.h"
#include "../inc/datatype_definition.h"
#include "../inc/circular_fifo_lib.h"
#include "../inc/sdfcomb_Abs.h"

/*
========================================
Declare Extern Channal Variables
========================================
*/
/* Input FIFO */

extern ref_fifo fifo_GrayScaleToAbs;
extern spinlock spinlock_GrayScaleToAbs;	

extern ref_fifo fifo_AbsY;
extern spinlock spinlock_AbsY;	

extern ref_fifo fifo_AbsX;
extern spinlock spinlock_AbsX;	

extern ref_fifo fifo_absysig;
extern spinlock spinlock_absysig;	

extern ref_fifo fifo_absxsig;
extern spinlock spinlock_absxsig;	
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
/*  initialize memory*/
static	UInt16 offsetX; 
static	UInt16 offsetY; 
static	Array2OfUInt16 dims; 
static	DoubleType resy; 
static	DoubleType resx; 
void actor_Abs(){

	ArrayXOfArrayXOfDoubleType system_img_sink_address = system_img_sink_global; 
	/* Read From Input Port  */
	int ret=0;
	{
		void* tmp_addr;
		read_non_blocking(&fifo_absxsig,&tmp_addr);
		resx= *((DoubleType *)tmp_addr);
	}
	
	{
		void* tmp_addr;
		read_non_blocking(&fifo_absysig,&tmp_addr);
		resy= *((DoubleType *)tmp_addr);
	}
	
	for(int i=0;i<2;++i){
		
		void* tmp_addr;
		read_non_blocking(&fifo_GrayScaleToAbs,&tmp_addr);
		dims[i]= *((UInt16 *)tmp_addr);
	}
	
	{
		void* tmp_addr;
		read_non_blocking(&fifo_AbsX,&tmp_addr);
		offsetX= *((UInt16 *)tmp_addr);
	}
	
	{
		void* tmp_addr;
		read_non_blocking(&fifo_AbsY,&tmp_addr);
		offsetY= *((UInt16 *)tmp_addr);
	}
	

	
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
	write_non_blocking(&fifo_AbsX,(void*)&offsetX);
							
	write_non_blocking(&fifo_AbsY,(void*)&offsetY);
							

}
