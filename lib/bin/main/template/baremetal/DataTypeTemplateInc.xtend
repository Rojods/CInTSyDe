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
import forsyde.io.java.core.EdgeTrait
import java.util.Set
import java.util.HashSet
import utils.Query

@FileTypeAnno(type=FileType.C_INCLUDE)
class DataTypeTemplateInc implements InitTemplate {
	// List<VertexTrait> primitiveTraitList
	Set<String> record = new HashSet

	new() {
//		primitiveTraitList = new ArrayList<VertexTrait>
//		primitiveTraitList.add(VertexTrait.TYPING_DATATYPES_INTEGER)
//		primitiveTraitList.add(VertexTrait.TYPING_DATATYPES_FLOAT)
//		primitiveTraitList.add(VertexTrait.TYPING_DATATYPES_DOUBLE)
//		primitiveTraitList.add(VertexTrait.TYPING_DATATYPES_ARRAY)
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
			/*
			==============================================================
					TYPING_DATATYPES_DOUBLE
			==============================================================
			*/
			«doubleTypeDef()»
			
			/*
			==============================================================
					TYPING_DATATYPES_FLOAT
			==============================================================
			*/
			«floatTypeDef()»
			
			/*
			==============================================================
					TYPING_DATATYPES_INTEGER
			==============================================================
			*/
			«intTypeDef()»
			
			/*
			==============================================================
					TYPING_DATATYPES_ARRAY
			==============================================================
			*/
			«arrayTypeDef()»
			
			
			
			#endif
		'''
	}

	def String doubleTypeDef() {

		'''
			«var doubleVertexSet=Generator.model.vertexSet.stream().filter([v|v.hasTrait(VertexTrait.TYPING_DATATYPES_DOUBLE)]).collect(Collectors.toSet())»
			«FOR doubleVertex : doubleVertexSet SEPARATOR "" AFTER ""»
				typedef double «doubleVertex.getIdentifier()»;
				«var tmp=this.record.add(doubleVertex.getIdentifier())»
			«ENDFOR»		
		'''
	}

	def String floatTypeDef() {
		'''
			«var floatVertexSet=Generator.model.vertexSet.stream().filter([v|v.hasTrait(VertexTrait.TYPING_DATATYPES_FLOAT)]).collect(Collectors.toSet())»
			«FOR floatVertex : floatVertexSet SEPARATOR "" AFTER ""»
				typedef float «floatVertex.getIdentifier()»;
				«var tmp=record.add(floatVertex.getIdentifier())»
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
				«var tmp=record.add(intVertex.getIdentifier())»		
			«ENDFOR»		
		'''
	}

	def String arrayTypeDef() {
		var arrayVertexSet = Generator.model.vertexSet.stream().
			filter([v|v.hasTrait(VertexTrait.TYPING_DATATYPES_ARRAY)]).collect(Collectors.toSet())

		'''	
			«FOR arrayVertex : arrayVertexSet SEPARATOR "" AFTER ""»	
				«help1(arrayVertex)»
			«ENDFOR»			
		'''
	}

	private def String help1(Vertex arrayVertex) {
		'''
			«var innerType=getInnerType(arrayVertex)»
			«IF record.contains(innerType)&&!record.contains(arrayVertex.getIdentifier())»
				«var maximumElems=getMaximumElems(arrayVertex)»
				«IF maximumElems>0»
					typedef «innerType» «arrayVertex.getIdentifier()»[«maximumElems»];
				«ENDIF»
				«IF maximumElems<0»
					typedef «innerType» *«arrayVertex.getIdentifier()»;
				«ENDIF»	
				«var tmp = this.record.add(arrayVertex.getIdentifier())»
			«ELSEIF record.contains(innerType)&& record.contains(arrayVertex.getIdentifier()) »
			«ELSE»
				«help1(Query.findVertexByName(Generator.model,innerType))»
				«var maximumElems=getMaximumElems(arrayVertex)»
				«IF maximumElems>0»
					typedef «innerType» «arrayVertex.getIdentifier()»[«maximumElems»];
				«ENDIF»
				«IF maximumElems<0»
					typedef «innerType» *«arrayVertex.getIdentifier()»;
				«ENDIF»	
				«var tmp = this.record.add(arrayVertex.getIdentifier())»
			«ENDIF»							
		'''
	}

	private def String getInnerType(Vertex arrayType) {
		var innerType = Generator.model.outgoingEdgesOf(arrayType).stream().filter([ e |
			e.hasTrait(EdgeTrait.TYPING_DATATYPES_DATADEFINITION)
		]).filter([e|e.getSource() == arrayType.getIdentifier() && e.getSourcePort().get() == "innerType"]).findAny().
			get().getTarget()
		return innerType
	}

	private def getMaximumElems(Vertex typeVertex) {
		var maximumElems = 0
		if (typeVertex.getProperties().get("maximumElems") !== null) {
			maximumElems = (typeVertex.getProperties().get("maximumElems").unwrap() as Integer)
		} else {
			maximumElems = (typeVertex.getProperties().get("production").unwrap() as Integer)
		}
		return maximumElems
	}
}
