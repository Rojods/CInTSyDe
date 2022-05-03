#include "../inc/config.h"
/*
==============================================
	Define Task Stack
==============================================
*/
#if FREERTOS==1
StackType_t task_Gx_stk[GX_STACKSIZE];
StaticTask_t tcb_Gx;
#endif
/*
==============================================
	Declare Extern Message Queue Handler
==============================================
*/
/* Input Message Queue */
#if FREERTOS==1
extern QueueHandle_t msg_queue_gxsig;
/* Output Message Quueue */
extern QueueHandle_t msg_queue_absxsig;
#endif
/*
==============================================
	Define Soft Timer and Soft Timer Semaphore
==============================================
*/
#if FREERTOS==1
SemaphoreHandle_t timer_sem_Gx;
TimerHandle_t timer_Gx;
static void timer_Gx_callback(TimerHandle_t xTimer);
#endif
/*
==============================================
	Define Task Function
==============================================
*/
void task_Gx(void* pdata){
	/* Initilize Memory           */
	DoubleType  gx;
	Array6OfDoubleType  imgBlockX;
	while(1){
		/* Read From»hannel      */
		for(int i=0;i<6;++i){
			read_nonblocking(gx_channel);
		}
		/* Inline Code            */
		//in combFunction GxImpl
		gx=gx-imgBlockX[0];
		gx=gx+imgBlockX[1];
		gx=gx-2.0*imgBlockX[2];
		gx=gx+2.0*imgBlockX[3];
		gx=gx-imgBlockX[4];
		gx=gx+imgBlockX[5];
		/* Write To Channel       */
		write(resx_channel);
		/* Pend Timer's Semaphore */	
		xSemaphoreTake(task_sem_Gx, portMAX_DELAY);	
	
	}
	
	
}

/*
=============================================
Soft Timer Callback Function
=============================================
*/
#if FREERTOS==1
void timer_Gx_callback(TimerHandle_t xTimer){
	xSemaphoreGive(task_sem_Gx);
}
#endif
