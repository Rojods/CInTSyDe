	#include "../configRTOS.h"
	#include "../datatype/datatype_definition.h"
	#include "sdfactor_p3.h"
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
	StackType_t task_p3_stk[P3_STACKSIZE];
	StaticTask_t tcb_p3;
	#endif
	/*
	==============================================
		Declare Extern Message Queue Handler
	==============================================
	*/
	/* Input Message Queue */
	#if FREERTOS==1
	extern QueueHandle_t msg_queue_s5;
	extern QueueHandle_t msg_queue_s3;
	/* Output Message Quueue */
	extern QueueHandle_t msg_queue_s6;
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
	SemaphoreHandle_t timer_sem_p3;
	TimerHandle_t timer_p3;
	#endif
	/*
	==============================================
		 Task Function
	==============================================
	*/
	void task_p3(void* pdata){
		/* Initilize Memory           */
		Array2OfUInt32Type s3; 
		Array2OfUInt32Type s5; 
		Array2OfUInt32Type s6; 
		while(1){
	/*
	==============================================
		Read From SDF Channels
	==============================================	
	*/
			for(int i=0;i<2;++i){
				#if FREERTOS==1
				xQueueReceive(msg_queue_s3,&s3[i],portMAX_DELAY);
				#endif
			}
			for(int i=0;i<2;++i){
				#if FREERTOS==1
				xQueueReceive(msg_queue_s5,&s5[i],portMAX_DELAY);
				#endif
			}

			
	/*
	==============================================
		Inline Code
	==============================================	
	*/
			//in combFunction p3Body
			s6[0]=s3[0]+s3[1];
			s6[1]=s5[0]+s5[1];
			
			
			
	/*
	==============================================
		Write To SDF Channels
	==============================================	
	*/
			for(int i=0;i<2;++i){
				#if FREERTOS==1
				xQueueSend(msg_queue_s6,s6+i,portMAX_DELAY);
				#endif
			}
			
	/*

	==============================================
		Pend Timer's Semaphore
	==============================================	
	*/	
			xSemaphoreTake(timer_sem_p3, portMAX_DELAY);	
		
		}
		
		
	}
	
	/*
	=============================================
	Soft Timer Callback Function
	=============================================
	*/
	#if FREERTOS==1
	void timer_p3_callback(TimerHandle_t xTimer){
		xSemaphoreGive(timer_sem_p3);
	}
	#endif
