package template.rtos

import fileAnnotation.FileType
import fileAnnotation.FileTypeAnno
import forsyde.io.java.core.Vertex
import template.templateInterface.ActorTemplate

@FileTypeAnno(type=FileType.C_INCLUDE)
class SDFCombInc implements ActorTemplate{
	
	override create(Vertex vertex) {
		var name=vertex.getIdentifier()
		'''
		#ifndef ACTOR_«name.toUpperCase()»_H_
		#define ACTOR_«name.toUpperCase()»_H_
		#include "../inc/datatype_definition.h"
		#include "../inc/config.h"
		#include "FreeRTOS.h"
		#include "semphr.h"
		#include "timers.h"	
		#include "queue.h"
		#if FREERTOS==1
		void task_«name»(void* pdata);
		void timer_«name»_callback(TimerHandle_t xTimer);
		#endif
		
		#endif
		'''
	}
	
}