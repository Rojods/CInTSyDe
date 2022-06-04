/* Includes */
#include "../inc/config.h"
#include "../inc/datatype_definition.h"
#include "../inc/circular_fifo_lib.h"
#include "../inc/sdfcomb_p1.h"

/*
========================================
Declare Extern Channal Variables
========================================
*/
/* Input FIFO */
/* Output FIFO */
extern ref_fifo fifo_s1;
extern spinlock spinlock_s1;
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
static	uint32 a; 
void actor_p1(){

	/* Read From Input Port  */
	int ret=0;

	
	/* Inline Code           */
	/* in combFunction p1impl */
	a=5;
	
	/* Write To Output Ports */
	write_non_blocking(&fifo_s1,(void*)&a);
							

}
