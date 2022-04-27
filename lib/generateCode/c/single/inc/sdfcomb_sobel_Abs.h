#ifndef  SOBEL_ABS_H_
#define SOBEL_ABS_H_			
#include "../inc/sdfchannel_absxsig.h"
#include "../inc/sdfchannel_absysig.h"
void actor_sobel_Abs(circularFIFO_absxsig* channel_resx_ptr, const size_t resx_rate,
circularFIFO_absysig* channel_resy_ptr, const size_t resy_rate
);

#endif		
