package template.uniprocessor.subsystem

import template.templateInterface.SubsystemTemplate


import processingModule.Schedule


class SubsystemTemplateInc implements SubsystemTemplate{
	override String create(Schedule s){
		
		'''
			#ifndef SUBSYSTEM_«this.hashCode()»_H_
			#define SUBSYSTEM_«this.hashCode()»_H_
			/* Includes--------------------*/
			
			/* Function Prototype----------*/
			int init_subsystem();
			int subsystem();
			#endif
		'''
	}
	
	override savePath() {
		return "/tile/subsystem.h"
	}
	
}
