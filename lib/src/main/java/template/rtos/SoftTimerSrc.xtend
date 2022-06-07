package template.rtos

import template.templateInterface.InitTemplate
import generator.Generator
import fileAnnotation.*


class SoftTimerSrc implements InitTemplate {
	
	override create() {
		'''
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
		
		«FOR actor:Generator.sdfcombSet SEPARATOR"" AFTER""»
		«var name = actor.getIdentifier()»
		/*
		============================================
		Soft Timer for Actor «name»
		============================================
		*/
		#if FREERTOS==1
		SemaphoreHandle_t timer_sem_«name»;
		TimerHandle_t task_timer_«name»;
		#endif
		«ENDFOR»
		'''
	}
	
	override savePath() {
		return "/timer/softtimer.c"
	}
	
}