#ifndef ACTOR_P3_H_
#define ACTOR_P3_H_
#include "../datatype/datatype_definition.h"
#include "../configRTOS.h"
#include "FreeRTOS.h"
#include "semphr.h"
#include "timers.h"	
#include "queue.h"
#if FREERTOS==1
void task_p3(void* pdata);
void timer_p3_callback(TimerHandle_t xTimer);
#endif

#endif
