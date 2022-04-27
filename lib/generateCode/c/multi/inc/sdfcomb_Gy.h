#ifndef  GY_H_
#define GY_H_			
#include "../inc/sdfchannel_gysig.h"
#include "../inc/sdfchannel_absysig.h"
void actor_Gy(circularFIFO_gysig* channel_gy_ptr, const size_t gy_rate
 ,circularFIFO_absysig* channel_resy_ptr,const size_t resy_rate
);

#endif		
