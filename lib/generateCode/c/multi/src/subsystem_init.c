#include "../inc/subsystem_init.h"
#include "../inc/circular_fifo_lib.h"
#include "../inc/datatype_definition.h"
/*
==============================================
	Extern Variables 
==============================================
*/	
	extern int ZeroValue;
	extern int OneValue;
	/* extern sdfchannel GrayScaleToAbs*/
	extern UInt16 buffer_GrayScaleToAbs[];
	extern int buffer_GrayScaleToAbs_size;
	extern circular_fifo_UInt16 fifo_GrayScaleToAbs;
	/* extern sdfchannel AbsY*/
	extern UInt16 buffer_AbsY[];
	extern int buffer_AbsY_size;
	extern circular_fifo_UInt16 fifo_AbsY;
	/* extern sdfchannel gysig*/
	extern DoubleType buffer_gysig[];
	extern int buffer_gysig_size;
	extern circular_fifo_DoubleType fifo_gysig;
	/* extern sdfchannel AbsX*/
	extern UInt16 buffer_AbsX[];
	extern int buffer_AbsX_size;
	extern circular_fifo_UInt16 fifo_AbsX;
	/* extern sdfchannel GrayScaleToGetPx*/
	extern DoubleType buffer_GrayScaleToGetPx[];
	extern int buffer_GrayScaleToGetPx_size;
	extern circular_fifo_DoubleType fifo_GrayScaleToGetPx;
	/* extern sdfchannel gxsig*/
	extern DoubleType buffer_gxsig[];
	extern int buffer_gxsig_size;
	extern circular_fifo_DoubleType fifo_gxsig;
	/* extern sdfchannel absysig*/
	extern DoubleType buffer_absysig[];
	extern int buffer_absysig_size;
	extern circular_fifo_DoubleType fifo_absysig;
	/* extern sdfchannel absxsig*/
	extern DoubleType buffer_absxsig[];
	extern int buffer_absxsig_size;
	extern circular_fifo_DoubleType fifo_absxsig;
	/* extern sdfchannel GrayScaleX*/
	extern UInt16 buffer_GrayScaleX[];
	extern int buffer_GrayScaleX_size;
	extern circular_fifo_UInt16 fifo_GrayScaleX;
	/* extern sdfchannel GrayScaleY*/
	extern UInt16 buffer_GrayScaleY[];
	extern int buffer_GrayScaleY_size;
	extern circular_fifo_UInt16 fifo_GrayScaleY;
void init_subsystem(){
	init_channel_UInt16(&fifo_GrayScaleToAbs,buffer_GrayScaleToAbs,buffer_GrayScaleToAbs_size);
	init_channel_UInt16(&fifo_AbsY,buffer_AbsY,buffer_AbsY_size);
	init_channel_DoubleType(&fifo_gysig,buffer_gysig,buffer_gysig_size);
	init_channel_UInt16(&fifo_AbsX,buffer_AbsX,buffer_AbsX_size);
	init_channel_DoubleType(&fifo_GrayScaleToGetPx,buffer_GrayScaleToGetPx,buffer_GrayScaleToGetPx_size);
	init_channel_DoubleType(&fifo_gxsig,buffer_gxsig,buffer_gxsig_size);
	init_channel_DoubleType(&fifo_absysig,buffer_absysig,buffer_absysig_size);
	init_channel_DoubleType(&fifo_absxsig,buffer_absxsig,buffer_absxsig_size);
	init_channel_UInt16(&fifo_GrayScaleX,buffer_GrayScaleX,buffer_GrayScaleX_size);
	init_channel_UInt16(&fifo_GrayScaleY,buffer_GrayScaleY,buffer_GrayScaleY_size);
	
	write_non_blocking_UInt16(&fifo_AbsY,ZeroValue);
	write_non_blocking_UInt16(&fifo_AbsX,ZeroValue);
	write_non_blocking_UInt16(&fifo_GrayScaleX,ZeroValue);
	write_non_blocking_UInt16(&fifo_GrayScaleY,ZeroValue);
	}
