	#include "../inc/config.h"
	#include "../inc/datatype_definition.h"
	#include "../inc/sdfcomb_getPx.h"
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
	StackType_t task_getPx_stk[GETPX_STACKSIZE];
	StaticTask_t tcb_getPx;
	#endif
	/*
	==============================================
		Declare Extern Message Queue Handler
	==============================================
	*/
	/* Input Message Queue */
	#if FREERTOS==1
	extern QueueHandle_t msg_queue_GrayScaleToGetPx;
	/* Output Message Quueue */
	extern QueueHandle_t msg_queue_gysig;
	extern QueueHandle_t msg_queue_gxsig;
	#endif
	/*
	==============================================
		Define Soft Timer and Soft Timer Semaphore
	==============================================
	*/
	#if FREERTOS==1
	SemaphoreHandle_t timer_sem_getPx;
	TimerHandle_t timer_getPx;
	#endif
	/*
	==============================================
		 Task Function
	==============================================
	*/
	void task_getPx(void* pdata){
		/* Initilize Memory           */
		Array6OfDoubleType gray; 
		Array6OfDoubleType imgBlockY; 
		Array6OfDoubleType imgBlockX; 
		while(1){
	/*
	==============================================
		Read From SDF Channels
	==============================================	
	*/
			for(int i=0;i<6;++i){
				#if FREERTOS==1
				xQueueReceive(msg_queue_GrayScaleToGetPx,&gray[i],portMAX_DELAY);
				#endif
			}

			#if defined(TESTING)
HAL_GPIO_WritePin(GPIOC,GPIO_PIN_9,1);
			#endif
			
	/*
	==============================================
		Inline Code
	==============================================	
	*/
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
			
			HAL_Delay(1000);
			HAL_GPIO_WritePin(GPIOC,GPIO_PIN_9,0);					
			
			
	/*
	==============================================
		Write To SDF Channels
	==============================================	
	*/
			for(int i=0;i<6;++i){
				#if FREERTOS==1
				xQueueSend(msg_queue_gysig,imgBlockY+i,portMAX_DELAY);
				#endif
			}
			for(int i=0;i<6;++i){
				#if FREERTOS==1
				xQueueSend(msg_queue_gxsig,imgBlockX+i,portMAX_DELAY);
				#endif
			}
			
	/*
	==============================================
		Pend Timer's Semaphore
	==============================================	
	*/	
			xSemaphoreTake(timer_sem_getPx, portMAX_DELAY);	
		
		}
		
		
	}
	
	/*
	=============================================
	Soft Timer Callback Function
	=============================================
	*/
	#if FREERTOS==1
	void timer_getPx_callback(TimerHandle_t xTimer){
		xSemaphoreGive(timer_sem_getPx);
	}
	#endif
