	#include "../configRTOS.h"
	#include "../datatype/datatype_definition.h"
	#include "sdfactor_p1.h"
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
	StackType_t task_p1_stk[P1_STACKSIZE];
	StaticTask_t tcb_p1;
	#endif
	/*
	==============================================
		Declare Extern Message Queue Handler
	==============================================
	*/
	/* Input Message Queue */
	#if FREERTOS==1
	extern QueueHandle_t msg_queue_s6;
	extern QueueHandle_t msg_queue_s_in;
	/* Output Message Quueue */
	extern QueueHandle_t msg_queue_s1;
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
	SemaphoreHandle_t timer_sem_p1;
	TimerHandle_t timer_p1;
	#endif
	/*
	==============================================
		 Task Function
	==============================================
	*/
	void task_p1(void* pdata){
		/* Initilize Memory           */
		UInt32 s6; 
		Array2OfUInt32Type s_in; 
		UInt32 s1; 
		while(1){
	/*
	==============================================
		Read From SDF Channels
	==============================================	
	*/
			for(int i=0;i<2;++i){
				#if FREERTOS==1
				xQueueReceive(msg_queue_s_in,&s_in[i],portMAX_DELAY);
				#endif
			}
			#if FREERTOS==1
			xQueueReceive(msg_queue_s6,&s6,portMAX_DELAY);
			#endif

			
	/*
	==============================================
		Inline Code
	==============================================	
	*/
			//in combFunction p1Body
			s1=s_in[0]+s_in[1]+s6;
			
			
			
	/*
	==============================================
		Write To SDF Channels
	==============================================	
	*/
			#if FREERTOS==1
			xQueueSend(msg_queue_s1,&s1,portMAX_DELAY);
			#endif
			
	/*

	==============================================
		Pend Timer's Semaphore
	==============================================	
	*/	
			xSemaphoreTake(timer_sem_p1, portMAX_DELAY);	
		
		}
		
		
	}
	
	/*
	=============================================
	Soft Timer Callback Function
	=============================================
	*/
	#if FREERTOS==1
	void timer_p1_callback(TimerHandle_t xTimer){
		xSemaphoreGive(timer_sem_p1);
	}
	#endif
