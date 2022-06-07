package template.rtos


import forsyde.io.java.core.Vertex
import template.templateInterface.ActorTemplate


class SDFActorInc implements ActorTemplate{
	Vertex actor
	override savePath() {
		return "/sdfactor/sdfactor_"+actor.getIdentifier()+".h"
	}	
	override create(Vertex vertex) {
		this.actor=vertex
		var name=vertex.getIdentifier()
		'''
		#ifndef ACTOR_«name.toUpperCase()»_H_
		#define ACTOR_«name.toUpperCase()»_H_
		#include "../datatype/datatype_definition.h"
		#include "../configRTOS.h"
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