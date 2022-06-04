#include "../inc/subsystem.h"
#include <stdio.h>
#include "../inc/sdfcomb_p1.h"
#include "../inc/sdfcomb_p2.h"
#include "../inc/datatype_definition.h"
#include "../inc/circular_fifo_lib.h"
/*
==============================================
	Subsystem Function
==============================================
*/	
int subsystem(){
		//printf("%s\n","enter p1");
			actor_p1();
		//printf("%s\n","enter p2");
			actor_p2();
}


	/*
	*********************************************************
	Initialize All the Channels
	Should be called before subsystem_single_uniprocessor()
	*********************************************************
	*/
int init_subsystem(){
	/* Extern Variables */
	
	/* extern sdfchannel s1*/
				extern void* buffer_s1[];
	extern size_t buffer_s1_size;
	extern ref_fifo fifo_s1;
	
	/* initialize the channels*/
				ref_init(&fifo_s1,buffer_s1,buffer_s1_size);
		
		return 0;
	}		


