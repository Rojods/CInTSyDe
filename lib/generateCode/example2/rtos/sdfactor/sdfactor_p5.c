	#include "../configRTOS.h"
	#include "../datatype/datatype_definition.h"
	#include "sdfactor_p5.h"
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
	StackType_t task_p5_stk[P5_STACKSIZE];
	StaticTask_t tcb_p5;
	#endif
	/*
	==============================================
		Declare Extern Message Queue Handler
	==============================================
	*/
	/* Input Message Queue */
	#if FREERTOS==1
	extern QueueHandle_t msg_queue_s4;
	/* Output Message Quueue */
	extern QueueHandle_t msg_queue_s5;
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
	SemaphoreHandle_t timer_sem_p5;
	TimerHandle_t timer_p5;
	#endif
	/*
	==============================================
		 Task Function
	==============================================
	*/
	void task_p5(void* pdata){
		/* Initilize Memory           */
		UInt32 s4; 
		UInt32 s5; 
		while(1){
	/*
	==============================================
		Read From SDF Channels
	==============================================	
	*/
			#if FREERTOS==1
			xQueueReceive(msg_queue_s4,&s4,portMAX_DELAY);
			#endif

			
	/*
	==============================================
		Inline Code
	==============================================	
	*/
			//in combFunction p5Body
			s5=s4+1;
			
			
			
	/*
	==============================================
		Write To SDF Channels
	==============================================	
	*/
			#if FREERTOS==1
			xQueueSend(msg_queue_s5,&s5,portMAX_DELAY);
			#endif
			
	/*

	==============================================
		Pend Timer's Semaphore
	==============================================	
	*/	
			xSemaphoreTake(timer_sem_p5, portMAX_DELAY);	
		
		}
		
		
	}
	
	/*
	=============================================
	Soft Timer Callback Function
	=============================================
	*/
	#if FREERTOS==1
	void timer_p5_callback(TimerHandle_t xTimer){
		xSemaphoreGive(timer_sem_p5);
	}
	#endif
