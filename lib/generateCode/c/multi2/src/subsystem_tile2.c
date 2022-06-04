#include "../inc/subsystem_tile2.h"
#include "../inc/datatype_definition.h"
#include <cheap_s.h>
void subsystem_tile2(){
	actor_p2();
}	

int init_tile2(){

	extern cheap fifo_admin_s1;
	extern volatile uint32 * const fifo_data_s1;
	extern unsigned int buffer_s1_size;
	extern unsigned int token_s1_size;
/* Create the channels*/
	
	/*Initialize the channel */
	
	/* wait util other channels are created*/
	while (cheap_get_buffer_capacity (fifo_admin_s1) == 0); 
	return 0;	
}
