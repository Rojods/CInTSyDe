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
inline void actor_getPx(){
	/* Initilize Memory      */
	Array6OfDoubleType  gray;
	Array6OfDoubleType  imgBlockX;
	Array6OfDoubleType  imgBlockY;
	
	/* Read From Input Port  */
	for(int i=0;i<6;++i){
		read_non_blocking(&channel,gray+i);
	}
	/* Inline Code           */
	//in combFunction getPxImpl1
	imgBlockX[0]=gray[0];
	imgBlockX[1]=gray[1];
	imgBlockX[2]=gray[2];
	imgBlockX[3]=gray[3];
	imgBlockX[4]=gray[4];
	imgBlockX[5]=gray[5];
	//in combFunction getPxImpl2
	imgBlockY[0]=gray[0];
	imgBlockY[1]=gray[1];
	imgBlockY[2]=gray[2];
	imgBlockY[3]=gray[3];
	imgBlockY[4]=gray[4];
	imgBlockY[5]=gray[5];

	/* Write To Output Ports */
	for(int i=0;i<6;++i){
		write(gx_channel);
	}
	for(int i=0;i<6;++i){
		write(gy_channel);
	}
	for(int i=0;i<6;++i){
		write(copyY_channel);
	}
	for(int i=0;i<6;++i){
		write(copyX_channel);
	}
}
