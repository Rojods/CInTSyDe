#include "../inc/subsystem_tile2.h"
#include "../inc/datatype_definition.h"

void subsystem_tile2(){
	actor_Gx();
}	

int init_tile2(){
	extern int ZeroValue;
	extern int OneValue;

	extern cheap fifo_admin_absxsig;
	extern volatile DoubleType * const fifo_data_absxsig;
	extern unsigned int buffer_absxsig_size;
	extern unsigned int token_absxsig_size;
	extern cheap fifo_admin_gxsig;
	extern volatile DoubleType * const fifo_data_gxsig;
	extern unsigned int buffer_gxsig_size;
	extern unsigned int token_gxsig_size;
/* Create the channels*/
	if (cheap_init_r (fifo_admin_absxsig, (void *) fifo_data_absxsig, buffer_absxsig_size, token_absxsig_size) == NULL) {
		//xil_printf("%04u/%010u: cheap_init_r failed\n", (uint32_t)(t>>32),(uint32_t)t);
		return 1;
	}				
	
	/*Initialize the channel */
	
	/* wait util other channels are created*/
	while (cheap_get_buffer_capacity (fifo_admin_gxsig) == 0); 
	return 0;	
}
