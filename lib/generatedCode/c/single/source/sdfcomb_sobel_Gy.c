#include <stdlib.h>
#include <stdio.h>
#include "../include/sdfcomb_sobel_Gy.h"

inline static void read_channel_sobel_Gy_gy(const circularFIFO_gysig src_channel, const size_t num, token_gysig  dst[]){
	//#if defined SINGLE
		for(size_t i=0 ; i < num ;++i){
				if(read_circularFIFO_non_blocking_gysig(src_channel,dst+i) ==-1){
					
				//error
				//abort();
			}
	}
}


inline static void write_channel_sobel_Gy_resy(const token_absysig src[],const size_t num,circularFIFO_absysig dst_channel){
	for(size_t i=0 ; i < num ;++i){
			if(write_circularFIFO_non_blocking_absysig(dst_channel,src[i]) ==-1){
				//error
			}
		}
}

inline void actor_sobel_Gy(circularFIFO_gysig channel_gy, const size_t gy_rate
 ,circularFIFO_absysig channel_resy,const size_t resy_rate
 
,void (*func_sobel_Gy_combinator)(
	token_gysig *, size_t 
 	,token_absysig *, size_t 
 )			
){
	/*
	define array
	*/
	//array aiming to storing data from input ports
	token_gysig gy[gy_rate];

		//array aiming to writing data to input ports
	token_absysig resy[resy_rate];

		read_channel_sobel_Gy_gy(channel_gy,gy_rate,gy);

		func_sobel_Gy_combinator(gy,gy_rate , resy,resy_rate );	
	write_channel_sobel_Gy_resy(resy,resy_rate,channel_resy);

	}

//void func_actorName_combinator(portName[], portName_rate ....)
inline void func_sobel_Gy_combinator(	
token_gysig gy[] , const size_t gy_rate
 ,token_absysig  resy[],const size_t resy_rate
){
	printf("in actor sobel_Gy\n");

}

				
