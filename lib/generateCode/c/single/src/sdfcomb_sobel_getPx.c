#include <stdlib.h>
#include <stdio.h>
#include "../inc/sdfcomb_sobel_getPx.h"
#include "../inc/config.h"
#include "../inc/spinlock.h"
extern spinlock spinlock_gxsig;
extern spinlock spinlock_gysig;


inline static void write_channel_sobel_getPx_gx(token_gxsig src[],const size_t num,circularFIFO_gxsig* dst_channel_ptr){
	for(size_t i=0 ; i < num ;++i){
		#if GXSIG_NONBLOCKING==1
			if(write_circularFIFO_non_blocking_gxsig(dst_channel_ptr,src[i]) ==-1){
		#else
			if(write_circularFIFO_blocking_gxsig(dst_channel_ptr,src[i],&spinlock_gxsig) ==-1){
		#endif
				//error
			}
		}
}
inline static void write_channel_sobel_getPx_gy(token_gysig src[],const size_t num,circularFIFO_gysig* dst_channel_ptr){
	for(size_t i=0 ; i < num ;++i){
		#if GYSIG_NONBLOCKING==1
			if(write_circularFIFO_non_blocking_gysig(dst_channel_ptr,src[i]) ==-1){
		#else
			if(write_circularFIFO_blocking_gysig(dst_channel_ptr,src[i],&spinlock_gysig) ==-1){
		#endif
				//error
			}
		}
}


inline static void combinator(	
token_gxsig gx[],const size_t gx_rate,
token_gysig gy[],const size_t gy_rate
){
	printf("in actor sobel_getPx\n");

}
	
inline void actor_sobel_getPx(circularFIFO_gxsig* channel_gx_ptr,const size_t gx_rate,
circularFIFO_gysig* channel_gy_ptr,const size_t gy_rate
){

	token_gxsig gx[gx_rate];
	token_gysig gy[gy_rate];
	
	combinator(gx,gx_rate,gy,gy_rate );	
	write_channel_sobel_getPx_gx(gx,gx_rate,channel_gx_ptr); 
	
	write_channel_sobel_getPx_gy(gy,gy_rate,channel_gy_ptr);

	}



				
