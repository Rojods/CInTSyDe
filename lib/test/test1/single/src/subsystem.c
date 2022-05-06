			#include "../inc/subsystem_include_help.h"
			#include "../inc/subsystem.h"
			#include "../inc/sdfcomb_Gx.h"
			#include "../inc/sdfcomb_Abs.h"
			#include "../inc/sdfcomb_GrayScale.h"
			#include "../inc/sdfcomb_Gy.h"
			#include "../inc/sdfcomb_getPx.h"
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
//				initChannels();
				
				/*    SDFdelay        */
//				int i=0;
//				while(1){
					printf("%s\n","enter GrayScale");
						actor_GrayScale();
					printf("%s\n","enter getPx");
						actor_getPx();
					printf("%s\n","enter Gx");
						actor_Gx();
					printf("%s\n","enter Gy");
						actor_Gy();
					printf("%s\n","enter Abs");
						actor_Abs();
					
//				}
			}
			
			void initChannels(){
				init_channel_UInt16(&fifo_GrayScaleToAbs,buffer_GrayScaleToAbs,buffer_GrayScaleToAbs_size);
				init_channel_UInt16(&fifo_AbsY,buffer_AbsY,buffer_AbsY_size);
				init_channel_UInt16(&fifo_AbsX,buffer_AbsX,buffer_AbsX_size);
				init_channel_DoubleType(&fifo_GrayScaleToGetPx,buffer_GrayScaleToGetPx,buffer_GrayScaleToGetPx_size);
				init_channel_DoubleType(&fifo_gysig,buffer_gysig,buffer_gysig_size);
				init_channel_DoubleType(&fifo_gxsig,buffer_gxsig,buffer_gxsig_size);
				init_channel_DoubleType(&fifo_absysig,buffer_absysig,buffer_absysig_size);
				init_channel_UInt16(&fifo_GrayScaleX,buffer_GrayScaleX,buffer_GrayScaleX_size);
				init_channel_DoubleType(&fifo_absxsig,buffer_absxsig,buffer_absxsig_size);
				init_channel_UInt16(&fifo_GrayScaleY,buffer_GrayScaleY,buffer_GrayScaleY_size);
				
				write_non_blocking_UInt16(&fifo_AbsY,ZeroValue);
				write_non_blocking_UInt16(&fifo_AbsX,ZeroValue);
				write_non_blocking_UInt16(&fifo_GrayScaleX,ZeroValue);
				write_non_blocking_UInt16(&fifo_GrayScaleY,ZeroValue);
			}
