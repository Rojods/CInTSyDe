	#include "../inc/config.h"
	#include "../inc/datatype_definition.h"
	#include "../inc/sdfcomb_GrayScale.h"
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
	StackType_t task_GrayScale_stk[GRAYSCALE_STACKSIZE];
	StaticTask_t tcb_GrayScale;
	#endif
	/*
	==============================================
		Declare Extern Message Queue Handler
	==============================================
	*/
	/* Input Message Queue */
	#if FREERTOS==1
	extern QueueHandle_t msg_queue_GrayScaleX;
	extern QueueHandle_t msg_queue_GrayScaleY;
	/* Output Message Quueue */
	extern QueueHandle_t msg_queue_GrayScaleToAbs;
	extern QueueHandle_t msg_queue_GrayScaleToGetPx;
	#endif
	/*
	==============================================
		Define Soft Timer and Soft Timer Semaphore
	==============================================
	*/
	#if FREERTOS==1
	SemaphoreHandle_t timer_sem_GrayScale;
	TimerHandle_t timer_GrayScale;
	#endif
	/*
	==============================================
		 Task Function
	==============================================
	*/
	void task_GrayScale(void* pdata){
		/* Initilize Memory           */
		UInt16 offsetX; 
		Array2OfUInt16 dimsOut; 
		UInt16 offsetY; 
		Array6OfDoubleType gray; 
		ArrayXOfArrayXOfDoubleType system_img_source_address = system_img_source_global; 
		UInt16 dimY = dimY_global; 
		UInt16 dimX = dimX_global; 
		while(1){
	/*
	==============================================
		Read From SDF Channels
	==============================================	
	*/
			#if FREERTOS==1
			xQueueReceive(msg_queue_GrayScaleX,&offsetX,portMAX_DELAY);
			#endif
			#if FREERTOS==1
			xQueueReceive(msg_queue_GrayScaleY,&offsetY,portMAX_DELAY);
			#endif

			#if defined(TESTING)
HAL_GPIO_WritePin(GPIOA,GPIO_PIN_5,1);
			#endif
			
	/*
	==============================================
		Inline Code
	==============================================	
	*/
			//in combFunction GrayScaleImpl
			gray[0]=0.3125*system_img_source_address[offsetY+0][offsetX+0]+0.5625*system_img_source_address[offsetY+0][offsetX+1]+0.125*system_img_source_address[offsetY+0][offsetX+2];
			gray[1]=0.3125*system_img_source_address[offsetY+0][offsetX+2]+0.5625*system_img_source_address[offsetY+0][offsetX+3]+0.125*system_img_source_address[offsetY+0][offsetX+4];
			gray[2]=0.3125*system_img_source_address[offsetY+1][offsetX+0]+0.5625*system_img_source_address[offsetY+1][offsetX+1]+0.125*system_img_source_address[offsetY+1][offsetX+2];
			gray[3]=0.3125*system_img_source_address[offsetY+1][offsetX+2]+0.5625*system_img_source_address[offsetY+1][offsetX+3]+0.125*system_img_source_address[offsetY+1][offsetX+4];
			gray[4]=0.3125*system_img_source_address[offsetY+2][offsetX+0]+0.5625*system_img_source_address[offsetY+2][offsetX+1]+0.125*system_img_source_address[offsetY+2][offsetX+2];
			gray[5]=0.3125*system_img_source_address[offsetY+2][offsetX+2]+0.5625*system_img_source_address[offsetY+2][offsetX+3]+0.125*system_img_source_address[offsetY+2][offsetX+4];
			if(offsetX>=dimX-2){
			offsetY+=1;
			offsetX=0;
			}
			if(offsetY>=dimY-2){
			offsetY=0;
			}
			dimsOut[0]=dimX;
			dimsOut[1]=dimY;
			
			HAL_Delay(1000);
			HAL_GPIO_WritePin(GPIOA,GPIO_PIN_5,0);
			
			
	/*
	==============================================
		Write To SDF Channels
	==============================================	
	*/
			for(int i=0;i<6;++i){
				#if FREERTOS==1
				xQueueSend(msg_queue_GrayScaleToGetPx,gray+i,portMAX_DELAY);
				#endif
			}
			#if FREERTOS==1
			xQueueSend(msg_queue_GrayScaleX,&offsetX,portMAX_DELAY);
			#endif
			#if FREERTOS==1
			xQueueSend(msg_queue_GrayScaleY,&offsetY,portMAX_DELAY);
			#endif
			for(int i=0;i<2;++i){
				#if FREERTOS==1
				xQueueSend(msg_queue_GrayScaleToAbs,dimsOut+i,portMAX_DELAY);
				#endif
			}
			
	/*
	==============================================
		Pend Timer's Semaphore
	==============================================	
	*/	
			xSemaphoreTake(timer_sem_GrayScale, portMAX_DELAY);	
		
		}
		
		
	}
	
	/*
	=============================================
	Soft Timer Callback Function
	=============================================
	*/
	#if FREERTOS==1
	void timer_GrayScale_callback(TimerHandle_t xTimer){
		xSemaphoreGive(timer_sem_GrayScale);
	}
	#endif
