	/* Includes-------------------------- */
	#include "../inc/config.h"
	#include "../inc/datatype_definition.h"
	#include "../inc/circular_fifo_lib.h"
	#include "../inc/sdfcomb_vertex_b.h"
	
	
	
	/*
	========================================
	Declare Extern Channal Variables
	========================================
	*/
	/* Input FIFO */
	extern circular_fifo_<ERROR! vertex_b Not Connected To Any ConbFunctions ! > fifo_sig;
	extern spinlock spinlock_sig;
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
void actor_vertex_b(){
				
/*  initialize memory*/
	
	/* Read From Input Port  */
	printf("%s\n","read");
	int ret=0;

	
	/* Inline Code           */
	printf("%s\n","inline code");
	
	/* Write To Output Ports */
	printf("%s\n","write");

	}
