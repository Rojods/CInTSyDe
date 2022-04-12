#include <stdlib.h>
#include <stdio.h>
#include "../include/sdfcomb_sobel_getPx.h"


inline static void write_channel_sobel_getPx_gx(const token_gxsig src[],const size_t num,circularFIFO_gxsig dst_channel){
	for(size_t i=0 ; i < num ;++i){
			if(write_circularFIFO_non_blocking_gxsig(dst_channel,src[i]) ==-1){
				//error
			}
		}
} 

inline static void write_channel_sobel_getPx_gy(const token_gysig src[],const size_t num,circularFIFO_gysig dst_channel){
	for(size_t i=0 ; i < num ;++i){
			if(write_circularFIFO_non_blocking_gysig(dst_channel,src[i]) ==-1){
				//error
			}
		}
}

inline void actor_sobel_getPx(circularFIFO_gxsig channel_gx,const size_t gx_rate,
circularFIFO_gysig channel_gy,const size_t gy_rate
 
,void (*func_sobel_getPx_combinator)(
	token_gxsig *, size_t ,
	token_gysig *, size_t 
 )			
){
	/*
	define array
	*/
	//array aiming to storing data from input ports
	//array aiming to writing data to input ports
	token_gxsig gx[gx_rate];
	
	token_gysig gy[gy_rate];

		func_sobel_getPx_combinator(gx,gx_rate,gy,gy_rate );	
	write_channel_sobel_getPx_gx(gx,gx_rate,channel_gx); 
	
	write_channel_sobel_getPx_gy(gy,gy_rate,channel_gy);

	}

//void func_actorName_combinator(portName[], portName_rate ....)
inline void func_sobel_getPx_combinator(	
token_gxsig gx[],const size_t gx_rate,
token_gysig gy[],const size_t gy_rate
){
	printf("in actor sobel_getPx\n");

}

				
