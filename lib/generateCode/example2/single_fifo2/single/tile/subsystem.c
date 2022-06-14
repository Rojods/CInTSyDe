#include "subsystem.h"
#include <stdio.h>
#include "./sdfactor/sdfactor_p1.h"
#include "./sdfactor/sdfactor_p2.h"
#include "./sdfactor/sdfactor_p3.h"
#include "./sdfactor/sdfactor_p4.h"
#include "./sdfactor/sdfactor_p5.h"
#include "../datatype/datatype_definition.h"
#include "../circular_fifo_lib/circular_fifo_lib.h"
/*
==============================================
	Subsystem Function
==============================================
*/	
int subsystem(){
	while(1){
		actor_p1();
		actor_p2();
		actor_p4();
		actor_p5();
		actor_p1();
		actor_p2();
		actor_p4();
		actor_p5();
		actor_p3();
	}

}


	/*
	*********************************************************
	Initialize All the Channels
	Should be called before subsystem_single_uniprocessor()
	*********************************************************
	*/
int init_subsystem(){
	/* Extern Variables */
	extern int ZeroValue1;
	extern int ZeroValue2;
	
	/* extern sdfchannel s4*/
	extern UInt32 buffer_s4[];
	extern size_t buffer_s4_size;
	extern circular_fifo fifo_s4;
	
	/* extern sdfchannel s5*/
	extern UInt32 buffer_s5[];
	extern size_t buffer_s5_size;
	extern circular_fifo fifo_s5;
	
	/* extern sdfchannel s6*/
	extern UInt32 buffer_s6[];
	extern size_t buffer_s6_size;
	extern circular_fifo fifo_s6;
	
	/* extern sdfchannel s_in*/
	extern UInt32 buffer_s_in[];
	extern size_t buffer_s_in_size;
	extern circular_fifo fifo_s_in;
	
	/* extern sdfchannel s1*/
	extern UInt32 buffer_s1[];
	extern size_t buffer_s1_size;
	extern circular_fifo fifo_s1;
	
	/* extern sdfchannel s2*/
	extern UInt32 buffer_s2[];
	extern size_t buffer_s2_size;
	extern circular_fifo fifo_s2;
	
	/* extern sdfchannel s3*/
	extern UInt32 buffer_s3[];
	extern size_t buffer_s3_size;
	extern circular_fifo fifo_s3;
	
	
	/* initialize the channels*/
		init_fifo(&fifo_s4,buffer_s4,buffer_s4_size, sizeof(UInt32));
		init_fifo(&fifo_s5,buffer_s5,buffer_s5_size, sizeof(UInt32));
		init_fifo(&fifo_s6,buffer_s6,buffer_s6_size, sizeof(UInt32));
		init_fifo(&fifo_s_in,buffer_s_in,buffer_s_in_size, sizeof(UInt32));
		init_fifo(&fifo_s1,buffer_s1,buffer_s1_size, sizeof(UInt32));
		init_fifo(&fifo_s2,buffer_s2,buffer_s2_size, sizeof(UInt32));
		init_fifo(&fifo_s3,buffer_s3,buffer_s3_size, sizeof(UInt32));
		
			
			
			
			
		write_fifo(&fifo_s6,(void*)&ZeroValue1,1);
		write_fifo(&fifo_s6,(void*)&ZeroValue2,1);
			
			
			
			
			
			
			
			
			
			
return 0;
}		
				
				
