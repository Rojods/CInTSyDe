package template.baremetal

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
		
		«FOR data : externDataBlocks»
		#define «data.getIdentifier().toUpperCase()»_BLOCKING 0
		«ENDFOR»		

		#endif 
		
		'''
	}
	
	override getFileName() {
		return "extern_datablock"
	}
	
}