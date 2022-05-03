#include "../inc/config.h"
/*
==============================================
	Define Task Stack
==============================================
*/
#if FREERTOS==1
StackType_t task_Gy_stk[GY_STACKSIZE];
StaticTask_t tcb_Gy;
#endif
/*
==============================================
	Declare Extern Message Queue Handler
==============================================
*/
/* Input Message Queue */
#if FREERTOS==1
extern QueueHandle_t msg_queue_gysig;
/* Output Message Quueue */
extern QueueHandle_t msg_queue_absysig;
#endif
/*
==============================================
	Define Soft Timer and Soft Timer Semaphore
==============================================
*/
#if FREERTOS==1
SemaphoreHandle_t timer_sem_Gy;
TimerHandle_t timer_Gy;
static void timer_Gy_callback(TimerHandle_t xTimer);
#endif
/*
==============================================
	Define Task Function
==============================================
*/
void task_Gy(void* pdata){
	/* Initilize Memory           */
	DoubleType  gy;
	Array6OfDoubleType  imgBlockY;
	while(1){
		/* Read From»hannel      */
		for(int i=0;i<6;++i){
			read_nonblocking(gy_channel);
		}
		/* Inline Code            */
		//in combFunction GyImpl
		gy=gy+imgBlockY[0];
		gy=gy+2.0*imgBlockY[1];
		gy=gy+imgBlockY[2];
		gy=gy-imgBlockY[3];
		gy=gy-2.0*imgBlockY[4];
		gy=gy-imgBlockY[5];
		/* Write To Channel       */
		write(resy_channel);
		/* Pend Timer's Semaphore */	
		xSemaphoreTake(task_sem_Gy, portMAX_DELAY);	
	
	}
	
	
}

/*
=============================================
Soft Timer Callback Function
=============================================
*/
#if FREERTOS==1
void timer_Gy_callback(TimerHandle_t xTimer){
	xSemaphoreGive(task_sem_Gy);
}
#endif
