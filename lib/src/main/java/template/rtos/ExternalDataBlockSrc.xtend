package template.rtos

import fileAnnotation.FileType

import fileAnnotation.FileTypeAnno
import generator.Generator
import template.templateInterface.InitTemplate
import utils.Query

@FileTypeAnno(type=FileType.C_SOURCE)
class ExternalDataBlockSrc implements InitTemplate {
	
	override create() {
		var model=Generator.model
		var externDataBlocks =Query.findAllExternalDataBlocks(model)
		'''
			#include "../inc/extern_datablock.h"
			#include "FreeRTOS.h"
			#include "semphr.h"
			/*
			=====================================================
					Blocking or Non Blokcing Read Write
			=====================================================
			*/
			«FOR data : externDataBlocks»
				SemaphoreHandle_t datablock_sem_«data.getIdentifier()»;
			«ENDFOR»
			
			/*
			=====================================================
					Counting Smeaphore
			=====================================================
			*/				
			«FOR data : externDataBlocks»
				SemaphoreHandle_t count_sem_«data.getIdentifier()»;
			«ENDFOR»
		'''
	}
	
	override getFileName() {
		return "extern_datablock"
	}
	
}
