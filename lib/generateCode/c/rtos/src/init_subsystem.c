#include "../inc/config.h"
#include "../inc/datatype_definition.h"
#include "FreeRTOS.h"
#include "semphr.h"
#include "timers.h"	
#include "queue.h"
#include "../inc/sdfcomb_Gx.h"
#include "../inc/sdfcomb_Abs.h"
#include "../inc/sdfcomb_Gy.h"
#include "../inc/sdfcomb_GrayScale.h"
#include "../inc/sdfcomb_getPx.h"
/*
=================================================
	Define StartTask Stack
=================================================
*/
StackType_t task_StartTask_stk[STARTTASK_STACKSIZE]; 
StaticTask_t tcb_start;
/*
=================================================
	Declare Extern Values
=================================================
*/		
extern int ZeroValue;
extern int OneValue;


static void init_msg_queue();
static void init_soft_timer();
static void init_semaphore();
static void init_actor_task();
static void timer_start();
		
void init_subsystem(){
	/* Initialize Message Queue     */
	init_msg_queue();
	
	/* Initialize Software Timer    */
	init_soft_timer();
	
	/* Initialize Timer's Semaphore */
	init_semaphore();
	
	/* Initialize Actor Tasks       */
	init_actor_task();
	
	/* Start Software Timer        */
	timer_start();
	
	/* Suspend All the Created Tasks */
	
	//vTaskStartScheduler();
	//vTaskDelete(NULL); 
	
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
	
	/* channel gysig */
	extern QueueHandle_t msg_queue_gysig;
	extern int queue_length_gysig;
	extern long item_size_gysig;
	
	/* channel AbsX */
	extern QueueHandle_t msg_queue_AbsX;
	extern int queue_length_AbsX;
	extern long item_size_AbsX;
	
	/* channel GrayScaleToGetPx */
	extern QueueHandle_t msg_queue_GrayScaleToGetPx;
	extern int queue_length_GrayScaleToGetPx;
	extern long item_size_GrayScaleToGetPx;
	
	/* channel gxsig */
	extern QueueHandle_t msg_queue_gxsig;
	extern int queue_length_gxsig;
	extern long item_size_gxsig;
	
	/* channel absysig */
	extern QueueHandle_t msg_queue_absysig;
	extern int queue_length_absysig;
	extern long item_size_absysig;
	
	/* channel absxsig */
	extern QueueHandle_t msg_queue_absxsig;
	extern int queue_length_absxsig;
	extern long item_size_absxsig;
	
	/* channel GrayScaleX */
	extern QueueHandle_t msg_queue_GrayScaleX;
	extern int queue_length_GrayScaleX;
	extern long item_size_GrayScaleX;
	
	/* channel GrayScaleY */
	extern QueueHandle_t msg_queue_GrayScaleY;
	extern int queue_length_GrayScaleY;
	extern long item_size_GrayScaleY;
	
	msg_queue_GrayScaleToAbs=xQueueCreate(queue_length_GrayScaleToAbs,item_size_GrayScaleToAbs);
	msg_queue_AbsY=xQueueCreate(queue_length_AbsY,item_size_AbsY);
	msg_queue_gysig=xQueueCreate(queue_length_gysig,item_size_gysig);
	msg_queue_AbsX=xQueueCreate(queue_length_AbsX,item_size_AbsX);
	msg_queue_GrayScaleToGetPx=xQueueCreate(queue_length_GrayScaleToGetPx,item_size_GrayScaleToGetPx);
	msg_queue_gxsig=xQueueCreate(queue_length_gxsig,item_size_gxsig);
	msg_queue_absysig=xQueueCreate(queue_length_absysig,item_size_absysig);
	msg_queue_absxsig=xQueueCreate(queue_length_absxsig,item_size_absxsig);
	msg_queue_GrayScaleX=xQueueCreate(queue_length_GrayScaleX,item_size_GrayScaleX);
	msg_queue_GrayScaleY=xQueueCreate(queue_length_GrayScaleY,item_size_GrayScaleY);
	
xQueueSend(msg_queue_AbsY,&ZeroValue,portMAX_DELAY);
xQueueSend(msg_queue_AbsX,&ZeroValue,portMAX_DELAY);
xQueueSend(msg_queue_GrayScaleX,&ZeroValue,portMAX_DELAY);
xQueueSend(msg_queue_GrayScaleY,&ZeroValue,portMAX_DELAY);
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
														
	/* actor Gy*/
	extern TimerHandle_t task_timer_Gy;
	task_timer_Gy=xTimerCreate(
											"timer_Gy"
											, pdMS_TO_TICKS(4146)
											, pdTRUE
											,0
											,timer_Gy_callback
											);
														
	/* actor GrayScale*/
	extern TimerHandle_t task_timer_GrayScale;
	task_timer_GrayScale=xTimerCreate(
											"timer_GrayScale"
											, pdMS_TO_TICKS(4000)
											, pdTRUE
											,0
											,timer_GrayScale_callback
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
	timer_sem_Gx=xSemaphoreCreateBinary();
				
	/* actor Abs*/
	extern SemaphoreHandle_t timer_sem_Abs;
	timer_sem_Abs=xSemaphoreCreateBinary();
				
	/* actor Gy*/
	extern SemaphoreHandle_t timer_sem_Gy;
	timer_sem_Gy=xSemaphoreCreateBinary();
				
	/* actor GrayScale*/
	extern SemaphoreHandle_t timer_sem_GrayScale;
	timer_sem_GrayScale=xSemaphoreCreateBinary();
				
	/* actor getPx*/
	extern SemaphoreHandle_t timer_sem_getPx;
	timer_sem_getPx=xSemaphoreCreateBinary();
				
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
	extern TimerHandle_t task_timer_Gx;
	xTimerStart(task_timer_Gx, portMAX_DELAY);		
	extern TimerHandle_t task_timer_Abs;
	xTimerStart(task_timer_Abs, portMAX_DELAY);		
	extern TimerHandle_t task_timer_Gy;
	xTimerStart(task_timer_Gy, portMAX_DELAY);		
	extern TimerHandle_t task_timer_GrayScale;
	xTimerStart(task_timer_GrayScale, portMAX_DELAY);		
	extern TimerHandle_t task_timer_getPx;
	xTimerStart(task_timer_getPx, portMAX_DELAY);		
}

