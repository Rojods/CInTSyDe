package template.baremetal.uniprocessor

import template.templateInterface.SubsystemTemplate
import fileAnnotation.FileTypeAnno
import fileAnnotation.FileType
import generator.Schedule

@FileTypeAnno(type=FileType.C_INCLUDE)
class SubsystemTemplateInc implements SubsystemTemplate{
	override String create(Schedule s){
		
		'''
			#ifndef SUBSYSTEM_«this.hashCode()»_H_
			#define SUBSYSTEM_«this.hashCode()»_H_
			/* Includes--------------------*/
			
			/* Function Prototype----------*/
			int subsystem();
			#endif
		'''
	}
	
	override getFileName() {
		return "subsystem"
	}
	
}
