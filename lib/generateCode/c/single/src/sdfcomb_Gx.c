/* Includes-------------------------- */
#include "../inc/datatype_definition.h"
/*
========================================
	Declare Extern Channal Variables
========================================
*/

/*
========================================
	Actor Function
========================================
*/			
inline void actor_Gx(){
	/* Initilize Memory      */
	Double  gx;
	Array6OfDoubleType  imgBlockX;
	
	/* Read From Input Port  */
	/* Inline Code           */
	//in combFunction GxImpl
	gx=gx-imgBlockX[0];
	gx=gx+imgBlockX[1];
	gx=gx-2.0*imgBlockX[2];
	gx=gx+2.0*imgBlockX[3];
	gx=gx-imgBlockX[4];
	gx=gx+imgBlockX[5];

	/* Write To Output Ports */
}
