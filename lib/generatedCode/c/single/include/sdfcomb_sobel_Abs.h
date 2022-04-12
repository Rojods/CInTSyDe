#ifndef  SOBEL_ABS_H_
#define SOBEL_ABS_H_
#include "../include/sdfchannel_absysig.h" 

#include "../include/sdfchannel_gxsig.h" 

#include "../include/sdfchannel_gysig.h" 

#include "../include/sdfchannel_absxsig.h"

void actor_sobel_Abs(circularFIFO_absxsig channel_resx, const size_t resx_rate,
circularFIFO_absysig channel_resy, const size_t resy_rate
 
,void (*func_sobel_Abs_combinator)(
	token_absxsig *, size_t ,
	token_absysig *, size_t 
 )			
);

void func_sobel_Abs_combinator(	
token_absxsig resx[] , const size_t resx_rate,
token_absysig resy[] , const size_t resy_rate
);

//void read_channel_sobel_Abs_resx(channel_absxsig src_channel, size_t num, token_absxsig  dst[]); 

//void read_channel_sobel_Abs_resy(channel_absysig src_channel, size_t num, token_absysig  dst[]);


#endif		
