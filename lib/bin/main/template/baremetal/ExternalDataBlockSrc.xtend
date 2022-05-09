package template.baremetal

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
		#include "../inc/spinlock.h"
		#include "../inc/extern_datablock.h"
		«FOR data : externDataBlocks»
		spinlock spinlock_«data.getIdentifier()»={.flag=0};
		«ENDFOR»
		'''
	}
	
	override getFileName() {
		return "extern_datablock"
	}
	
}