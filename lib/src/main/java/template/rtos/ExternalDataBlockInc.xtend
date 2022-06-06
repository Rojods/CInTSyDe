package template.rtos

import fileAnnotation.FileType
import fileAnnotation.FileTypeAnno
import generator.Generator
import template.templateInterface.InitTemplate
import utils.Query

@FileTypeAnno(type=FileType.C_INCLUDE)
class ExternalDataBlockInc implements InitTemplate {
	
	override create() {
		var model=Generator.model
		var externDataBlocks =Query.findAllExternalDataBlocks(model)		
		'''
			#ifndef EXTERNAL_DATABLOCK_H_
			#define EXTERNAL_DATABLOCK_H_
			
			/*
			=====================================================
					Blocking or Non Blokcing Read Write
			=====================================================
			*/
			«FOR data : externDataBlocks»
				#define «data.getIdentifier().toUpperCase()»_BLOCKING 0
			«ENDFOR»	
			
			/*
			=====================================================
					Counting Smeaphore
			=====================================================
			*/				
			«FOR data : externDataBlocks»
				#define count_sem_«data.getIdentifier().toUpperCase()»_max 1
				#define count_sem_«data.getIdentifier().toUpperCase()»_init 0
				
			«ENDFOR»			
			#endif 
			
		'''
	}
	
	override savePath() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
}
