#ifndef ACTOR_P5_H_
#define ACTOR_P5_H_
#include "../datatype/datatype_definition.h"
#include "../configRTOS.h"
#include "FreeRTOS.h"
#include "semphr.h"
#include "timers.h"	
#include "queue.h"
#if FREERTOS==1
void task_p5(void* pdata);
void timer_p5_callback(TimerHandle_t xTimer);
#endif

#endif
