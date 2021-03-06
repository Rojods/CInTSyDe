#include "subsystem.h"
#include <stdio.h>
#include "./sdfactor/sdfactor_Gx.h"
#include "./sdfactor/sdfactor_Abs.h"
#include "./sdfactor/sdfactor_Gy.h"
#include "./sdfactor/sdfactor_GrayScale.h"
#include "./sdfactor/sdfactor_getPx.h"
#include "../datatype/datatype_definition.h"
#include "../circular_fifo_lib/circular_fifo_lib.h"
/*
==============================================
	Subsystem Function
==============================================
*/	
int subsystem(){
	while(1){
		actor_GrayScale();
		actor_getPx();
		actor_Gx();
		actor_Gy();
		actor_Abs();
	}

}


	/*
	*********************************************************
	Initialize All the Channels
	Should be called before subsystem_single_uniprocessor()
	*********************************************************
	*/
int init_subsystem(){
	/* Extern Variables */
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
	
	
	/* initialize the channels*/
init_fifo_UInt16(&fifo_GrayScaleToAbs,buffer_GrayScaleToAbs,buffer_GrayScaleToAbs_size);
init_fifo_UInt16(&fifo_AbsY,buffer_AbsY,buffer_AbsY_size);
init_fifo_DoubleType(&fifo_gysig,buffer_gysig,buffer_gysig_size);
init_fifo_UInt16(&fifo_AbsX,buffer_AbsX,buffer_AbsX_size);
init_fifo_DoubleType(&fifo_GrayScaleToGetPx,buffer_GrayScaleToGetPx,buffer_GrayScaleToGetPx_size);
init_fifo_DoubleType(&fifo_gxsig,buffer_gxsig,buffer_gxsig_size);
init_fifo_DoubleType(&fifo_absysig,buffer_absysig,buffer_absysig_size);
init_fifo_DoubleType(&fifo_absxsig,buffer_absxsig,buffer_absxsig_size);
init_fifo_UInt16(&fifo_GrayScaleX,buffer_GrayScaleX,buffer_GrayScaleX_size);
init_fifo_UInt16(&fifo_GrayScaleY,buffer_GrayScaleY,buffer_GrayScaleY_size);
		
			
			
		write_fifo_UInt16(&fifo_AbsY,&ZeroValue,1);
			
			
			
			
		write_fifo_UInt16(&fifo_AbsX,&ZeroValue,1);
			
			
			
			
			
			
			
			
			
			
		write_fifo_UInt16(&fifo_GrayScaleX,&ZeroValue,1);
			
			
		write_fifo_UInt16(&fifo_GrayScaleY,&ZeroValue,1);
			
			
return 0;
}		
				
				
