#include <stdlib.h>
#include <stdio.h>
#include "../include/sdfcomb_sobel_Gx.h"

inline static void read_channel_sobel_Gx_gx(const circularFIFO_gxsig src_channel, const size_t num, token_gxsig  dst[]){
	//#if defined SINGLE
		for(size_t i=0 ; i < num ;++i){
				if(read_circularFIFO_non_blocking_gxsig(src_channel,dst+i) ==-1){
					
				//error
				//abort();
			}
	}
}


inline static void write_channel_sobel_Gx_resx(const token_absxsig src[],const size_t num,circularFIFO_absxsig dst_channel){
	for(size_t i=0 ; i < num ;++i){
			if(write_circularFIFO_non_blocking_absxsig(dst_channel,src[i]) ==-1){
				//error
			}
		}
}

inline void actor_sobel_Gx(circularFIFO_gxsig channel_gx, const size_t gx_rate
 ,circularFIFO_absxsig channel_resx,const size_t resx_rate
 
,void (*func_sobel_Gx_combinator)(
	token_gxsig *, size_t 
 	,token_absxsig *, size_t 
 )			
){
	/*
	define array
	*/
	//array aiming to storing data from input ports
	token_gxsig gx[gx_rate];

		//array aiming to writing data to input ports
	token_absxsig resx[resx_rate];

		read_channel_sobel_Gx_gx(channel_gx,gx_rate,gx);

		func_sobel_Gx_combinator(gx,gx_rate , resx,resx_rate );	
	write_channel_sobel_Gx_resx(resx,resx_rate,channel_resx);

	}

//void func_actorName_combinator(portName[], portName_rate ....)
inline void func_sobel_Gx_combinator(	
token_gxsig gx[] , const size_t gx_rate
 ,token_absxsig  resx[],const size_t resx_rate
){
	printf("in actor sobel_Gx\n");

}

				
