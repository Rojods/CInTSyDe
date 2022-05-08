package template.rtos

import template.templateInterface.InitTemplate
import fileAnnotation.FileTypeAnno
import fileAnnotation.FileType

@FileTypeAnno(type=FileType.C_INCLUDE)
class StartTaskInc implements InitTemplate{
	
	override create() {
		'''
		#ifndef  SUBSYSTEM_H_
		#define  SUBSYSTEM_H_
		void init_subsystem();
		#endif
		'''
	}
	
	override getFileName() {
		return "init_subsystem"
	}
	
}