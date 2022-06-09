#include "subsystem_tile2.h"
#include "../datatype/datatype_definition.h"
#include "../circular_fifo_lib/circular_fifo_lib.h"
#include <cheap_s.h>


void subsystem_tile2(){
	while(1){
	xil_printf("fire actor Gx\n");
	actor_Gx();
	xil_printf("actor Gx ends\n");
	xil_printf("fire actor Gy\n");
	actor_Gy();
	xil_printf("actor Gy ends\n");
	xil_printf("fire actor Abs\n");
	actor_Abs();
	xil_printf("actor Abs ends\n");
	}
}	

int init_tile2(){
	

	xil_printf("tile initialization starts\n");
	
/* extern */
	extern int ZeroValue;
	extern int OneValue;
	extern cheap fifo_admin_GrayScaleToAbs;
	extern volatile UInt16 * const fifo_data_GrayScaleToAbs;
	extern size_t buffer_GrayScaleToAbs_size;
	extern size_t token_GrayScaleToAbs_size;
	extern UInt16 buffer_AbsY[];
	extern size_t buffer_AbsY_size;
	extern circular_fifo fifo_AbsY;
	extern cheap fifo_admin_gysig;
	extern volatile DoubleType * const fifo_data_gysig;
	extern size_t buffer_gysig_size;
	extern size_t token_gysig_size;
	extern UInt16 buffer_AbsX[];
	extern size_t buffer_AbsX_size;
	extern circular_fifo fifo_AbsX;
	extern cheap fifo_admin_gxsig;
	extern volatile DoubleType * const fifo_data_gxsig;
	extern size_t buffer_gxsig_size;
	extern size_t token_gxsig_size;
	extern DoubleType buffer_absysig[];
	extern size_t buffer_absysig_size;
	extern circular_fifo fifo_absysig;
	extern DoubleType buffer_absxsig[];
	extern size_t buffer_absxsig_size;
	extern circular_fifo fifo_absxsig;

/* Create the channels*/
	init_fifo(&fifo_AbsY,buffer_AbsY,buffer_AbsY_size, sizeof(UInt16));
	init_fifo(&fifo_AbsX,buffer_AbsX,buffer_AbsX_size, sizeof(UInt16));
	init_fifo(&fifo_absysig,buffer_absysig,buffer_absysig_size, sizeof(DoubleType));
	init_fifo(&fifo_absxsig,buffer_absxsig,buffer_absxsig_size, sizeof(DoubleType));

/* SDF Delays */
	write_fifo(&fifo_AbsY,&ZeroValue,1);
	write_fifo(&fifo_AbsX,&ZeroValue,1);

/*wait util all other fifos are created*/
	while (cheap_get_buffer_capacity (fifo_admin_GrayScaleToAbs) == 0); 
	while (cheap_get_buffer_capacity (fifo_admin_gysig) == 0); 
	while (cheap_get_buffer_capacity (fifo_admin_gxsig) == 0); 

	xil_printf("tile initialization ends\n");				
	return 0;	
}
