#include "../inc/subsystem_tile1.h"
#include "../inc/datatype_definition.h"
#include <cheap_s.h>
void subsystem_tile1(){
	actor_GrayScale();
	actor_getPx();
}	

int init_tile1(){
	xil_printf("tile initialization starts\n");
	extern int ZeroValue;
	extern int OneValue;

	extern cheap fifo_admin_GrayScaleToAbs;
	extern volatile UInt16 * const fifo_data_GrayScaleToAbs;
	extern unsigned int buffer_GrayScaleToAbs_size;
	extern unsigned int token_GrayScaleToAbs_size;
	/* extern sdfchannel GrayScaleToGetPx*/
	extern DoubleType buffer_GrayScaleToGetPx[];
	extern int buffer_GrayScaleToGetPx_size;
	extern circular_fifo_DoubleType fifo_GrayScaleToGetPx;
	extern cheap fifo_admin_gysig;
	extern volatile DoubleType * const fifo_data_gysig;
	extern unsigned int buffer_gysig_size;
	extern unsigned int token_gysig_size;
	extern cheap fifo_admin_gxsig;
	extern volatile DoubleType * const fifo_data_gxsig;
	extern unsigned int buffer_gxsig_size;
	extern unsigned int token_gxsig_size;
	/* extern sdfchannel GrayScaleX*/
	extern UInt16 buffer_GrayScaleX[];
	extern int buffer_GrayScaleX_size;
	extern circular_fifo_UInt16 fifo_GrayScaleX;
	/* extern sdfchannel GrayScaleY*/
	extern UInt16 buffer_GrayScaleY[];
	extern int buffer_GrayScaleY_size;
	extern circular_fifo_UInt16 fifo_GrayScaleY;
/* Create the channels*/
	
	if (cheap_init_r (fifo_admin_GrayScaleToAbs, (void *) fifo_data_GrayScaleToAbs, buffer_GrayScaleToAbs_size, token_GrayScaleToAbs_size) == NULL) {
		//xil_printf("%04u/%010u: cheap_init_r failed\n", (uint32_t)(t>>32),(uint32_t)t);
		return 1;
	}				
	init_channel_DoubleType(&fifo_GrayScaleToGetPx,buffer_GrayScaleToGetPx,buffer_GrayScaleToGetPx_size);
	
	if (cheap_init_r (fifo_admin_gysig, (void *) fifo_data_gysig, buffer_gysig_size, token_gysig_size) == NULL) {
		//xil_printf("%04u/%010u: cheap_init_r failed\n", (uint32_t)(t>>32),(uint32_t)t);
		return 1;
	}				
	
	if (cheap_init_r (fifo_admin_gxsig, (void *) fifo_data_gxsig, buffer_gxsig_size, token_gxsig_size) == NULL) {
		//xil_printf("%04u/%010u: cheap_init_r failed\n", (uint32_t)(t>>32),(uint32_t)t);
		return 1;
	}				
	init_channel_UInt16(&fifo_GrayScaleX,buffer_GrayScaleX,buffer_GrayScaleX_size);
	init_channel_UInt16(&fifo_GrayScaleY,buffer_GrayScaleY,buffer_GrayScaleY_size);
	
	/*Initialize the channel */

	write_non_blocking_UInt16(&fifo_GrayScaleX,ZeroValue);

	write_non_blocking_UInt16(&fifo_GrayScaleY,ZeroValue);
	
	/* wait util other channels are created*/
	xil_printf("tile initialization ends\n");				
	return 0;	
}
