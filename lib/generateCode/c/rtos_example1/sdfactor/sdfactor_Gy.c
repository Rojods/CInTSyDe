	#include "../configRTOS.h"
	#include "../datatype/datatype_definition.h"
	#include "sdfactor_Gy.h"
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
			Extern Variables
	==============================================
	*/
	
	/*
	==============================================
		Define Soft Timer and Soft Timer Semaphore
	==============================================
	*/
	#if FREERTOS==1
	SemaphoreHandle_t timer_sem_Gy;
	TimerHandle_t timer_Gy;
	#endif
	/*
	==============================================
		 Task Function
	==============================================
	*/
	void task_Gy(void* pdata){
		/* Initilize Memory           */
		DoubleType gy; 
		Array6OfDoubleType imgBlockY; 
		while(1){
	/*
	==============================================
		Read From SDF Channels
	==============================================	
	*/
			for(int i=0;i<6;++i){
				#if FREERTOS==1
				xQueueReceive(msg_queue_gysig,&imgBlockY[i],portMAX_DELAY);
				#endif
			}

			
	/*
	==============================================
		Inline Code
	==============================================	
	*/
			//in combFunction GyImpl
			gy=0;
			gy=gy+imgBlockY[0];
			gy=gy+2.0*imgBlockY[1];
			gy=gy+imgBlockY[2];
			gy=gy-imgBlockY[3];
			gy=gy-2.0*imgBlockY[4];
			gy=gy-imgBlockY[5];
			
			
			
	/*
	==============================================
		Write To SDF Channels
	==============================================	
	*/
			#if FREERTOS==1
			xQueueSend(msg_queue_absysig,&gy,portMAX_DELAY);
			#endif
			
	/*

	==============================================
		Pend Timer's Semaphore
	==============================================	
	*/	
			xSemaphoreTake(timer_sem_Gy, portMAX_DELAY);	
		
		}
		
		
	}
	
	/*
	=============================================
	Soft Timer Callback Function
	=============================================
	*/
	#if FREERTOS==1
	void timer_Gy_callback(TimerHandle_t xTimer){
		xSemaphoreGive(timer_sem_Gy);
	}
	#endif
