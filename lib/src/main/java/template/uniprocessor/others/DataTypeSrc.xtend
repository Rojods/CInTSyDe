package template.uniprocessor.others

import fileAnnotation.FileType
import fileAnnotation.FileTypeAnno
import forsyde.io.java.typed.viewers.values.IntegerValue
import generator.Generator
import java.util.stream.Collectors
import template.templateInterface.InitTemplate

@FileTypeAnno(type=FileType.C_SOURCE)
class DataTypeSrc implements InitTemplate {
	
	override create() {
		var model=Generator.model
		var integerValues = model.vertexSet().stream()
		.filter([v|IntegerValue.conforms(v)])
		.map([v|IntegerValue.safeCast(v).get()])
		.collect(Collectors.toSet())
		
		
		'''
			#include "../inc/datatype_definition.h"
			
			«FOR value:integerValues »
				int «value.getIdentifier()»=«value.getIntValue()»;
			«ENDFOR»
		'''
	}
	
	override getFileName() {
		return "data_definition"
	}
	
}
