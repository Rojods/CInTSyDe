#include "configRTOS.h"
#include "./datatype/datatype_definition.h"
#include "FreeRTOS.h"
#include "semphr.h"
#include "timers.h"	
#include "queue.h"
#include "./sdfactor/sdfactor_p1.h"
#include "./sdfactor/sdfactor_p2.h"
#include "./sdfactor/sdfactor_p3.h"
#include "./sdfactor/sdfactor_p4.h"
#include "./sdfactor/sdfactor_p5.h"
/*
=================================================
	Define StartTask Stack
=================================================
*/
StackType_t task_StartTask_stk[STARTTASK_STACKSIZE]; 
StaticTask_t tcb_start;
/*
=================================================
	Declare Extern Values
=================================================
*/		
extern int ZeroValue1;
extern int ZeroValue2;


static void init_msg_queue();
static void init_soft_timer();
static void init_semaphore();
static void init_actor_task();
static void timer_start();
		
void init_subsystem(){
	/* Initialize Message Queue     */
	init_msg_queue();
	
	/* Initialize Software Timer    */
	init_soft_timer();
	
	/* Initialize Timer's Semaphore */
	init_semaphore();
	
	/* Initialize Actor Tasks       */
	init_actor_task();
	
	/* Start Software Timer        */
	timer_start();
	
	/* Suspend All the Created Tasks */
	
	//vTaskStartScheduler();
	//vTaskDelete(NULL); 
	
}
static void init_msg_queue(){
	/* channel s4 */
	extern QueueHandle_t msg_queue_s4;
	extern int queue_length_s4;
	extern long item_size_s4;
	
	/* channel s5 */
	extern QueueHandle_t msg_queue_s5;
	extern int queue_length_s5;
	extern long item_size_s5;
	
	/* channel s6 */
	extern QueueHandle_t msg_queue_s6;
	extern int queue_length_s6;
	extern long item_size_s6;
	
	/* channel s_in */
	extern QueueHandle_t msg_queue_s_in;
	extern int queue_length_s_in;
	extern long item_size_s_in;
	
	/* channel s1 */
	extern QueueHandle_t msg_queue_s1;
	extern int queue_length_s1;
	extern long item_size_s1;
	
	/* channel s2 */
	extern QueueHandle_t msg_queue_s2;
	extern int queue_length_s2;
	extern long item_size_s2;
	
	/* channel s3 */
	extern QueueHandle_t msg_queue_s3;
	extern int queue_length_s3;
	extern long item_size_s3;
	
	msg_queue_s4=xQueueCreate(queue_length_s4,item_size_s4);
	msg_queue_s5=xQueueCreate(queue_length_s5,item_size_s5);
	msg_queue_s6=xQueueCreate(queue_length_s6,item_size_s6);
	msg_queue_s_in=xQueueCreate(queue_length_s_in,item_size_s_in);
	msg_queue_s1=xQueueCreate(queue_length_s1,item_size_s1);
	msg_queue_s2=xQueueCreate(queue_length_s2,item_size_s2);
	msg_queue_s3=xQueueCreate(queue_length_s3,item_size_s3);
	
xQueueSend(msg_queue_s6,&ZeroValue1,portMAX_DELAY);
xQueueSend(msg_queue_s6,&ZeroValue2,portMAX_DELAY);
}
static void init_soft_timer(){
	/* actor p1*/
	extern TimerHandle_t task_timer_p1;
	task_timer_p1=xTimerCreate(
											"timer_p1"
											, pdMS_TO_TICKS(4000)
											, pdTRUE
											,0
											,timer_p1_callback
											);
														
	/* actor p2*/
	extern TimerHandle_t task_timer_p2;
	task_timer_p2=xTimerCreate(
											"timer_p2"
											, pdMS_TO_TICKS(4000)
											, pdTRUE
											,0
											,timer_p2_callback
											);
														
	/* actor p3*/
	extern TimerHandle_t task_timer_p3;
	task_timer_p3=xTimerCreate(
											"timer_p3"
											, pdMS_TO_TICKS(4000)
											, pdTRUE
											,0
											,timer_p3_callback
											);
														
	/* actor p4*/
	extern TimerHandle_t task_timer_p4;
	task_timer_p4=xTimerCreate(
											"timer_p4"
											, pdMS_TO_TICKS(4000)
											, pdTRUE
											,0
											,timer_p4_callback
											);
														
	/* actor p5*/
	extern TimerHandle_t task_timer_p5;
	task_timer_p5=xTimerCreate(
											"timer_p5"
											, pdMS_TO_TICKS(4000)
											, pdTRUE
											,0
											,timer_p5_callback
											);
														
}
static void init_semaphore(){
	/* actor p1*/
	extern SemaphoreHandle_t timer_sem_p1;
	timer_sem_p1=xSemaphoreCreateBinary();
				
	/* actor p2*/
	extern SemaphoreHandle_t timer_sem_p2;
	timer_sem_p2=xSemaphoreCreateBinary();
				
	/* actor p3*/
	extern SemaphoreHandle_t timer_sem_p3;
	timer_sem_p3=xSemaphoreCreateBinary();
				
	/* actor p4*/
	extern SemaphoreHandle_t timer_sem_p4;
	timer_sem_p4=xSemaphoreCreateBinary();
				
	/* actor p5*/
	extern SemaphoreHandle_t timer_sem_p5;
	timer_sem_p5=xSemaphoreCreateBinary();
				
}
static void init_actor_task(){
	/* actor p1*/
	extern StackType_t task_p1_stk[];
	extern StaticTask_t tcb_p1;
	xTaskCreateStatic(task_p1
						,"p1"
						,P1_STACKSIZE
						,NULL
						,configMAX_PRIORITIES-2
						,task_p1_stk,
						&tcb_p1
						);	
								
	/* actor p2*/
	extern StackType_t task_p2_stk[];
	extern StaticTask_t tcb_p2;
	xTaskCreateStatic(task_p2
						,"p2"
						,P2_STACKSIZE
						,NULL
						,configMAX_PRIORITIES-2
						,task_p2_stk,
						&tcb_p2
						);	
								
	/* actor p3*/
	extern StackType_t task_p3_stk[];
	extern StaticTask_t tcb_p3;
	xTaskCreateStatic(task_p3
						,"p3"
						,P3_STACKSIZE
						,NULL
						,configMAX_PRIORITIES-2
						,task_p3_stk,
						&tcb_p3
						);	
								
	/* actor p4*/
	extern StackType_t task_p4_stk[];
	extern StaticTask_t tcb_p4;
	xTaskCreateStatic(task_p4
						,"p4"
						,P4_STACKSIZE
						,NULL
						,configMAX_PRIORITIES-2
						,task_p4_stk,
						&tcb_p4
						);	
								
	/* actor p5*/
	extern StackType_t task_p5_stk[];
	extern StaticTask_t tcb_p5;
	xTaskCreateStatic(task_p5
						,"p5"
						,P5_STACKSIZE
						,NULL
						,configMAX_PRIORITIES-2
						,task_p5_stk,
						&tcb_p5
						);	
								
}
static void timer_start(){
	extern TimerHandle_t task_timer_p1;
	xTimerStart(task_timer_p1, portMAX_DELAY);		
	extern TimerHandle_t task_timer_p2;
	xTimerStart(task_timer_p2, portMAX_DELAY);		
	extern TimerHandle_t task_timer_p3;
	xTimerStart(task_timer_p3, portMAX_DELAY);		
	extern TimerHandle_t task_timer_p4;
	xTimerStart(task_timer_p4, portMAX_DELAY);		
	extern TimerHandle_t task_timer_p5;
	xTimerStart(task_timer_p5, portMAX_DELAY);		
}

