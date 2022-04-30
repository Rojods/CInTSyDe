#include "../inc/config.h"
/*
=================================================
	Define Task Stack
=================================================
*/
StackType_t task_StartTask_stk[TASK_STACKSIZE]; 
StaticTask_t tcb_start;
/*
=================================================
	Declare Extern Task Stack
=================================================
*/		
/*
=================================================
	Declare Extern Message Queue
=================================================
*/	
/*
=================================================
	Declare Extern Software Timer and Semaphore
=================================================
*/	
void init_msg_queue();
void init_soft_timer();
void init_semaphore();
void init_actor_task();		
void timer_start(){
	/* Initilize Message Queue     */
	init_msg_queue();
	
	/* Initilize Software Timer    */
	init_soft_timer();
	
	/* Initilize Timer's Semaphore */
	init_semaphore();
	
	/* Initilize Actor Tasks       */
	init_actor_task();
	
	/* Start Software Timer        */
	timer_start();
	
	/* Delete start task           */
	vTaskDelete(NULL); 
	
}
static void init_msg_queue(){
	/* channel GrayScaleToAbs */
	extern QueueHandle_t msg_queue_GrayScaleToAbs;
	extern int queue_length_GrayScaleToAbs;
	extern long item_size_GrayScaleToAbs;
	
	/* channel AbsY */
	extern QueueHandle_t msg_queue_AbsY;
	extern int queue_length_AbsY;
	extern long item_size_AbsY;
	
	/* channel AbsX */
	extern QueueHandle_t msg_queue_AbsX;
	extern int queue_length_AbsX;
	extern long item_size_AbsX;
	
	/* channel GrayScaleToGetPx */
	extern QueueHandle_t msg_queue_GrayScaleToGetPx;
	extern int queue_length_GrayScaleToGetPx;
	extern long item_size_GrayScaleToGetPx;
	
	/* channel gysig */
	extern QueueHandle_t msg_queue_gysig;
	extern int queue_length_gysig;
	extern long item_size_gysig;
	
	/* channel gxsig */
	extern QueueHandle_t msg_queue_gxsig;
	extern int queue_length_gxsig;
	extern long item_size_gxsig;
	
	/* channel absysig */
	extern QueueHandle_t msg_queue_absysig;
	extern int queue_length_absysig;
	extern long item_size_absysig;
	
	/* channel GrayScaleX */
	extern QueueHandle_t msg_queue_GrayScaleX;
	extern int queue_length_GrayScaleX;
	extern long item_size_GrayScaleX;
	
	/* channel absxsig */
	extern QueueHandle_t msg_queue_absxsig;
	extern int queue_length_absxsig;
	extern long item_size_absxsig;
	
	/* channel GrayScaleY */
	extern QueueHandle_t msg_queue_GrayScaleY;
	extern int queue_length_GrayScaleY;
	extern long item_size_GrayScaleY;
	
	msg_queue_GrayScaleToAbs=xQueueCreate(queue_length_GrayScaleToAbs,item_size_GrayScaleToAbs);
	msg_queue_AbsY=xQueueCreate(queue_length_AbsY,item_size_AbsY);
	msg_queue_AbsX=xQueueCreate(queue_length_AbsX,item_size_AbsX);
	msg_queue_GrayScaleToGetPx=xQueueCreate(queue_length_GrayScaleToGetPx,item_size_GrayScaleToGetPx);
	msg_queue_gysig=xQueueCreate(queue_length_gysig,item_size_gysig);
	msg_queue_gxsig=xQueueCreate(queue_length_gxsig,item_size_gxsig);
	msg_queue_absysig=xQueueCreate(queue_length_absysig,item_size_absysig);
	msg_queue_GrayScaleX=xQueueCreate(queue_length_GrayScaleX,item_size_GrayScaleX);
	msg_queue_absxsig=xQueueCreate(queue_length_absxsig,item_size_absxsig);
	msg_queue_GrayScaleY=xQueueCreate(queue_length_GrayScaleY,item_size_GrayScaleY);
}
static void init_soft_timer(){
	/* actor Gx*/
	extern TimerHandle_t task_timer_Gx;
	task_timer_Gx=xTimerCreate(
											"timer_Gx"
											, pdMS_TO_TICKS(4147)
											, pdTRUE
											,0
											,timer_Gx_callback
											);
														
	/* actor Abs*/
	extern TimerHandle_t task_timer_Abs;
	task_timer_Abs=xTimerCreate(
											"timer_Abs"
											, pdMS_TO_TICKS(1411)
											, pdTRUE
											,0
											,timer_Abs_callback
											);
														
	/* actor GrayScale*/
	extern TimerHandle_t task_timer_GrayScale;
	task_timer_GrayScale=xTimerCreate(
											"timer_GrayScale"
											, pdMS_TO_TICKS(-1)
											, pdTRUE
											,0
											,timer_GrayScale_callback
											);
														
	/* actor Gy*/
	extern TimerHandle_t task_timer_Gy;
	task_timer_Gy=xTimerCreate(
											"timer_Gy"
											, pdMS_TO_TICKS(4146)
											, pdTRUE
											,0
											,timer_Gy_callback
											);
														
	/* actor getPx*/
	extern TimerHandle_t task_timer_getPx;
	task_timer_getPx=xTimerCreate(
											"timer_getPx"
											, pdMS_TO_TICKS(4356)
											, pdTRUE
											,0
											,timer_getPx_callback
											);
														
}
static void init_semaphore(){
	/* actor Gx*/
	extern SemaphoreHandle_t timer_sem_Gx;
	task_sem_Gx=xSemaphoreCreateBinary();
				
	/* actor Abs*/
	extern SemaphoreHandle_t timer_sem_Abs;
	task_sem_Abs=xSemaphoreCreateBinary();
				
	/* actor GrayScale*/
	extern SemaphoreHandle_t timer_sem_GrayScale;
	task_sem_GrayScale=xSemaphoreCreateBinary();
				
	/* actor Gy*/
	extern SemaphoreHandle_t timer_sem_Gy;
	task_sem_Gy=xSemaphoreCreateBinary();
				
	/* actor getPx*/
	extern SemaphoreHandle_t timer_sem_getPx;
	task_sem_getPx=xSemaphoreCreateBinary();
				
}
static void init_actor_task(){
	/* actor Gx*/
	extern StackType_t task_Gx_stk[];
	extern StaticTask_t tcb_Gx;
	xTaskCreateStatic(task_Gx
						,"Gx"
						,GX_STACKSIZE
						,NULL
						,configMAX_PRIORITIES-2
						,task_Gx_stk,
						&tcb_Gx
						);	
								
	/* actor Abs*/
	extern StackType_t task_Abs_stk[];
	extern StaticTask_t tcb_Abs;
	xTaskCreateStatic(task_Abs
						,"Abs"
						,ABS_STACKSIZE
						,NULL
						,configMAX_PRIORITIES-2
						,task_Abs_stk,
						&tcb_Abs
						);	
								
	/* actor GrayScale*/
	extern StackType_t task_GrayScale_stk[];
	extern StaticTask_t tcb_GrayScale;
	xTaskCreateStatic(task_GrayScale
						,"GrayScale"
						,GRAYSCALE_STACKSIZE
						,NULL
						,configMAX_PRIORITIES-2
						,task_GrayScale_stk,
						&tcb_GrayScale
						);	
								
	/* actor Gy*/
	extern StackType_t task_Gy_stk[];
	extern StaticTask_t tcb_Gy;
	xTaskCreateStatic(task_Gy
						,"Gy"
						,GY_STACKSIZE
						,NULL
						,configMAX_PRIORITIES-2
						,task_Gy_stk,
						&tcb_Gy
						);	
								
	/* actor getPx*/
	extern StackType_t task_getPx_stk[];
	extern StaticTask_t tcb_getPx;
	xTaskCreateStatic(task_getPx
						,"getPx"
						,GETPX_STACKSIZE
						,NULL
						,configMAX_PRIORITIES-2
						,task_getPx_stk,
						&tcb_getPx
						);	
								
}
static void timer_start(){
	xTimerStart(task_timer_Gx, portMAX_DELAY);		
	xTimerStart(task_timer_Abs, portMAX_DELAY);		
	xTimerStart(task_timer_GrayScale, portMAX_DELAY);		
	xTimerStart(task_timer_Gy, portMAX_DELAY);		
	xTimerStart(task_timer_getPx, portMAX_DELAY);		
}

