#include "../inc/subsystem_init.h"
#include "../inc/datatype_definition.h"
#include "../inc/circular_fifo_lib.h"
/*
==============================================
	Extern Variables 
==============================================
*/		

/* extern sdfchannel sig*/
extern <ERROR! vertex_b Not Connected To Any ConbFunctions ! > buffer_sig[];
extern int buffer_sig_size;
extern circular_fifo_<ERROR! vertex_b Not Connected To Any ConbFunctions ! > fifo_sig;




/*
*********************************************************
Initialize All the Channels
Should be called before subsystem_single_uniprocessor()
*********************************************************
*/
void init_subsystem(){
	init_channel_<ERROR! vertex_b Not Connected To Any ConbFunctions ! >(&fifo_sig,buffer_sig,buffer_sig_size);
	
}
