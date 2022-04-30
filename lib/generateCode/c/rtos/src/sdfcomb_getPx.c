#include "../inc/config.h"
/*
==============================================
	Define Task Stack
==============================================
*/
StackType_t task_getPx_stk[GETPX_STACKSIZE];
StaticTask_t tcb_getPx;

/*
==============================================
	Declare Extern Message Queue Handler
==============================================
*/

/*
==============================================
	Define Soft Timer and Soft Timer Semaphore
==============================================
*/

SemaphoreHandle_t timer_sem_getPx;
TimerHandle_t timer_getPx;
//void timer_getPx_callback(TimerHandle_t xTimer);
/*
==============================================
	Define Task Function
==============================================
*/
void task_getPx(void* pdata){
	/* Initilize Memory           */
	while(1){
		/* Read From Channel      */
		for(int i=0;i<6;++i){
			read_nonblocking(gray_channel);
		}
		/* Inline Code            */
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
		/* Write To Channel       */
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
		/* Pend Timer's Semaphore */	
		xSemaphoreTake(task_sem_getPx, portMAX_DELAY);	
		
	}
	
	
}

/*
=============================================
Soft Timer Callback Function
=============================================
*/
void timer_getPx_callback(TimerHandle_t xTimer){
	xSemaphoreGive(task_sem_getPx);
}
