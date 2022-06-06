package template.baremetal_multi

import template.templateInterface.SubsystemTemplate
import generator.Schedule
import fileAnnotation.FileType
import fileAnnotation.FileTypeAnno

@FileTypeAnno(type=FileType.C_INCLUDE)
class SubsystemTemplateIncMulti implements SubsystemTemplate{
	Schedule s
	override create(Schedule s) {
		this.s=s
		var tile=s.tile
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
		
		return "/"+s.tile.getIdentifier()+"/subsystem_"+s.tile.getIdentifier()+".h"
	}
}
