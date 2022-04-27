#ifndef  SOBEL_GX_H_
#define SOBEL_GX_H_			
#include "../inc/sdfchannel_gxsig.h"
#include "../inc/sdfchannel_absxsig.h"
void actor_sobel_Gx(circularFIFO_gxsig* channel_gx_ptr, const size_t gx_rate
 ,circularFIFO_absxsig* channel_resx_ptr,const size_t resx_rate
);

#endif		
