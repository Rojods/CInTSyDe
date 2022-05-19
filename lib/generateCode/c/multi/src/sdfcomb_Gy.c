	/* Includes-------------------------- */
	#include "../inc/config.h"
	#include "../inc/datatype_definition.h"
	
	#if SINGLECORE==1
	#include "../inc/circular_fifo_lib.h"
	#endif
	
	#if MULTICORE==1
	#include <cheap.h>
	#endif
	#include "../inc/sdfcomb_Gy.h"
	
	/*
	========================================
	Declare Extern Channal Variables
	========================================
	*/
	/* Input FIFO */
	#if SINGLECORE==1
		extern circular_fifo_DoubleType fifo_gysig;
		extern spinlock spinlock_gysig;
	#endif
	#if MULTICORE==1
		
	#endif
	
	/* Output FIFO */
	#if SINGLECORE==1
		extern circular_fifo_DoubleType fifo_absysig;
		extern spinlock spinlock_absysig;
	#endif
	/*
	========================================
		Declare Extern Global Variables
	========================================
	*/			
	
	/*
	========================================
		Actor Function
	========================================
	*/			
void actor_Gy(){
				
/*  initialize memory*/
DoubleType gy; 
Array6OfDoubleType imgBlockY; 
	
	/* Read From Input Port  */
	int ret=0;
	for(int i=0;i<6;++i){
		
		#if GYSIG_BLOCKING==0
		ret=read_non_blocking_DoubleType(&fifo_gysig,&imgBlockY[i]);
		if(ret==-1){
			printf("fifo_gysig read error\n");
		}
		#else
		read_blocking_DoubleType(&fifo_gysig,&imgBlockY[i],&spinlock_gysig);
		#endif
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
	#if ABSYSIG_BLOCKING==0
	write_non_blocking_DoubleType(&fifo_absysig,gy);
	#else
	write_blocking_DoubleType(&fifo_absysig,gy,&spinlock_absysig);
	#endif
							

	}
