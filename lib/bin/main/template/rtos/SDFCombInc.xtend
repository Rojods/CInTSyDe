package template.rtos
import fileAnnotation.FileType
import fileAnnotation.FileTypeAnno
import forsyde.io.java.core.Vertex
import forsyde.io.java.core.VertexAcessor
import forsyde.io.java.core.VertexAcessor.VertexPortDirection
import forsyde.io.java.core.VertexTrait
import generator.Generator
import java.util.ArrayList
import java.util.HashMap
import java.util.HashSet
import java.util.Set
import template.templateInterface.ActorTemplate
import utils.Name
import utils.Query
import forsyde.io.java.core.EdgeInfo
import java.util.stream.Collectors
import forsyde.io.java.core.EdgeTrait
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb
import forsyde.io.java.typed.viewers.typing.TypedOperation
import forsyde.io.java.core.ForSyDeSystemGraph
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