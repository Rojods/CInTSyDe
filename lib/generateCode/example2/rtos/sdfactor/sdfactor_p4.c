	#include "../configRTOS.h"
	#include "../datatype/datatype_definition.h"
	#include "sdfactor_p4.h"
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
	StackType_t task_p4_stk[P4_STACKSIZE];
	StaticTask_t tcb_p4;
	#endif
	/*
	==============================================
		Declare Extern Message Queue Handler
	==============================================
	*/
	/* Input Message Queue */
	#if FREERTOS==1
	extern QueueHandle_t msg_queue_s2;
	/* Output Message Quueue */
	extern QueueHandle_t msg_queue_s4;
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
	SemaphoreHandle_t timer_sem_p4;
	TimerHandle_t timer_p4;
	#endif
	/*
	==============================================
		 Task Function
	==============================================
	*/
	void task_p4(void* pdata){
		/* Initilize Memory           */
		UInt32 s4; 
		UInt32 s2; 
		while(1){
	/*
	==============================================
		Read From SDF Channels
	==============================================	
	*/
			#if FREERTOS==1
			xQueueReceive(msg_queue_s2,&s2,portMAX_DELAY);
			#endif

			
	/*
	==============================================
		Inline Code
	==============================================	
	*/
			//in combFunction p4Body
			s4=s2;
			intout[3];
			out[0]=s2;
			out[1]=s2+1;
			out[2]=s2+2;
			
			
			
	/*
	==============================================
		Write To SDF Channels
	==============================================	
	*/
			#if FREERTOS==1
			xQueueSend(msg_queue_s4,&s4,portMAX_DELAY);
			#endif
			
	/*

	==============================================
		Pend Timer's Semaphore
	==============================================	
	*/	
			xSemaphoreTake(timer_sem_p4, portMAX_DELAY);	
		
		}
		
		
	}
	
	/*
	=============================================
	Soft Timer Callback Function
	=============================================
	*/
	#if FREERTOS==1
	void timer_p4_callback(TimerHandle_t xTimer){
		xSemaphoreGive(timer_sem_p4);
	}
	#endif
