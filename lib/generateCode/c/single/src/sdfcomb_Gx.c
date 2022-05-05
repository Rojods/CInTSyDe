/* Includes-------------------------- */
#include "../inc/datatype_definition.h"
/*
========================================
	Declare Extern Channal Variables
========================================
*/
/* Input FIFO */
extern fifo_gxsig;
/* Output FIFO */
extern fifo_absxsig;
/*
========================================
	Actor Function
========================================
*/			
inline void actor_Gx(){
	/* Initilize Memory      */
	DoubleType gx; 
	Array6OfDoubleType imgBlockX; 
	/* Read From Input Port  */
	for(int i=0;i<6;++i){
		read_non_blocking_DoubleType(&fifo_gxsig,&imgBlockX[i]);
	}
	/* Inline Code           */
	/* in combFunction GxImpl */
	gx=0;
	gx=gx-imgBlockX[0];
	gx=gx+imgBlockX[1];
	gx=gx-2.0*imgBlockX[2];
	gx=gx+2.0*imgBlockX[3];
	gx=gx-imgBlockX[4];
	gx=gx+imgBlockX[5];

	/* Write To Output Ports */
	write_non_blocking_DoubleType(&fifo_absxsig,gx);
}
