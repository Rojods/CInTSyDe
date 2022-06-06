#include "subsystem_tile3.h"
#include "../datatype/datatype_definition.h"
#include <cheap_s.h>
void subsystem_tile3(){
	actor_Abs();
}	

int init_tile3(){
	xil_printf("tile initialization starts\n");
	extern int ZeroValue;
	extern int OneValue;

	/* extern sdfchannel AbsY*/
	extern UInt16 buffer_AbsY[];
	extern int buffer_AbsY_size;
	extern circular_fifo_UInt16 fifo_AbsY;
	/* extern sdfchannel AbsX*/
	extern UInt16 buffer_AbsX[];
	extern int buffer_AbsX_size;
	extern circular_fifo_UInt16 fifo_AbsX;
	extern cheap fifo_admin_GrayScaleToAbs;
	extern volatile UInt16 * const fifo_data_GrayScaleToAbs;
	extern unsigned int buffer_GrayScaleToAbs_size;
	extern unsigned int token_GrayScaleToAbs_size;
	extern cheap fifo_admin_absysig;
	extern volatile DoubleType * const fifo_data_absysig;
	extern unsigned int buffer_absysig_size;
	extern unsigned int token_absysig_size;
	extern cheap fifo_admin_absxsig;
	extern volatile DoubleType * const fifo_data_absxsig;
	extern unsigned int buffer_absxsig_size;
	extern unsigned int token_absxsig_size;
/* Create the channels*/
	init_channel_UInt16(&fifo_AbsY,buffer_AbsY,buffer_AbsY_size);
	init_channel_UInt16(&fifo_AbsX,buffer_AbsX,buffer_AbsX_size);
	
	/*Initialize the channel */

	write_non_blocking_UInt16(&fifo_AbsY,ZeroValue);

	write_non_blocking_UInt16(&fifo_AbsX,ZeroValue);
	
	/* wait util other channels are created*/
	while (cheap_get_buffer_capacity (fifo_admin_GrayScaleToAbs) == 0); 
	while (cheap_get_buffer_capacity (fifo_admin_absysig) == 0); 
	while (cheap_get_buffer_capacity (fifo_admin_absxsig) == 0); 
	xil_printf("tile initialization ends\n");				
	return 0;	
}
