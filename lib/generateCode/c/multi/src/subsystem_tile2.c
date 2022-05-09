		#include "../inc/subsystem_tile2.h"
		extern circularFIFO_GrayScaleToAbs channel_GrayScaleToAbs;
		extern token_GrayScaleToAbs arr_GrayScaleToAbs[];
		extern int buffersize_GrayScaleToAbs;
		extern circularFIFO_system_img_sink_global channel_system_img_sink_global;
		extern circularFIFO_AbsY channel_AbsY;
		extern token_AbsY arr_AbsY[];
		extern int buffersize_AbsY;
		extern circularFIFO_AbsX channel_AbsX;
		extern token_AbsX arr_AbsX[];
		extern int buffersize_AbsX;
		extern circularFIFO_outputImage channel_outputImage;
		extern circularFIFO_absysig channel_absysig;
		extern token_absysig arr_absysig[];
		extern int buffersize_absysig;
		extern circularFIFO_absxsig channel_absxsig;
		extern token_absxsig arr_absxsig[];
		extern int buffersize_absxsig;
		void subsystem_tile2(){
			
//			while(1){
				actor_Abs();

							
//			}	
			
		}	
		
		void init_tile2(){
			init_circularFIFO_GrayScaleToAbs(&channel_GrayScaleToAbs,arr_GrayScaleToAbs,buffersize_GrayScaleToAbs);
			init_circularFIFO_AbsY(&channel_AbsY,arr_AbsY,buffersize_AbsY);
			init_circularFIFO_AbsX(&channel_AbsX,arr_AbsX,buffersize_AbsX);
			init_circularFIFO_absysig(&channel_absysig,arr_absysig,buffersize_absysig);
			init_circularFIFO_absxsig(&channel_absxsig,arr_absxsig,buffersize_absxsig);
		}
