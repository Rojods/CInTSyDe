#ifndef  SOBEL_GX_H_
#define SOBEL_GX_H_
#include "../include/sdfchannel_absysig.h" 

#include "../include/sdfchannel_gxsig.h" 

#include "../include/sdfchannel_gysig.h" 

#include "../include/sdfchannel_absxsig.h"

void actor_sobel_Gx(circularFIFO_gxsig channel_gx, const size_t gx_rate
 ,circularFIFO_absxsig channel_resx,const size_t resx_rate
 
,void (*func_sobel_Gx_combinator)(
	token_gxsig *, size_t 
 	,token_absxsig *, size_t 
 )			
);

void func_sobel_Gx_combinator(	
token_gxsig gx[] , const size_t gx_rate
 ,token_absxsig  resx[],const size_t resx_rate
);

//void read_channel_sobel_Gx_gx(channel_gxsig src_channel, size_t num, token_gxsig  dst[]);


//void write_channel_sobel_Gx_resx(token_absxsig src[],size_t num,channel_absxsig dst_channel);

#endif		
