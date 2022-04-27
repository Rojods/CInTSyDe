#include "../inc/subsystem.h"
	extern circularFIFO_absysig channel_absysig;
	extern token_absysig arr_absysig[];
	extern int buffersize_absysig;
	
	extern circularFIFO_gxsig channel_gxsig;
	extern token_gxsig arr_gxsig[];
	extern int buffersize_gxsig;
	
	extern circularFIFO_gysig channel_gysig;
	extern token_gysig arr_gysig[];
	extern int buffersize_gysig;
	
	extern circularFIFO_absxsig channel_absxsig;
	extern token_absxsig arr_absxsig[];
	extern int buffersize_absxsig;
	extern circularFIFO_outputImage channel_outputImage;
	extern circularFIFO_inputImage channel_inputImage;
void subsystem(){
	//create internal channels
	/*
	 create sdf channels 
	 the identifier of sdf channel is the name of created channel
	*/
	init_circularFIFO_absysig(&channel_absysig,arr_absysig,buffersize_absysig);
	init_circularFIFO_gxsig(&channel_gxsig,arr_gxsig,buffersize_gxsig);
	init_circularFIFO_gysig(&channel_gysig,arr_gysig,buffersize_gysig);
	init_circularFIFO_absxsig(&channel_absxsig,arr_absxsig,buffersize_absxsig);

		
	//SDFDelay
		
	while(1){
		/*round robin*/
		actor_getPx(&channel_inputImage,1
		 ,&channel_gxsig,6 
		,&channel_gysig,6
		);
		
		actor_Gx(&channel_gxsig,6
		 ,&channel_absxsig,1
		);
		
		actor_Gy(&channel_gysig,6
		 ,&channel_absysig,1
		);
		
		actor_Abs(&channel_absxsig,1,
		&channel_absysig,1
		 ,&channel_outputImage,1
		);

				
	}									
}		

