#include "../inc/subsystem_tile3.h"
#include "../inc/datatype_definition.h"

void subsystem_tile3(){
	actor_Gy();
}	

int init_tile3(){
	extern int ZeroValue;
	extern int OneValue;

	extern cheap fifo_admin_absysig;
	extern volatile DoubleType * const fifo_data_absysig;
	extern unsigned int buffer_absysig_size;
	extern unsigned int token_absysig_size;
	extern cheap fifo_admin_gysig;
	extern volatile DoubleType * const fifo_data_gysig;
	extern unsigned int buffer_gysig_size;
	extern unsigned int token_gysig_size;
/* Create the channels*/
	if (cheap_init_r (fifo_admin_absysig, (void *) fifo_data_absysig, buffer_absysig_size, token_absysig_size) == NULL) {
		//xil_printf("%04u/%010u: cheap_init_r failed\n", (uint32_t)(t>>32),(uint32_t)t);
		return 1;
	}				
	
	/*Initialize the channel */
	
	/* wait util other channels are created*/
	while (cheap_get_buffer_capacity (fifo_admin_gysig) == 0); 
	return 0;	
}
