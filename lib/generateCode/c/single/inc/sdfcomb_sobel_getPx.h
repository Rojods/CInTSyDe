#ifndef  SOBEL_GETPX_H_
#define SOBEL_GETPX_H_			
#include "../inc/sdfchannel_gxsig.h"
#include "../inc/sdfchannel_gysig.h"
void actor_sobel_getPx(circularFIFO_gxsig* channel_gx_ptr,const size_t gx_rate,
circularFIFO_gysig* channel_gy_ptr,const size_t gy_rate
);

#endif		
