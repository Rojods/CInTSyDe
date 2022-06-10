#ifndef ACTOR_P4_H_
#define ACTOR_P4_H_
#include "../datatype/datatype_definition.h"
#include "../configRTOS.h"
#include "FreeRTOS.h"
#include "semphr.h"
#include "timers.h"	
#include "queue.h"
#if FREERTOS==1
void task_p4(void* pdata);
void timer_p4_callback(TimerHandle_t xTimer);
#endif

#endif
