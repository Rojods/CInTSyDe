#include "../inc/config.h"
#include "../inc/subsystem_init.h"
#include "../inc/circular_fifo_lib.h"
#include "../inc/datatype_definition.h"
/*
==============================================
	Extern Variables 
==============================================
*/	
	extern int ZeroValue;
	extern int OneValue;
			
	#if MULTICORE==1
	extern cheap fifo_admin_GrayScaleToAbs;
	extern unsigned int buffer_GrayScaleToAbs_size;
	extern unsigned int token_GrayScaleToAbs_size;
	extern volatile UInt16 buffer_GrayScaleToAbs[];
	/* extern sdfchannel AbsY*/
	extern UInt16 buffer_AbsY[];
	extern int buffer_AbsY_size;
	extern circular_fifo_UInt16 fifo_AbsY;
	extern cheap fifo_admin_gysig;
	extern unsigned int buffer_gysig_size;
	extern unsigned int token_gysig_size;
	extern volatile DoubleType buffer_gysig[];
	/* extern sdfchannel AbsX*/
	extern UInt16 buffer_AbsX[];
	extern int buffer_AbsX_size;
	extern circular_fifo_UInt16 fifo_AbsX;
	extern cheap fifo_admin_GrayScaleToGetPx;
	extern unsigned int buffer_GrayScaleToGetPx_size;
	extern unsigned int token_GrayScaleToGetPx_size;
	extern volatile DoubleType buffer_GrayScaleToGetPx[];
	extern cheap fifo_admin_gxsig;
	extern unsigned int buffer_gxsig_size;
	extern unsigned int token_gxsig_size;
	extern volatile DoubleType buffer_gxsig[];
	extern cheap fifo_admin_absysig;
	extern unsigned int buffer_absysig_size;
	extern unsigned int token_absysig_size;
	extern volatile DoubleType buffer_absysig[];
	extern cheap fifo_admin_absxsig;
	extern unsigned int buffer_absxsig_size;
	extern unsigned int token_absxsig_size;
	extern volatile DoubleType buffer_absxsig[];
	/* extern sdfchannel GrayScaleX*/
	extern UInt16 buffer_GrayScaleX[];
	extern int buffer_GrayScaleX_size;
	extern circular_fifo_UInt16 fifo_GrayScaleX;
	/* extern sdfchannel GrayScaleY*/
	extern UInt16 buffer_GrayScaleY[];
	extern int buffer_GrayScaleY_size;
	extern circular_fifo_UInt16 fifo_GrayScaleY;
	#endif			
int init_subsystem(){
	#if SINGLECORE==1
if (cheap_init_r (fifo_admin_GrayScaleToAbs, (void *) buffer_GrayScaleToAbs, buffer_GrayScaleToAbs_size, token_GrayScaleToAbs_size) == NULL) {
  //xil_printf("%04u/%010u: cheap_init_r failed\n", (uint32_t)(t>>32),(uint32_t)t);
  return 1;
}				
	init_channel_UInt16(&fifo_AbsY,buffer_AbsY,buffer_AbsY_size);
if (cheap_init_r (fifo_admin_gysig, (void *) buffer_gysig, buffer_gysig_size, token_gysig_size) == NULL) {
  //xil_printf("%04u/%010u: cheap_init_r failed\n", (uint32_t)(t>>32),(uint32_t)t);
  return 1;
}				
	init_channel_UInt16(&fifo_AbsX,buffer_AbsX,buffer_AbsX_size);
if (cheap_init_r (fifo_admin_GrayScaleToGetPx, (void *) buffer_GrayScaleToGetPx, buffer_GrayScaleToGetPx_size, token_GrayScaleToGetPx_size) == NULL) {
  //xil_printf("%04u/%010u: cheap_init_r failed\n", (uint32_t)(t>>32),(uint32_t)t);
  return 1;
}				
if (cheap_init_r (fifo_admin_gxsig, (void *) buffer_gxsig, buffer_gxsig_size, token_gxsig_size) == NULL) {
  //xil_printf("%04u/%010u: cheap_init_r failed\n", (uint32_t)(t>>32),(uint32_t)t);
  return 1;
}				
if (cheap_init_r (fifo_admin_absysig, (void *) buffer_absysig, buffer_absysig_size, token_absysig_size) == NULL) {
  //xil_printf("%04u/%010u: cheap_init_r failed\n", (uint32_t)(t>>32),(uint32_t)t);
  return 1;
}				
if (cheap_init_r (fifo_admin_absxsig, (void *) buffer_absxsig, buffer_absxsig_size, token_absxsig_size) == NULL) {
  //xil_printf("%04u/%010u: cheap_init_r failed\n", (uint32_t)(t>>32),(uint32_t)t);
  return 1;
}				
	init_channel_UInt16(&fifo_GrayScaleX,buffer_GrayScaleX,buffer_GrayScaleX_size);
	init_channel_UInt16(&fifo_GrayScaleY,buffer_GrayScaleY,buffer_GrayScaleY_size);
	
	write_non_blocking_UInt16(&fifo_AbsY,ZeroValue);
	write_non_blocking_UInt16(&fifo_AbsX,ZeroValue);
	write_non_blocking_UInt16(&fifo_GrayScaleX,ZeroValue);
	write_non_blocking_UInt16(&fifo_GrayScaleY,ZeroValue);
	#endif
	
	
	#if MULTICORE==1
	
	#endif
	return 0;
	}
