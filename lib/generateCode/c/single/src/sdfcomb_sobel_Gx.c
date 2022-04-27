#include <stdlib.h>
#include <stdio.h>
#include "../inc/sdfcomb_sobel_Gx.h"
#include "../inc/config.h"
#include "../inc/spinlock.h"
extern spinlock spinlock_gxsig;
extern spinlock spinlock_absxsig;

inline static void read_channel_sobel_Gx_gx(circularFIFO_gxsig* src_channel_ptr, const size_t num, token_gxsig  dst[]){
	//#if defined SINGLE
		for(size_t i=0 ; i < num ;++i){
			#if GXSIG_NONBLOCKING==1
				if(read_circularFIFO_non_blocking_gxsig(src_channel_ptr,dst+i) ==-1){
			#else
				if(read_circularFIFO_blocking_gxsig(src_channel_ptr,dst+i,&spinlock_gxsig) ==-1){
			#endif		
				//error
				//abort();
			}
	}
}


inline static void write_channel_sobel_Gx_resx(token_absxsig src[],const size_t num,circularFIFO_absxsig* dst_channel_ptr){
	for(size_t i=0 ; i < num ;++i){
		#if ABSXSIG_NONBLOCKING==1
			if(write_circularFIFO_non_blocking_absxsig(dst_channel_ptr,src[i]) ==-1){
		#else
			if(write_circularFIFO_blocking_absxsig(dst_channel_ptr,src[i],&spinlock_absxsig) ==-1){
		#endif
				//error
			}
		}
}


inline static void combinator(	
token_gxsig gx[] , const size_t gx_rate
 ,token_absxsig  resx[],const size_t resx_rate
){
	printf("in actor sobel_Gx\n");

}
	
inline void actor_sobel_Gx(circularFIFO_gxsig* channel_gx_ptr, const size_t gx_rate
 ,circularFIFO_absxsig* channel_resx_ptr,const size_t resx_rate
){

	token_gxsig gx[gx_rate];
	token_absxsig resx[resx_rate];
	read_channel_sobel_Gx_gx(channel_gx_ptr,gx_rate,gx);

		
	combinator(gx,gx_rate , resx,resx_rate );	
	write_channel_sobel_Gx_resx(resx,resx_rate,channel_resx_ptr);

	}



				
