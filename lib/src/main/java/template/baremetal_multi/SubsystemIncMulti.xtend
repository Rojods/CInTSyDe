package template.baremetal_multi

import template.templateInterface.SubsystemTemplate

import processingModule.Schedule
import forsyde.io.java.core.Vertex

class SubsystemIncMulti implements SubsystemTemplate{

	Vertex tile
	override create(Vertex tile) {

		this.tile=tile
		'''
			#ifndef SUBSYSTEM_«this.hashCode()»_H_
			#define SUBSYSTEM_«this.hashCode()»_H_
			/* Includes--------------------*/
			
			/* Function Prototype----------*/
			void subsystem_«tile.getIdentifier()»();
			int init_«tile.getIdentifier()»();
			#endif		
		'''
	}
	
	override savePath() {
		
		return "/"+tile.getIdentifier()+"/subsystem_"+tile.getIdentifier()+".h"
	}
}
