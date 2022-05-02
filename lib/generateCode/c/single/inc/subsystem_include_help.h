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
extern UInt16 buffer_GrayScaleToAbs[];
extern int buffer_GrayScaleToAbs_size;
extern circular_fifo_UInt16 fifo_GrayScaleToAbs;

/* extern sdfchannel AbsY*/
extern UInt16 buffer_AbsY[];
extern int buffer_AbsY_size;
extern circular_fifo_UInt16 fifo_AbsY;

/* extern sdfchannel AbsX*/
extern UInt16 buffer_AbsX[];
extern int buffer_AbsX_size;
extern circular_fifo_UInt16 fifo_AbsX;

/* extern sdfchannel GrayScaleToGetPx*/
extern Array6OfDoubleType buffer_GrayScaleToGetPx[];
extern int buffer_GrayScaleToGetPx_size;
extern circular_fifo_Array6OfDoubleType fifo_GrayScaleToGetPx;

/* extern sdfchannel gysig*/
extern Array6OfDoubleType buffer_gysig[];
extern int buffer_gysig_size;
extern circular_fifo_Array6OfDoubleType fifo_gysig;

/* extern sdfchannel gxsig*/
extern Array6OfDoubleType buffer_gxsig[];
extern int buffer_gxsig_size;
extern circular_fifo_Array6OfDoubleType fifo_gxsig;

/* extern sdfchannel absysig*/
extern UInt16 buffer_absysig[];
extern int buffer_absysig_size;
extern circular_fifo_UInt16 fifo_absysig;

/* extern sdfchannel GrayScaleX*/
extern UInt16 buffer_GrayScaleX[];
extern int buffer_GrayScaleX_size;
extern circular_fifo_UInt16 fifo_GrayScaleX;

/* extern sdfchannel absxsig*/
extern UInt16 buffer_absxsig[];
extern int buffer_absxsig_size;
extern circular_fifo_UInt16 fifo_absxsig;

/* extern sdfchannel GrayScaleY*/
extern UInt16 buffer_GrayScaleY[];
extern int buffer_GrayScaleY_size;
extern circular_fifo_UInt16 fifo_GrayScaleY;


#endif
