#include <stdlib.h>
#include <stdio.h>
#include "../inc/sdfcomb_sobel_Gy.h"
#include "../inc/config.h"
#include "../inc/spinlock.h"
extern spinlock spinlock_gysig;
extern spinlock spinlock_absysig;

inline static void read_channel_sobel_Gy_gy(circularFIFO_gysig* src_channel_ptr, const size_t num, token_gysig  dst[]){
	//#if defined SINGLE
		for(size_t i=0 ; i < num ;++i){
			#if GYSIG_NONBLOCKING==1
				if(read_circularFIFO_non_blocking_gysig(src_channel_ptr,dst+i) ==-1){
			#else
				if(read_circularFIFO_blocking_gysig(src_channel_ptr,dst+i,&spinlock_gysig) ==-1){
			#endif		
				//error
				//abort();
			}
	}
}


inline static void write_channel_sobel_Gy_resy(token_absysig src[],const size_t num,circularFIFO_absysig* dst_channel_ptr){
	for(size_t i=0 ; i < num ;++i){
		#if ABSYSIG_NONBLOCKING==1
			if(write_circularFIFO_non_blocking_absysig(dst_channel_ptr,src[i]) ==-1){
		#else
			if(write_circularFIFO_blocking_absysig(dst_channel_ptr,src[i],&spinlock_absysig) ==-1){
		#endif
				//error
			}
		}
}


inline static void combinator(	
token_gysig gy[] , const size_t gy_rate
 ,token_absysig  resy[],const size_t resy_rate
){
	printf("in actor sobel_Gy\n");

}
	
inline void actor_sobel_Gy(circularFIFO_gysig* channel_gy_ptr, const size_t gy_rate
 ,circularFIFO_absysig* channel_resy_ptr,const size_t resy_rate
){

	token_gysig gy[gy_rate];
	token_absysig resy[resy_rate];
	read_channel_sobel_Gy_gy(channel_gy_ptr,gy_rate,gy);

		
	combinator(gy,gy_rate , resy,resy_rate );	
	write_channel_sobel_Gy_resy(resy,resy_rate,channel_resy_ptr);

	}



				
