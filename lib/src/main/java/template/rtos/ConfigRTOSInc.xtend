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
			#define TESTING
			#if defined(TESTING)
			#include "main.h"
			#endif
			
			/*
			************************************************
							Config
			************************************************
			*/
			#define FREERTOS 1
			#define UCOS_2  0
			#define STARTTASK_STACKSIZE 2048
			«FOR actor:Generator.sdfcombSet   SEPARATOR "" AFTER""»
			#define «actor.getIdentifier().toUpperCase()»_STACKSIZE 2048
			«ENDFOR»
			#endif		
		'''
	}

	override savePath() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

}
