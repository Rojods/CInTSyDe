/* Includes */
#include "../inc/config.h"
#include "../inc/datatype_definition.h"
#include "../inc/circular_fifo_lib.h"
#include "../inc/sdfcomb_Gy.h"

/*
========================================
Declare Extern Channal Variables
========================================
*/
/* Input FIFO */
extern circular_fifo fifo_gysig;
extern spinlock spinlock_gysig;	

/* Output FIFO */
extern circular_fifo fifo_absysig;
extern spinlock spinlock_absysig;
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
static	DoubleType gy; 
static	Array6OfDoubleType imgBlockY; 
void actor_Gy(){

	/* Read From Input Port  */
	int ret=0;
	for(int i=0;i<6;++i){
		void* tmp_addr;
		read_non_blocking(&fifo_gysig,&tmp_addr);
		imgBlockY[i]= *((DoubleType *)tmp_addr);
	}
	

	
	/* Inline Code           */
	/* in combFunction GyImpl */
	gy=0;
	gy=gy+imgBlockY[0];
	gy=gy+2.0*imgBlockY[1];
	gy=gy+imgBlockY[2];
	gy=gy-imgBlockY[3];
	gy=gy-2.0*imgBlockY[4];
	gy=gy-imgBlockY[5];
	
	/* Write To Output Ports */
	write_non_blocking(&fifo_absysig,(void*)&gy);

}
