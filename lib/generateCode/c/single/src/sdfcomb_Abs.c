#include <stdlib.h>
#include <stdio.h>
#include "../inc/sdfcomb_Abs.h"
#include "../inc/config.h"
#include "../inc/spinlock.h"
extern spinlock spinlock_absxsig;
extern spinlock spinlock_absysig;
extern spinlock spinlock_outputImage;

inline static void read_channel_Abs_resx(circularFIFO_absxsig* src_channel_ptr, const size_t num, token_absxsig  dst[]){
	//#if defined SINGLE
		for(size_t i=0 ; i < num ;++i){
			#if ABSXSIG_NONBLOCKING==1
				if(read_circularFIFO_non_blocking_absxsig(src_channel_ptr,dst+i) ==-1){
			#else
				if(read_circularFIFO_blocking_absxsig(src_channel_ptr,dst+i,&spinlock_absxsig) ==-1){
			#endif		
				//error
				//abort();
			}
	}
}
inline static void read_channel_Abs_resy(circularFIFO_absysig* src_channel_ptr, const size_t num, token_absysig  dst[]){
	//#if defined SINGLE
		for(size_t i=0 ; i < num ;++i){
			#if ABSYSIG_NONBLOCKING==1
				if(read_circularFIFO_non_blocking_absysig(src_channel_ptr,dst+i) ==-1){
			#else
				if(read_circularFIFO_blocking_absysig(src_channel_ptr,dst+i,&spinlock_absysig) ==-1){
			#endif		
				//error
				//abort();
			}
	}
}


inline static void write_channel_Abs_imgOutput(token_outputImage src[],const size_t num,circularFIFO_outputImage* dst_channel_ptr){
	for(size_t i=0 ; i < num ;++i){
		#if OUTPUTIMAGE_NONBLOCKING==1
			if(write_circularFIFO_non_blocking_outputImage(dst_channel_ptr,src[i]) ==-1){
		#else
			if(write_circularFIFO_blocking_outputImage(dst_channel_ptr,src[i],&spinlock_outputImage) ==-1){
		#endif
				//error
			}
		}
}


inline static void combinator(	
token_absxsig resx[] , const size_t resx_rate,
token_absysig resy[] , const size_t resy_rate
 ,token_outputImage  imgOutput[],const size_t imgOutput_rate
){
	printf("in actor Abs\n");

}
	
inline void actor_Abs(circularFIFO_absxsig* channel_resx_ptr, const size_t resx_rate,
circularFIFO_absysig* channel_resy_ptr, const size_t resy_rate
 ,circularFIFO_outputImage* channel_imgOutput_ptr,const size_t imgOutput_rate
){

	token_absxsig resx[resx_rate];
	token_absysig resy[resy_rate];
	token_outputImage imgOutput[imgOutput_rate];
	read_channel_Abs_resx(channel_resx_ptr,resx_rate,resx); 
	
	read_channel_Abs_resy(channel_resy_ptr,resy_rate,resy);

		
	combinator(resx,resx_rate,resy,resy_rate , imgOutput,imgOutput_rate );	
	write_channel_Abs_imgOutput(imgOutput,imgOutput_rate,channel_imgOutput_ptr);

	}



				
