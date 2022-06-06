package template.rtos
import fileAnnotation.FileType

import fileAnnotation.FileTypeAnno
import generator.Generator
import template.templateInterface.InitTemplate
import utils.Query
@FileTypeAnno(type=FileType.C_SOURCE)
class FireAllSrc implements InitTemplate  {
	
	override create() {
		'''
		#include "../inc/fire_all.h"
		#include "../inc/config.h"
		#include "FreeRTOS.h"
		#include "semphr.h"
		#include "timers.h"	
		#include "queue.h"		
		void fire_all(){
			#if FREERTOS==1
			vTaskStartScheduler();
			#endif
		}
		'''
	}
	
	override savePath() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
}