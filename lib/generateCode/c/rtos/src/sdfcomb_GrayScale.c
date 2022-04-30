#include "../inc/config.h"
/*
==============================================
	Define Task Stack
==============================================
*/
StackType_t task_GrayScale_stk[GRAYSCALE_STACKSIZE];
StaticTask_t tcb_GrayScale;

/*
==============================================
	Declare Extern Message Queue Handler
==============================================
*/

/*
==============================================
	Define Soft Timer and Soft Timer Semaphore
==============================================
*/

SemaphoreHandle_t timer_sem_GrayScale;
TimerHandle_t timer_GrayScale;
//void timer_GrayScale_callback(TimerHandle_t xTimer);
/*
==============================================
	Define Task Function
==============================================
*/
void task_GrayScale(void* pdata){
	/* Initilize Memory           */
	while(1){
		/* Read From Channel      */
		read_nonblocking(offsetXIn_channel);
		read_nonblocking(offsetYIn_channel);
		/* Inline Code            */
		//in combFunction GrayScaleImpl
		gray[0]=0.3125*system_img_source_address[offsetY+0][offsetX+0]+0.5625*system_img_source_address[offsetY+0][offsetX+1]+0.125*system_img_source_address[offsetY+0][offsetX+2];
		gray[1]=0.3125*system_img_source_address[offsetY+0][offsetX+2]+0.5625*system_img_source_address[offsetY+0][offsetX+3]+0.125*system_img_source_address[offsetY+0][offsetX+4];
		gray[2]=0.3125*system_img_source_address[offsetY+1][offsetX+0]+0.5625*system_img_source_address[offsetY+1][offsetX+1]+0.125*system_img_source_address[offsetY+1][offsetX+2];
		gray[3]=0.3125*system_img_source_address[offsetY+1][offsetX+2]+0.5625*system_img_source_address[offsetY+1][offsetX+3]+0.125*system_img_source_address[offsetY+1][offsetX+4];
		gray[4]=0.3125*system_img_source_address[offsetY+2][offsetX+0]+0.5625*system_img_source_address[offsetY+2][offsetX+1]+0.125*system_img_source_address[offsetY+2][offsetX+2];
		gray[5]=0.3125*system_img_source_address[offsetY+2][offsetX+2]+0.5625*system_img_source_address[offsetY+2][offsetX+3]+0.125*system_img_source_address[offsetY+2][offsetX+4];
		if(offsetX>=dimX-2){offsetY+=1;
		offsetX=0;
		}if(offsetY>=dimY-2){offsetY=0;
		}dimsOut[0]=dimX;
		dimsOut[1]=dimY;
		/* Write To Channel       */
		for(int i=0;i<2;++i){
			write(dimsOut_channel);
		}
		for(int i=0;i<6;++i){
			write(gray_channel);
		}
		write(offsetYOut_channel);
		write(offsetXOut_channel);
		/* Pend Timer's Semaphore */	
		xSemaphoreTake(task_sem_GrayScale, portMAX_DELAY);	
		
	}
	
	
}

/*
=============================================
Soft Timer Callback Function
=============================================
*/
void timer_GrayScale_callback(TimerHandle_t xTimer){
	xSemaphoreGive(task_sem_GrayScale);
}
