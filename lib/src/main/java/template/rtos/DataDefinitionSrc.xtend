package template.rtos
import fileAnnotation.FileType

import fileAnnotation.FileTypeAnno
import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.core.Vertex
import forsyde.io.java.core.VertexTrait
import forsyde.io.java.typed.viewers.typing.TypedDataBlockViewer
import forsyde.io.java.typed.viewers.typing.datatypes.Array
import forsyde.io.java.typed.viewers.typing.datatypes.ArrayViewer
import forsyde.io.java.typed.viewers.typing.datatypes.Double
import forsyde.io.java.typed.viewers.typing.datatypes.Float
import forsyde.io.java.typed.viewers.typing.datatypes.IntegerViewer
import generator.Generator
import java.util.HashSet
import java.util.Set
import java.util.stream.Collectors
import template.templateInterface.InitTemplate
import forsyde.io.java.typed.viewers.values.IntegerValue

@FileTypeAnno(type=FileType.C_SOURCE)
class DataDefinitionSrc implements InitTemplate {
	
	override create() {
		var model=Generator.model
		var integerValues = model.vertexSet().stream()
		.filter([v|IntegerValue.conforms(v)])
		.map([v|IntegerValue.safeCast(v).get()])
		.collect(Collectors.toSet())
		
		
		'''
		#include "../inc/datatype_definition.h"
		#include "../inc/config.h"
		«FOR value:integerValues »
		int «value.getIdentifier()»=«value.getIntValue()»;
		«ENDFOR»
		'''
	}
	
	override getFileName() {
		return "data_definition"
	}
	
}