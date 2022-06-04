#include "../inc/subsystem_tile1.h"
#include "../inc/datatype_definition.h"
#include <cheap_s.h>
void subsystem_tile1(){
	actor_p1();
}	

int init_tile1(){
	xil_printf("tile initialization starts\n");

	extern cheap fifo_admin_s1;
	extern volatile uint32 * const fifo_data_s1;
	extern unsigned int buffer_s1_size;
	extern unsigned int token_s1_size;
/* Create the channels*/
	
	if (cheap_init_r (fifo_admin_s1, (void *) fifo_data_s1, buffer_s1_size, token_s1_size) == NULL) {
		//xil_printf("%04u/%010u: cheap_init_r failed\n", (uint32_t)(t>>32),(uint32_t)t);
		return 1;
	}				
	
	/*Initialize the channel */
	
	/* wait util other channels are created*/
	xil_printf("tile initialization ends\n");				
	return 0;	
}
