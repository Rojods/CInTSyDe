#include "../inc/subsystem_include_help.h"
/*
==============================================
	Extern Variables are decalred in the 
	subsystem_include_help.h
==============================================
*/
/*
==============================================
	Subsystem Function
==============================================
*/	
void initChannels();
int subsystem_single_uniprocessor(){
	
	/* Initilize Channels */
	initChannels();
	
	/*    SDFdelay        */
	
	while(1){
		actor_GrayScale();
		actor_getPx();
		actor_Gx();
		actor_Gy();
		actor_Abs();
		
	}
}

void initChannels(){
	init_channel_type(&fifo_GrayScaleToAbs,buffer_GrayScaleToAbs,buffer_GrayScaleToAbs_size);
	init_channel_type(&fifo_AbsY,buffer_AbsY,buffer_AbsY_size);
	init_channel_type(&fifo_AbsX,buffer_AbsX,buffer_AbsX_size);
	init_channel_type(&fifo_GrayScaleToGetPx,buffer_GrayScaleToGetPx,buffer_GrayScaleToGetPx_size);
	init_channel_type(&fifo_gysig,buffer_gysig,buffer_gysig_size);
	init_channel_type(&fifo_gxsig,buffer_gxsig,buffer_gxsig_size);
	init_channel_type(&fifo_absysig,buffer_absysig,buffer_absysig_size);
	init_channel_type(&fifo_GrayScaleX,buffer_GrayScaleX,buffer_GrayScaleX_size);
	init_channel_type(&fifo_absxsig,buffer_absxsig,buffer_absxsig_size);
	init_channel_type(&fifo_GrayScaleY,buffer_GrayScaleY,buffer_GrayScaleY_size);
}
