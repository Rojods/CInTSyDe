package template.baremetal

import template.templateInterface.SubsystemTemplate

class SubsystemTemplateInc implements SubsystemTemplate{
	def String create(){
		
		'''
		#ifndef SUBSYSTEM_«tile_name»_H_
		#define SUBSYSTEM_«tile_name»_H_
		/* Includes--------------------*/
		
		/* Function Prototype----------*/
		int subsystem_«tile_name»();
		#endif
		'''
	}	
}