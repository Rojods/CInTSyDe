#include "FreeRTOS.h"
#include "semphr.h"
#include "timers.h"	
#include "queue.h"
#include "../inc/config.h"
/*
********************************************
Soft Timer and Semaphore
********************************************
*/

/*
============================================
Soft Timer for Actor Gx
============================================
*/
#if FREERTOS==1
SemaphoreHandle_t timer_sem_Gx;
TimerHandle_t task_timer_Gx;
#endif
/*
============================================
Soft Timer for Actor Abs
============================================
*/
#if FREERTOS==1
SemaphoreHandle_t timer_sem_Abs;
TimerHandle_t task_timer_Abs;
#endif
/*
============================================
Soft Timer for Actor Gy
============================================
*/
#if FREERTOS==1
SemaphoreHandle_t timer_sem_Gy;
TimerHandle_t task_timer_Gy;
#endif
/*
============================================
Soft Timer for Actor GrayScale
============================================
*/
#if FREERTOS==1
SemaphoreHandle_t timer_sem_GrayScale;
TimerHandle_t task_timer_GrayScale;
#endif
/*
============================================
Soft Timer for Actor getPx
============================================
*/
#if FREERTOS==1
SemaphoreHandle_t timer_sem_getPx;
TimerHandle_t task_timer_getPx;
#endif
