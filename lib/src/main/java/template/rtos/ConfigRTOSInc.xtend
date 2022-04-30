package template.rtos

import template.templateInterface.InitTemplate

import generator.Generator
import fileAnnotation.FileTypeAnno
import fileAnnotation.FileType

@FileTypeAnno(type=FileType.C_INCLUDE)
class ConfigRTOSInc implements InitTemplate {

	override create() {
		
		'''	
			#ifndef CONFIG_H_
			#define CONFIG_H_
			/*
			************************************************
			This file define the stack size for each task
			************************************************
			*/
			#define STARTTASK_STACKSIZE 2048
			«FOR actor:Generator.sdfcombSet   SEPARATOR "" AFTER""»
			#define «actor.getIdentifier().toUpperCase()»_STACKSIZE 2048
			«ENDFOR»
			#endif		
		'''
	}

	override getFileName() {
		return "config"
	}

}
