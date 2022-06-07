package template.rtos

import template.templateInterface.InitTemplate


import generator.Generator


class ConfigRTOSInc implements InitTemplate {

	override create() {
		
		'''	
			#ifndef CONFIG_H_
			#define CONFIG_H_

			
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
		return "/configRTOS.h"
	}

}
