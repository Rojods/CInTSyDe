package template.baremetal.multiprocessor

import template.templateInterface.SubsystemTemplate
import generator.Schedule
import fileAnnotation.FileType
import fileAnnotation.FileTypeAnno

@FileTypeAnno(type=FileType.C_INCLUDE)
class SubsystemTemplateIncMulti implements SubsystemTemplate{
	
	override create(Schedule s) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override getFileName() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
}