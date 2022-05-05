#ifndef ACTOR_ABS_H_
#define ACTOR_ABS_H_
#include "../inc/datatype_definition.h"
#include "../inc/config.h"
#include "FreeRTOS.h"
#include "semphr.h"
#include "timers.h"	
#include "queue.h"
#if FREERTOS==1
void task_Abs(void* pdata);
void timer_Abs_callback(TimerHandle_t xTimer);
#endif

#endif
