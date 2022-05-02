/* Includes-------------------------- */
#include "../inc/datatype_definition.h"
/*
========================================
	Declare Extern Channal Variables
========================================
*/
extern fifo_gysig;

extern fifo_absysig;
/*
========================================
	Actor Function
========================================
*/			
inline void actor_Gy(){
	/* Initilize Memory      */
	Array6OfDoubleType imgBlockY; 
	DoubleType gy; 
	
	/* Read From Input Port  */
	error imgBlockY;
	/* Inline Code           */
	//in combFunction GyImpl
	gy=gy+imgBlockY[0];
	gy=gy+2.0*imgBlockY[1];
	gy=gy+imgBlockY[2];
	gy=gy-imgBlockY[3];
	gy=gy-2.0*imgBlockY[4];
	gy=gy-imgBlockY[5];

	/* Write To Output Ports */
	error gy;
}
