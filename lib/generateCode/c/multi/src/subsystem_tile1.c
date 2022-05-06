		#include "../inc/subsystem_tile1.h"
		extern circularFIFO_gysig channel_gysig;
		extern token_gysig arr_gysig[];
		extern int buffersize_gysig;
		extern circularFIFO_GrayScaleToGetPx channel_GrayScaleToGetPx;
		extern token_GrayScaleToGetPx arr_GrayScaleToGetPx[];
		extern int buffersize_GrayScaleToGetPx;
		extern circularFIFO_gxsig channel_gxsig;
		extern token_gxsig arr_gxsig[];
		extern int buffersize_gxsig;
		extern circularFIFO_inputImage channel_inputImage;
		void subsystem_tile1(){
			
//			while(1){
				actor_getPx();

							
//			}	
			
		}	
		
		void init_tile1(){
			init_circularFIFO_gysig(&channel_gysig,arr_gysig,buffersize_gysig);
			init_circularFIFO_GrayScaleToGetPx(&channel_GrayScaleToGetPx,arr_GrayScaleToGetPx,buffersize_GrayScaleToGetPx);
			init_circularFIFO_gxsig(&channel_gxsig,arr_gxsig,buffersize_gxsig);
		}
