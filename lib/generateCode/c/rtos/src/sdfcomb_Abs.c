#include "../inc/config.h"
/*
==============================================
	Define Task Stack
==============================================
*/
StackType_t task_Abs_stk[ABS_STACKSIZE];
StaticTask_t tcb_Abs;

/*
==============================================
	Declare Extern Message Queue Handler
==============================================
*/
/* Input Message Queue */
extern QueueHandle_t msg_queue_GrayScaleToAbs;
extern QueueHandle_t msg_queue_AbsY;
extern QueueHandle_t msg_queue_AbsX;
extern QueueHandle_t msg_queue_absysig;
extern QueueHandle_t msg_queue_absxsig;
/* Output Message Quueue */
/*
==============================================
	Define Soft Timer and Soft Timer Semaphore
==============================================
*/

SemaphoreHandle_t timer_sem_Abs;
TimerHandle_t timer_Abs;
static void timer_Abs_callback(TimerHandle_t xTimer);
/*
==============================================
	Define Task Function
==============================================
*/
void task_Abs(void* pdata){
	/* Initilize Memory           */
	Array2OfUInt16  dims;
	UInt16  offsetXIn;
	UInt16  offsetYIn;
	ArrayXOfArrayXOfDoubleType  system_img_sink_address;
	DoubleType  resy;
	DoubleType  resx;
	UInt16  offsetYOut;
	UInt16  offsetXOut;
	while(1){
		/* Read From»hannel      */
		read_nonblocking(offsetXIn_channel);
		read_nonblocking(offsetYIn_channel);
		read_nonblocking(resy_channel);
		read_nonblocking(resx_channel);
		for(int i=0;i<2;++i){
			read_nonblocking(dimsIn_channel);
		}
		/* Inline Code            */
		//in combFunction AbsImpl
		if(resx<0.0)resx=-resx;
		if(resy<0.0)resy=-resy;
		if(offsetX>=dims[0]-2){offsetY+=1;
		offsetX=0;
		}if(offsetY>=dims[1]-2){offsetY=0;
		}system_img_sink_address[offsetX][offsetY]=resx+resy;
		/* Write To Channel       */
		write(offsetYOut_channel);
		write(offsetXOut_channel);
		/* Pend Timer's Semaphore */	
		xSemaphoreTake(task_sem_Abs, portMAX_DELAY);	
	
	}
	
	
}

/*
=============================================
Soft Timer Callback Function
=============================================
*/
void timer_Abs_callback(TimerHandle_t xTimer){
	xSemaphoreGive(task_sem_Abs);
}
