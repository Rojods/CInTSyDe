#ifndef  GX_H_
#define GX_H_			
#include "../inc/sdfchannel_gxsig.h"
#include "../inc/sdfchannel_absxsig.h"
void actor_Gx(circularFIFO_gxsig* channel_gx_ptr, const size_t gx_rate
 ,circularFIFO_absxsig* channel_resx_ptr,const size_t resx_rate
);

#endif		
