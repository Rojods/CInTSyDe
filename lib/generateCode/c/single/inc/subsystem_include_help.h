#ifndef SUBSYSTEM_INCLUDE_HELP_H_
#define SUBSYSTEM_INCLUDE_HELP_H_

/*
****************************************************************
The aim of this .h file is to help subsystem.c
Only the subsystem.c includes this file.
****************************************************************
*/
#include "datatype_definition.h"
#include "circular_fifo_lib.h"
/*
==============================================
	Extern Variables 
==============================================
*/		
/* extern sdfchannel GrayScaleToAbs*/
extern type buffer_GrayScaleToAbs[];
extern int buffer_GrayScaleToAbs_size;
extern circular_fifo_type fifo_GrayScaleToAbs;

/* extern sdfchannel AbsY*/
extern type buffer_AbsY[];
extern int buffer_AbsY_size;
extern circular_fifo_type fifo_AbsY;

/* extern sdfchannel AbsX*/
extern type buffer_AbsX[];
extern int buffer_AbsX_size;
extern circular_fifo_type fifo_AbsX;

/* extern sdfchannel GrayScaleToGetPx*/
extern type buffer_GrayScaleToGetPx[];
extern int buffer_GrayScaleToGetPx_size;
extern circular_fifo_type fifo_GrayScaleToGetPx;

/* extern sdfchannel gysig*/
extern type buffer_gysig[];
extern int buffer_gysig_size;
extern circular_fifo_type fifo_gysig;

/* extern sdfchannel gxsig*/
extern type buffer_gxsig[];
extern int buffer_gxsig_size;
extern circular_fifo_type fifo_gxsig;

/* extern sdfchannel absysig*/
extern type buffer_absysig[];
extern int buffer_absysig_size;
extern circular_fifo_type fifo_absysig;

/* extern sdfchannel GrayScaleX*/
extern type buffer_GrayScaleX[];
extern int buffer_GrayScaleX_size;
extern circular_fifo_type fifo_GrayScaleX;

/* extern sdfchannel absxsig*/
extern type buffer_absxsig[];
extern int buffer_absxsig_size;
extern circular_fifo_type fifo_absxsig;

/* extern sdfchannel GrayScaleY*/
extern type buffer_GrayScaleY[];
extern int buffer_GrayScaleY_size;
extern circular_fifo_type fifo_GrayScaleY;


#endif
