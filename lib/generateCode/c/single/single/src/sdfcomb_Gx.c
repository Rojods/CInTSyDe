/* Includes */
#include "../inc/config.h"
#include "../inc/datatype_definition.h"
#include "../inc/circular_fifo_lib.h"
#include "../inc/sdfcomb_Gx.h"

/*
========================================
Declare Extern Channal Variables
========================================
*/
/* Input FIFO */
extern circular_fifo fifo_gxsig;
extern spinlock spinlock_gxsig;	

/* Output FIFO */
extern circular_fifo fifo_absxsig;
extern spinlock spinlock_absxsig;
/*
========================================
	Declare Extern Global Variables
========================================
*/			
	
/*
========================================
	Actor Function
========================================
*/	
/*  initialize memory*/
static	DoubleType gx; 
static	Array6OfDoubleType imgBlockX; 
void actor_Gx(){

	/* Read From Input Port  */
	int ret=0;
	for(int i=0;i<6;++i){
		void* tmp_addr;
		read_non_blocking(&fifo_gxsig,&tmp_addr);
		imgBlockX[i]= *((DoubleType *)tmp_addr);
	}
	

	
	/* Inline Code           */
	/* in combFunction GxImpl */
	gx=0;
	gx=gx-imgBlockX[0];
	gx=gx+imgBlockX[1];
	gx=gx-2.0*imgBlockX[2];
	gx=gx+2.0*imgBlockX[3];
	gx=gx-imgBlockX[4];
	gx=gx+imgBlockX[5];
	
	/* Write To Output Ports */
	write_non_blocking(&fifo_absxsig,(void*)&gx);

}
