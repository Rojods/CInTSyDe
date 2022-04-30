package template.rtos

import template.templateInterface.InitTemplate
import generator.Generator
import fileAnnotation.*

@FileTypeAnno(type=FileType.C_SOURCE)
class SoftTimerTemplateSrc implements InitTemplate {
	
	override create() {
		'''
		#include "semphr.h"
		#include "timers.h"	
		#include "FreeRTOS.h"
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
		SemaphoreHandle_t timer_sem_«name»;
		TimerHandle_t task_timer_«name»;
		
		«ENDFOR»
		'''
	}
	
	override getFileName() {
		return "soft_timer"
	}
	
}