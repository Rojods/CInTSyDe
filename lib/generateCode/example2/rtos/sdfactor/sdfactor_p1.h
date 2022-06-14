#ifndef ACTOR_P1_H_
#define ACTOR_P1_H_
#include "../datatype/datatype_definition.h"
#include "../configRTOS.h"
#include "FreeRTOS.h"
#include "semphr.h"
#include "timers.h"	
#include "queue.h"
#if FREERTOS==1
void task_p1(void* pdata);
void timer_p1_callback(TimerHandle_t xTimer);
#endif

#endif
