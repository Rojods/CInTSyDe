#include "FreeRTOS.h"
#include "semphr.h"
#include "timers.h"	
#include "queue.h"
#include "../configRTOS.h"
/*
********************************************
Soft Timer and Semaphore
********************************************
*/

/*
============================================
Soft Timer for Actor p1
============================================
*/
#if FREERTOS==1
SemaphoreHandle_t timer_sem_p1;
TimerHandle_t task_timer_p1;
#endif
/*
============================================
Soft Timer for Actor p2
============================================
*/
#if FREERTOS==1
SemaphoreHandle_t timer_sem_p2;
TimerHandle_t task_timer_p2;
#endif
/*
============================================
Soft Timer for Actor p3
============================================
*/
#if FREERTOS==1
SemaphoreHandle_t timer_sem_p3;
TimerHandle_t task_timer_p3;
#endif
/*
============================================
Soft Timer for Actor p4
============================================
*/
#if FREERTOS==1
SemaphoreHandle_t timer_sem_p4;
TimerHandle_t task_timer_p4;
#endif
/*
============================================
Soft Timer for Actor p5
============================================
*/
#if FREERTOS==1
SemaphoreHandle_t timer_sem_p5;
TimerHandle_t task_timer_p5;
#endif
