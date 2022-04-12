#include <stdlib.h>
#include <stdio.h>
#include "../include/sdfcomb_sobel_Abs.h"

inline static void read_channel_sobel_Abs_resx(const circularFIFO_absxsig src_channel, const size_t num, token_absxsig  dst[]){
	//#if defined SINGLE
		for(size_t i=0 ; i < num ;++i){
				if(read_circularFIFO_non_blocking_absxsig(src_channel,dst+i) ==-1){
					
				//error
				//abort();
			}
	}
} 

inline static void read_channel_sobel_Abs_resy(const circularFIFO_absysig src_channel, const size_t num, token_absysig  dst[]){
	//#if defined SINGLE
		for(size_t i=0 ; i < num ;++i){
				if(read_circularFIFO_non_blocking_absysig(src_channel,dst+i) ==-1){
					
				//error
				//abort();
			}
	}
}


inline void actor_sobel_Abs(circularFIFO_absxsig channel_resx, const size_t resx_rate,
circularFIFO_absysig channel_resy, const size_t resy_rate
 
,void (*func_sobel_Abs_combinator)(
	token_absxsig *, size_t ,
	token_absysig *, size_t 
 )			
){
	/*
	define array
	*/
	//array aiming to storing data from input ports
	token_absxsig resx[resx_rate];
	
	token_absysig resy[resy_rate];

		//array aiming to writing data to input ports
	read_channel_sobel_Abs_resx(channel_resx,resx_rate,resx); 
	
	read_channel_sobel_Abs_resy(channel_resy,resy_rate,resy);

		func_sobel_Abs_combinator(resx,resx_rate,resy,resy_rate );	
}

//void func_actorName_combinator(portName[], portName_rate ....)
inline void func_sobel_Abs_combinator(	
token_absxsig resx[] , const size_t resx_rate,
token_absysig resy[] , const size_t resy_rate
){
	printf("in actor sobel_Abs\n");

}

				
