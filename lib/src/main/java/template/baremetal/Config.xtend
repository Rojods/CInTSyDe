package template.baremetal

import template.templateInterface.InitTemplate

import generator.Generator
import fileAnnotation.FileTypeAnno
import fileAnnotation.FileType
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import java.util.stream.Collectors

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
			#define TESTING
			#if defined(TESTING)
			
			#endif
			#define MULTICORE  0
			#define SINGLECORE  1
			/*
			*************************************************************
				Config Channel Block or Non Block Read Write
			*************************************************************
			*/
			«FOR c:channels »
			#define «c.getIdentifier().toUpperCase()»_BLOCKING 0
			«ENDFOR»
			#endif		
		'''
	}

	override getFileName() {
		return "config"
	}

}
