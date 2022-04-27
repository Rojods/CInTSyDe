#include <stdlib.h>
#include <stdio.h>
#include "../inc/sdfcomb_getPx.h"
#include "../inc/config.h"
#include "../inc/spinlock.h"
extern spinlock spinlock_inputImage;
extern spinlock spinlock_gxsig;
extern spinlock spinlock_gysig;

inline static void read_channel_getPx_imgInput(circularFIFO_inputImage* src_channel_ptr, const size_t num, token_inputImage  dst[]){
	//#if defined SINGLE
		for(size_t i=0 ; i < num ;++i){
			#if INPUTIMAGE_NONBLOCKING==1
				if(read_circularFIFO_non_blocking_inputImage(src_channel_ptr,dst+i) ==-1){
			#else
				if(read_circularFIFO_blocking_inputImage(src_channel_ptr,dst+i,&spinlock_inputImage) ==-1){
			#endif		
				//error
				//abort();
			}
	}
}


inline static void write_channel_getPx_gx(token_gxsig src[],const size_t num,circularFIFO_gxsig* dst_channel_ptr){
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
inline static void write_channel_getPx_gy(token_gysig src[],const size_t num,circularFIFO_gysig* dst_channel_ptr){
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
token_inputImage imgInput[] , const size_t imgInput_rate
 ,token_gxsig  gx[],const size_t gx_rate 
,token_gysig  gy[],const size_t gy_rate
){
	printf("in actor getPx\n");

}
	
inline void actor_getPx(circularFIFO_inputImage* channel_imgInput_ptr, const size_t imgInput_rate
 ,circularFIFO_gxsig* channel_gx_ptr,const size_t gx_rate 
,circularFIFO_gysig* channel_gy_ptr,const size_t gy_rate
){

	token_inputImage imgInput[imgInput_rate];
	token_gxsig gx[gx_rate];
	token_gysig gy[gy_rate];
	read_channel_getPx_imgInput(channel_imgInput_ptr,imgInput_rate,imgInput);

		
	combinator(imgInput,imgInput_rate , gx,gx_rate , gy,gy_rate );	
	write_channel_getPx_gx(gx,gx_rate,channel_gx_ptr); 
	
	write_channel_getPx_gy(gy,gy_rate,channel_gy_ptr);

	}



				
