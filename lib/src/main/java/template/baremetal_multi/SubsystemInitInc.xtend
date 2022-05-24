package template.baremetal_multi
import fileAnnotation.FileType

import fileAnnotation.FileTypeAnno
import template.templateInterface.InitTemplate
import generator.Generator
import utils.Query
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import java.util.HashMap
@FileTypeAnno(type=FileType.C_INCLUDE)
class SubsystemInitInc implements InitTemplate{
	
	override create() {
		'''
			#ifndef SUBSYSTEM_INIT_H_
			#define SUBSYSTEM_INIT_H_
			void init_subsystem();
			#endif
		'''
	}
	
	override getFileName() {
		return "subsystem_init"
	}
	
}
