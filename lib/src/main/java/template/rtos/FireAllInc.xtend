package template.rtos
import fileAnnotation.FileType

import fileAnnotation.FileTypeAnno
import generator.Generator
import template.templateInterface.InitTemplate
import utils.Query
@FileTypeAnno(type=FileType.C_INCLUDE)
class FireAllInc implements InitTemplate {
	
	override create() {
		'''
		#ifndef FIRE_ALL_H_
		#define FIRE_ALL_H_
		void fire_all();
		#endif
		
		'''
	}
	
	override savePath() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
}