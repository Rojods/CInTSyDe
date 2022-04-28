package template.baremetal

import template.templateInterface.InitTemplate
import generator.Generator
import java.util.List
import forsyde.io.java.core.VertexTrait
import java.util.ArrayList
import forsyde.io.java.core.Vertex
import forsyde.io.java.core.VertexAcessor
import java.util.stream.Collectors
import fileAnnotation.FileType
import fileAnnotation.FileTypeAnno

@FileTypeAnno(type=FileType.C_INCLUDE)
class DataTypeTemplateInc implements InitTemplate {
	List<VertexTrait> primitiveDataTypeList

	new() {
		primitiveDataTypeList = new ArrayList<VertexTrait>
		init()
	}

	def init() {
		primitiveDataTypeList.add(VertexTrait.TYPING_DATATYPES_INTEGER)
		primitiveDataTypeList.add(VertexTrait.TYPING_DATATYPES_FLOAT)
		primitiveDataTypeList.add(VertexTrait.TYPING_DATATYPES_DOUBLE)
		primitiveDataTypeList.add(VertexTrait.TYPING_DATATYPES_ARRAY)
	}
	override getFileName() {
		return "datatype_definition"
	}
	override create() {
		var model = Generator.model

		'''
			#ifndef DATATYPE_DEFINITION_
			#define DATATYPE_DEFINITION_
			#include <stdio.h>
			//double
			«doubleTypeDef()»
			//float
			«floatTypeDef()»
			//int
			«intTypeDef()»
			//array
			«arrayTypeDef()»
			
			#endif
		'''
	}

	def String doubleTypeDef() {

		'''
			«var doubleVertexSet=Generator.model.vertexSet.stream().filter([v|v.hasTrait(VertexTrait.TYPING_DATATYPES_DOUBLE)]).collect(Collectors.toSet())»
			«FOR doubleVertex : doubleVertexSet SEPARATOR "" AFTER ""»
				typedef double «doubleVertex.getIdentifier()»;
			«ENDFOR»		
		'''
	}

	def String floatTypeDef() {
		'''
			«var floatVertexSet=Generator.model.vertexSet.stream().filter([v|v.hasTrait(VertexTrait.TYPING_DATATYPES_FLOAT)]).collect(Collectors.toSet())»
			«FOR floatVertex : floatVertexSet SEPARATOR "" AFTER ""»
				typedef float «floatVertex.getIdentifier()»;
			«ENDFOR»		
		'''
	}

	def String intTypeDef() {
		'''
			«var intVertexSet=Generator.model.vertexSet.stream().filter([v|v.hasTrait(VertexTrait.TYPING_DATATYPES_INTEGER)]).collect(Collectors.toSet())»
			«FOR intVertex : intVertexSet SEPARATOR "" AFTER ""»
				«IF (intVertex.getProperties().get("numberOfBits").unwrap() as Integer)==8»
					typedef char «intVertex.getIdentifier()»;
				«ENDIF»
				«IF (intVertex.getProperties().get("numberOfBits").unwrap() as Integer)==16»
					typedef unsigned short «intVertex.getIdentifier()»;
				«ENDIF»						
				«IF (intVertex.getProperties().get("numberOfBits").unwrap() as Integer)==32»
					typedef unsigned int «intVertex.getIdentifier()»;
				«ENDIF»	
				«IF (intVertex.getProperties().get("numberOfBits").unwrap() as Integer)==64»
					typedef unsigned long «intVertex.getIdentifier()»;
				«ENDIF»			
			«ENDFOR»		
		'''
	}

	def String arrayTypeDef() {
		'''
			«var arrayVertexSet=Generator.model.vertexSet.stream().filter([v|v.hasTrait(VertexTrait.TYPING_DATATYPES_ARRAY)]).collect(Collectors.toSet())»
			«FOR arrayVertex : arrayVertexSet SEPARATOR "" AFTER ""»
				«var maximumElems=(arrayVertex.getProperties().get("maximumElems").unwrap() as Integer)»
				«IF maximumElems>0»
					typedef «getInnerType(arrayVertex)» «arrayVertex.getIdentifier()»[«maximumElems»];
				«ENDIF»
				«IF maximumElems<0»
					typedef «getInnerType(arrayVertex)» *«arrayVertex.getIdentifier()»;
				«ENDIF»					
			«ENDFOR»			
		'''
	}

	private def String getInnerType(Vertex arrayType) {

		//var maximumElems = (arrayType.getProperties().get("maximumElems").unwrap() as Integer)
		var innertypeVertex = VertexAcessor.getNamedPort(Generator.model, arrayType, "innerType",
			VertexTrait.TYPING_DATATYPES_DATATYPE).orElse(null)
		if (innertypeVertex == null) {
			return "ERROR! InnerType Not Found!"
		} else {
			return innertypeVertex.getIdentifier()
		}

	}

	def add(VertexTrait trait) {
		primitiveDataTypeList.add(trait)
	}
	

	
}
