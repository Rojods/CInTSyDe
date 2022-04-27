#ifndef  GETPX_H_
#define GETPX_H_			
#include "../inc/sdfchannel_inputImage.h"
#include "../inc/sdfchannel_gxsig.h"
#include "../inc/sdfchannel_gysig.h"
void actor_getPx(circularFIFO_inputImage* channel_imgInput_ptr, const size_t imgInput_rate
 ,circularFIFO_gxsig* channel_gx_ptr,const size_t gx_rate 
,circularFIFO_gysig* channel_gy_ptr,const size_t gy_rate
);

#endif		
