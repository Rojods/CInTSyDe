#include "../include/system_example.h"
void system_example(){
	//create internal channels
	/*
	 create sdf channels 
	 the identifier of sdf channel is the name of created channel
	*/
	//the max buffer size is 1,
	//but because the channel is implemented by a circular buffer,
	// the created circular size should be 2
	int buffersize_absysig = 1;
	token_absysig arr_absysig[buffersize_absysig+1];
	circularFIFO_absysig   Channel_absysig = init_circularFIFO_absysig( arr_absysig,buffersize_absysig+1);
	
	//the max buffer size is 6,
	//but because the channel is implemented by a circular buffer,
	// the created circular size should be 7
	int buffersize_gxsig = 6;
	token_gxsig arr_gxsig[buffersize_gxsig+1];
	circularFIFO_gxsig   Channel_gxsig = init_circularFIFO_gxsig( arr_gxsig,buffersize_gxsig+1);
	
	//the max buffer size is 6,
	//but because the channel is implemented by a circular buffer,
	// the created circular size should be 7
	int buffersize_gysig = 6;
	token_gysig arr_gysig[buffersize_gysig+1];
	circularFIFO_gysig   Channel_gysig = init_circularFIFO_gysig( arr_gysig,buffersize_gysig+1);
	
	//the max buffer size is 1,
	//but because the channel is implemented by a circular buffer,
	// the created circular size should be 2
	int buffersize_absxsig = 1;
	token_absxsig arr_absxsig[buffersize_absxsig+1];
	circularFIFO_absxsig   Channel_absxsig = init_circularFIFO_absxsig( arr_absxsig,buffersize_absxsig+1);

		
	//SDFDelay
		
	while(1){
		actor_sobel_getPx(Channel_gxsig,6,
		Channel_gysig,6
		 ,func_sobel_getPx_combinator
		);
		
			
		actor_sobel_Gx(Channel_gxsig,6
		 ,Channel_absxsig,1
		 ,func_sobel_Gx_combinator
		);
		
			
		actor_sobel_Gy(Channel_gysig,6
		 ,Channel_absysig,1
		 ,func_sobel_Gy_combinator
		);
		
			
		actor_sobel_Abs(Channel_absxsig,1,
		Channel_absysig,1
		 ,func_sobel_Abs_combinator
		);
			

				
	}									
}		

