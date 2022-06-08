#ifndef ACTOR_P2_H_
#define ACTOR_P2_H_
#include "../datatype/datatype_definition.h"
#include "../configRTOS.h"
#include "FreeRTOS.h"
#include "semphr.h"
#include "timers.h"	
#include "queue.h"
#if FREERTOS==1
void task_p2(void* pdata);
void timer_p2_callback(TimerHandle_t xTimer);
#endif

#endif
