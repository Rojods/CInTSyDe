/* Includes-------------------------- */
#include "../inc/datatype_definition.h"
/*
========================================
	Declare Extern Channal Variables
========================================
*/
/* Input FIFO */
extern fifo_gysig;
/* Output FIFO */
extern fifo_absysig;
/*
========================================
	Actor Function
========================================
*/			
inline void actor_Gy(){
	/* Initilize Memory      */
	DoubleType gy; 
	Array6OfDoubleType imgBlockY; 
	/* Read From Input Port  */
	for(int i=0;i<6;++i){
		read_non_blocking_DoubleType(&fifo_gysig,&imgBlockY[i]);
	}
	/* Inline Code           */
	/* in combFunction GyImpl */
	gy=0;
	gy=gy+imgBlockY[0];
	gy=gy+2.0*imgBlockY[1];
	gy=gy+imgBlockY[2];
	gy=gy-imgBlockY[3];
	gy=gy-2.0*imgBlockY[4];
	gy=gy-imgBlockY[5];

	/* Write To Output Ports */
	write_non_blocking_DoubleType(&fifo_absysig,gy);
}
