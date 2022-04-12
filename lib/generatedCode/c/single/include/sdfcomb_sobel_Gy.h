#ifndef  SOBEL_GY_H_
#define SOBEL_GY_H_
#include "../include/sdfchannel_absysig.h" 

#include "../include/sdfchannel_gxsig.h" 

#include "../include/sdfchannel_gysig.h" 

#include "../include/sdfchannel_absxsig.h"

void actor_sobel_Gy(circularFIFO_gysig channel_gy, const size_t gy_rate
 ,circularFIFO_absysig channel_resy,const size_t resy_rate
 
,void (*func_sobel_Gy_combinator)(
	token_gysig *, size_t 
 	,token_absysig *, size_t 
 )			
);

void func_sobel_Gy_combinator(	
token_gysig gy[] , const size_t gy_rate
 ,token_absysig  resy[],const size_t resy_rate
);

//void read_channel_sobel_Gy_gy(channel_gysig src_channel, size_t num, token_gysig  dst[]);


//void write_channel_sobel_Gy_resy(token_absysig src[],size_t num,channel_absysig dst_channel);

#endif		
