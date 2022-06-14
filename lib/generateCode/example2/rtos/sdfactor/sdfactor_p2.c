	#include "../configRTOS.h"
	#include "../datatype/datatype_definition.h"
	#include "sdfactor_p2.h"
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
	StackType_t task_p2_stk[P2_STACKSIZE];
	StaticTask_t tcb_p2;
	#endif
	/*
	==============================================
		Declare Extern Message Queue Handler
	==============================================
	*/
	/* Input Message Queue */
	#if FREERTOS==1
	extern QueueHandle_t msg_queue_s1;
	/* Output Message Quueue */
	extern QueueHandle_t msg_queue_s2;
	extern QueueHandle_t msg_queue_s3;
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
	SemaphoreHandle_t timer_sem_p2;
	TimerHandle_t timer_p2;
	#endif
	/*
	==============================================
		 Task Function
	==============================================
	*/
	void task_p2(void* pdata){
		/* Initilize Memory           */
		UInt32 s3; 
		UInt32 s1; 
		UInt32 s2; 
		while(1){
	/*
	==============================================
		Read From SDF Channels
	==============================================	
	*/
			#if FREERTOS==1
			xQueueReceive(msg_queue_s1,&s1,portMAX_DELAY);
			#endif

			
	/*
	==============================================
		Inline Code
	==============================================	
	*/
			//in combFunction p2Body
			s2=s1;
			s3=s1+1;
			
			
			
	/*
	==============================================
		Write To SDF Channels
	==============================================	
	*/
			#if FREERTOS==1
			xQueueSend(msg_queue_s2,&s2,portMAX_DELAY);
			#endif
			#if FREERTOS==1
			xQueueSend(msg_queue_s3,&s3,portMAX_DELAY);
			#endif
			
	/*

	==============================================
		Pend Timer's Semaphore
	==============================================	
	*/	
			xSemaphoreTake(timer_sem_p2, portMAX_DELAY);	
		
		}
		
		
	}
	
	/*
	=============================================
	Soft Timer Callback Function
	=============================================
	*/
	#if FREERTOS==1
	void timer_p2_callback(TimerHandle_t xTimer){
		xSemaphoreGive(timer_sem_p2);
	}
	#endif
