	#include "../inc/config.h"
	#include "../inc/datatype_definition.h"
	#include "../inc/sdfcomb_Gx.h"
	#include "FreeRTOS.h"
	#include "semphr.h"
	#include "timers.h"	
	#include "queue.h"
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
	#endif
	/*
	==============================================
		 Task Function
	==============================================
	*/
	void task_Gx(void* pdata){
		/* Initilize Memory           */
		DoubleType gx; 
		Array6OfDoubleType imgBlockX; 
		while(1){
	/*
	==============================================
		Read From SDF Channels
	==============================================	
	*/
			for(int i=0;i<6;++i){
				#if FREERTOS==1
				xQueueReceive(msg_queue_gxsig,&imgBlockX[i],portMAX_DELAY);
				#endif
			}

			#if defined(TESTING)
HAL_GPIO_WritePin(GPIOC,GPIO_PIN_8,1);
			#endif
			
	/*
	==============================================
		Inline Code
	==============================================	
	*/
			//in combFunction GxImpl
			gx=0;
			gx=gx-imgBlockX[0];
			gx=gx+imgBlockX[1];
			gx=gx-2.0*imgBlockX[2];
			gx=gx+2.0*imgBlockX[3];
			gx=gx-imgBlockX[4];
			gx=gx+imgBlockX[5];
			
			HAL_Delay(1000);
			HAL_GPIO_WritePin(GPIOC,GPIO_PIN_8,0);	
			
			
	/*
	==============================================
		Write To SDF Channels
	==============================================	
	*/
			#if FREERTOS==1
			xQueueSend(msg_queue_absxsig,&gx,portMAX_DELAY);
			#endif
			
	/*
	==============================================
		Pend Timer's Semaphore
	==============================================	
	*/	
			xSemaphoreTake(timer_sem_Gx, portMAX_DELAY);	
		
		}
		
		
	}
	
	/*
	=============================================
	Soft Timer Callback Function
	=============================================
	*/
	#if FREERTOS==1
	void timer_Gx_callback(TimerHandle_t xTimer){
		xSemaphoreGive(timer_sem_Gx);
	}
	#endif
