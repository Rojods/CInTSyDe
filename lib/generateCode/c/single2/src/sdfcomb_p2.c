/* Includes */
#include "../inc/config.h"
#include "../inc/datatype_definition.h"
#include "../inc/circular_fifo_lib.h"
#include "../inc/sdfcomb_p2.h"
#include <stdio.h>
/*
========================================
Declare Extern Channal Variables
========================================
*/
/* Input FIFO */

extern ref_fifo fifo_s1;
extern spinlock spinlock_s1;	
/* Output FIFO */
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
static	uint32 b; 
void actor_p2(){

	/* Read From Input Port  */
	int ret=0;
	{
		void* tmp_addr;
		read_non_blocking(&fifo_s1,&tmp_addr);
		b= *((uint32 *)tmp_addr);
	}
	

	
	/* Inline Code           */
	/* in combFunction p2impl */
	int c=2*b;
	printf("c->%d\n",c);
	/* Write To Output Ports */

}
