package template.rtos

import template.templateInterface.InitTemplate
import generator.Generator
import fileAnnotation.*

@FileTypeAnno(type=FileType.C_SOURCE)
class SoftTimerTemplateSrc implements InitTemplate {
	
	override create() {
		'''
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
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
}