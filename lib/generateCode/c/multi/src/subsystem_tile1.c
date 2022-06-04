#include "../inc/subsystem_tile1.h"
#include "../inc/datatype_definition.h"

void subsystem_tile1(){
	actor_GrayScale();
}	

int init_tile1(){
	extern int ZeroValue;
	extern int OneValue;

	/* extern sdfchannel GrayScaleToAbs*/
	extern UInt16 buffer_GrayScaleToAbs[];
	extern int buffer_GrayScaleToAbs_size;
	extern circular_fifo_UInt16 fifo_GrayScaleToAbs;
	/* extern sdfchannel GrayScaleToGetPx*/
	extern DoubleType buffer_GrayScaleToGetPx[];
	extern int buffer_GrayScaleToGetPx_size;
	extern circular_fifo_DoubleType fifo_GrayScaleToGetPx;
	/* extern sdfchannel GrayScaleX*/
	extern UInt16 buffer_GrayScaleX[];
	extern int buffer_GrayScaleX_size;
	extern circular_fifo_UInt16 fifo_GrayScaleX;
	/* extern sdfchannel GrayScaleY*/
	extern UInt16 buffer_GrayScaleY[];
	extern int buffer_GrayScaleY_size;
	extern circular_fifo_UInt16 fifo_GrayScaleY;
/* Create the channels*/
	init_channel_UInt16(&fifo_GrayScaleToAbs,buffer_GrayScaleToAbs,buffer_GrayScaleToAbs_size);
	init_channel_DoubleType(&fifo_GrayScaleToGetPx,buffer_GrayScaleToGetPx,buffer_GrayScaleToGetPx_size);
	init_channel_UInt16(&fifo_GrayScaleX,buffer_GrayScaleX,buffer_GrayScaleX_size);
	init_channel_UInt16(&fifo_GrayScaleY,buffer_GrayScaleY,buffer_GrayScaleY_size);
	
	/*Initialize the channel */

	write_non_blocking_UInt16(&fifo_GrayScaleX,ZeroValue);

	write_non_blocking_UInt16(&fifo_GrayScaleY,ZeroValue);
	
	/* wait util other channels are created*/
	return 0;	
}
