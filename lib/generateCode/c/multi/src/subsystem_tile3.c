#include "../inc/subsystem_tile3.h"
extern circularFIFO_gxsig channel_gxsig;
extern token_gxsig arr_gxsig[];
extern int buffersize_gxsig;
extern circularFIFO_absxsig channel_absxsig;
extern token_absxsig arr_absxsig[];
extern int buffersize_absxsig;

void subsystem_tile3(){
	init_circularFIFO_gxsig(&channel_gxsig,arr_gxsig,buffersize_gxsig);
	init_circularFIFO_absxsig(&channel_absxsig,arr_absxsig,buffersize_absxsig);
	
	while(1){
		actor_Gx(&channel_gxsig,6
		 ,&channel_absxsig,1
		);

			
	}	
	
}
