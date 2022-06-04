package template.baremetal_multi

import template.templateInterface.InitTemplate

import generator.Generator
import fileAnnotation.FileTypeAnno
import fileAnnotation.FileType
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import java.util.stream.Collectors
import utils.Query

@FileTypeAnno(type=FileType.C_INCLUDE)
class Config implements InitTemplate {

	override create() {
		var model=Generator.model
		var channels=model.vertexSet().stream()
							.filter([v|SDFChannel.conforms(v)])
							.collect(Collectors.toSet())
		'''	
			#ifndef CONFIG_H_
			#define CONFIG_H_
			#include <cheap_s.h>
			/*
			*************************************************************
				Config Channel Block or Non Block Read Write
			*************************************************************
			*/
			«FOR c:channels »
			«IF Query.isOnOneCoreChannel(model,c)»
				#define «c.getIdentifier().toUpperCase()»_BLOCKING 0
			«ENDIF»
			«ENDFOR»
			
			«FOR c:channels »
			«IF !Query.isOnOneCoreChannel(model,c)»
				#define «c.getIdentifier().toUpperCase()»_ADDR 0x80020000
			«ENDIF»
			«ENDFOR»
			#endif		
		'''
	}

	override getFileName() {
		return "config"
	}

}
