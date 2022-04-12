#ifndef  SOBEL_GETPX_H_
#define SOBEL_GETPX_H_
#include "../include/sdfchannel_absysig.h" 

#include "../include/sdfchannel_gxsig.h" 

#include "../include/sdfchannel_gysig.h" 

#include "../include/sdfchannel_absxsig.h"

void actor_sobel_getPx(circularFIFO_gxsig channel_gx,const size_t gx_rate,
circularFIFO_gysig channel_gy,const size_t gy_rate
 
,void (*func_sobel_getPx_combinator)(
	token_gxsig *, size_t ,
	token_gysig *, size_t 
 )			
);

void func_sobel_getPx_combinator(	
token_gxsig gx[],const size_t gx_rate,
token_gysig gy[],const size_t gy_rate
);


//void write_channel_sobel_getPx_gx(token_gxsig src[],size_t num,channel_gxsig dst_channel); 

//void write_channel_sobel_getPx_gy(token_gysig src[],size_t num,channel_gysig dst_channel);

#endif		
