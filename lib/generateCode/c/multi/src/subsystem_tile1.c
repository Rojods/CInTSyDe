#include "../inc/subsystem_tile1.h"
#include "../inc/datatype_definition.h"

void subsystem_tile1(){
	actor_getPx();
}	

int init_tile1(){
	extern int ZeroValue;
	extern int OneValue;

	extern cheap fifo_admin_gysig;
	extern volatile DoubleType * const fifo_data_gysig;
	extern unsigned int buffer_gysig_size;
	extern unsigned int token_gysig_size;
	extern cheap fifo_admin_gxsig;
	extern volatile DoubleType * const fifo_data_gxsig;
	extern unsigned int buffer_gxsig_size;
	extern unsigned int token_gxsig_size;
	extern cheap fifo_admin_GrayScaleToGetPx;
	extern volatile DoubleType * const fifo_data_GrayScaleToGetPx;
	extern unsigned int buffer_GrayScaleToGetPx_size;
	extern unsigned int token_GrayScaleToGetPx_size;
/* Create the channels*/
	if (cheap_init_r (fifo_admin_gysig, (void *) fifo_data_gysig, buffer_gysig_size, token_gysig_size) == NULL) {
		//xil_printf("%04u/%010u: cheap_init_r failed\n", (uint32_t)(t>>32),(uint32_t)t);
		return 1;
	}				
	if (cheap_init_r (fifo_admin_gxsig, (void *) fifo_data_gxsig, buffer_gxsig_size, token_gxsig_size) == NULL) {
		//xil_printf("%04u/%010u: cheap_init_r failed\n", (uint32_t)(t>>32),(uint32_t)t);
		return 1;
	}				
	
	/*Initialize the channel */
	
	/* wait util other channels are created*/
	while (cheap_get_buffer_capacity (fifo_admin_GrayScaleToGetPx) == 0); 
	return 0;	
}
