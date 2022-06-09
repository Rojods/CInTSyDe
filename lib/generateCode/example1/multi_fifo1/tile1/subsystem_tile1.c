#include "subsystem_tile1.h"
#include "../datatype/datatype_definition.h"
#include "../circular_fifo_lib/circular_fifo_lib.h"
#include <cheap_s.h>


void subsystem_tile1(){
	while(1){
	xil_printf("fire actor GrayScale\n");
	actor_GrayScale();
	xil_printf("actor GrayScale ends\n");
	xil_printf("fire actor getPx\n");
	actor_getPx();
	xil_printf("actor getPx ends\n");
	}
}	

int init_tile1(){
	

	xil_printf("tile initialization starts\n");
	
/* extern */
	extern int ZeroValue;
	extern int OneValue;
	extern cheap fifo_admin_GrayScaleToAbs;
	extern volatile UInt16 * const fifo_data_GrayScaleToAbs;
	extern size_t  buffer_GrayScaleToAbs_size;
	extern size_t token_GrayScaleToAbs_size;
	extern cheap fifo_admin_gysig;
	extern volatile DoubleType * const fifo_data_gysig;
	extern size_t  buffer_gysig_size;
	extern size_t token_gysig_size;
	extern DoubleType buffer_GrayScaleToGetPx[];
	extern int buffer_GrayScaleToGetPx_size;
	extern circular_fifo_DoubleType fifo_GrayScaleToGetPx;
	extern cheap fifo_admin_gxsig;
	extern volatile DoubleType * const fifo_data_gxsig;
	extern size_t  buffer_gxsig_size;
	extern size_t token_gxsig_size;
	extern UInt16 buffer_GrayScaleX[];
	extern int buffer_GrayScaleX_size;
	extern circular_fifo_UInt16 fifo_GrayScaleX;
	extern UInt16 buffer_GrayScaleY[];
	extern int buffer_GrayScaleY_size;
	extern circular_fifo_UInt16 fifo_GrayScaleY;

/* Create the channels*/
	if (cheap_init_r (fifo_admin_GrayScaleToAbs, (void *) fifo_data_GrayScaleToAbs, buffer_GrayScaleToAbs_size, token_GrayScaleToAbs_size) == NULL) {
		//xil_printf("%04u/%010u: cheap_init_r failed\n", (uint32_t)(t>>32),(uint32_t)t);
		return 1;
	}				
	if (cheap_init_r (fifo_admin_gysig, (void *) fifo_data_gysig, buffer_gysig_size, token_gysig_size) == NULL) {
		//xil_printf("%04u/%010u: cheap_init_r failed\n", (uint32_t)(t>>32),(uint32_t)t);
		return 1;
	}				
	init_fifo_DoubleType(&fifo_GrayScaleToGetPx,buffer_GrayScaleToGetPx,buffer_GrayScaleToGetPx_size);
	if (cheap_init_r (fifo_admin_gxsig, (void *) fifo_data_gxsig, buffer_gxsig_size, token_gxsig_size) == NULL) {
		//xil_printf("%04u/%010u: cheap_init_r failed\n", (uint32_t)(t>>32),(uint32_t)t);
		return 1;
	}				
	init_fifo_UInt16(&fifo_GrayScaleX,buffer_GrayScaleX,buffer_GrayScaleX_size);
	init_fifo_UInt16(&fifo_GrayScaleY,buffer_GrayScaleY,buffer_GrayScaleY_size);

/* SDF Delays */
	write_fifo_UInt16(&fifo_GrayScaleX,&ZeroValue,1);
	write_fifo_UInt16(&fifo_GrayScaleY,&ZeroValue,1);

/*wait util all other fifos are created*/

	xil_printf("tile initialization ends\n");				
	return 0;	
}
