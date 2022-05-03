/* Includes-------------------------- */
#include "../inc/datatype_definition.h"
/*
========================================
	Declare Extern Channal Variables
========================================
*/
extern fifo_gxsig;

extern fifo_absxsig;
/*
========================================
	Actor Function
========================================
*/			
inline void actor_Gx(){
	/* Initilize Memory      */
	Array6OfDoubleType imgBlockX; 
	DoubleType gx; 
	/* Read From Input Port  */
	for(int i=0;i<6;++i){
		read_non_blocking(&fifo_gxsig,&imgBlockX[i]);
	}
	/* Inline Code           */
	//in combFunction GxImpl
	gx=gx-imgBlockX[0];
	gx=gx+imgBlockX[1];
	gx=gx-2.0*imgBlockX[2];
	gx=gx+2.0*imgBlockX[3];
	gx=gx-imgBlockX[4];
	gx=gx+imgBlockX[5];

	/* Write To Output Ports */
	write_non_blocking(&fifo_absxsig,&gx);
}
