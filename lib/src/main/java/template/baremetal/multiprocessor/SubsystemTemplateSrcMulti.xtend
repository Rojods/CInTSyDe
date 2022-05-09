package template.baremetal.multiprocessor

import template.templateInterface.SubsystemTemplate
import generator.Schedule
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import utils.Name
import fileAnnotation.FileTypeAnno
import fileAnnotation.FileType
import generator.Generator
import utils.Query

@FileTypeAnno(type=FileType.C_SOURCE)
class SubsystemTemplateSrcMulti implements SubsystemTemplate {
	Schedule s

	override create(Schedule schedule) {
		this.s = schedule
		var tile = schedule.tile
		'''
			#include "../inc/subsystem_«s.tile.getIdentifier()».h"
			
			void fire_subsystem_«tile.getIdentifier()»(){
			«FOR actor : schedule.slots SEPARATOR "" AFTER ""»
			«var tmp =1»
				«IF actor!==null»
					 actor_«Name.name(actor)»();
				«ENDIF»
			«ENDFOR»
			}	
		'''
	}

	override getFileName() {
		return "subsystem_tile_" + s.tile.getIdentifier()
	}

}
