#include "../inc/subsystem_tile4.h"
extern circularFIFO_gysig channel_gysig;
extern token_gysig arr_gysig[];
extern int buffersize_gysig;
extern circularFIFO_absysig channel_absysig;
extern token_absysig arr_absysig[];
extern int buffersize_absysig;

void subsystem_tile4(){
	init_circularFIFO_gysig(&channel_gysig,arr_gysig,buffersize_gysig);
	init_circularFIFO_absysig(&channel_absysig,arr_absysig,buffersize_absysig);
	
	while(1){
		actor_Gy(&channel_gysig,6
		 ,&channel_absysig,1
		);

			
	}	
	
}
