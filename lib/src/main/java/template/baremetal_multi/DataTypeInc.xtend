package template.baremetal_multi

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

@FileTypeAnno(type=FileType.C_INCLUDE)
class DataTypeInc implements InitTemplate {

	Set<String> record = new HashSet

	new() {
	}

	override savePath() {
		return "/datatype/datatype_definition.h"
	}

	override create() {
		var model = Generator.model
		var outset = model.vertexSet().stream().filter([ v |
			!v.hasTrait(VertexTrait.MOC_SDF_SDFCHANNEL) && v.hasTrait(VertexTrait.TYPING_TYPEDDATABLOCK)
		]).collect(Collectors.toSet())
		'''
			#ifndef DATATYPE_DEFINITION_
			#define DATATYPE_DEFINITION_
			
			#include <stdio.h>
			
			/*
			==============================================================
					TYPING_DATATYPES_DOUBLE
			==============================================================
			*/
			«doubleTypeDef(model)»
			
			/*
			==============================================================
					TYPING_DATATYPES_FLOAT
			==============================================================
			*/
			«floatTypeDef(model)»
			
			/*
			==============================================================
					TYPING_DATATYPES_INTEGER
			==============================================================
			*/
			«intTypeDef(model)»
			
			/*
			==============================================================
					TYPING_DATATYPES_ARRAY
			==============================================================
			*/
			«arrayTypeDef(model)»

			#endif
		'''
	}

	def String doubleTypeDef(ForSyDeSystemGraph model) {

		'''
			«var doubleVertexSet=model.vertexSet.stream().filter([v|Double.conforms(v)]).collect(Collectors.toSet())»
			«FOR doubleVertex : doubleVertexSet SEPARATOR "" AFTER ""»
				typedef double «doubleVertex.getIdentifier()»;
				«var tmp=this.record.add(doubleVertex.getIdentifier())»
			«ENDFOR»		
		'''
	}

	def String floatTypeDef(ForSyDeSystemGraph model) {
		'''
			«var floatVertexSet=model.vertexSet.stream().filter([v|Float.conforms(v)]).collect(Collectors.toSet())»
			«FOR floatVertex : floatVertexSet SEPARATOR "" AFTER ""»
				typedef float «floatVertex.getIdentifier()»;
				«var tmp=record.add(floatVertex.getIdentifier())»
			«ENDFOR»		
		'''
	}

	def String intTypeDef(ForSyDeSystemGraph model) {
		'''
			«var intVertexViewerSet=model.vertexSet.stream()
			.filter([v|forsyde.io.java.typed.viewers.typing.datatypes.Integer.conforms(v)])
			.map([v|new IntegerViewer(v)])
			.collect(Collectors.toSet())»
			«FOR intVertexViewer : intVertexViewerSet SEPARATOR "" AFTER ""»
				«IF (intVertexViewer.getNumberOfBits())==8»
					typedef char «intVertexViewer.getIdentifier()»;
				«ENDIF»
				«IF (intVertexViewer.getNumberOfBits())==16»
					typedef unsigned short «intVertexViewer.getIdentifier()»;
				«ENDIF»						
				«IF (intVertexViewer.getNumberOfBits())==32»
					typedef unsigned int «intVertexViewer.getIdentifier()»;
				«ENDIF»	
				«IF (intVertexViewer.getNumberOfBits())==64»
					typedef unsigned long «intVertexViewer.getIdentifier()»;
				«ENDIF»	
				«var tmp=record.add(intVertexViewer.getIdentifier())»		
			«ENDFOR»		
		'''
	}

	def String arrayTypeDef(ForSyDeSystemGraph model) {
		var arrayViewerSet = model.vertexSet.stream()
							.filter([v| Array.conforms(v) ])
							.map([v|new ArrayViewer(v)])
							.collect(Collectors.toSet())

		'''	
			«FOR array : arrayViewerSet SEPARATOR "" AFTER ""»	
				«help1(model,array)»
			«ENDFOR»			
		'''
	}

	private def String help1(ForSyDeSystemGraph model, ArrayViewer arr) {

		'''
			«var innerType=arr.getInnerTypePort(Generator.model).get().getIdentifier()»
			«IF record.contains(innerType)&&!record.contains(arr.getIdentifier())»
				«var maximumElems=getMaximumElems(arr.getViewedVertex())»
				«IF maximumElems>0»
					typedef «innerType» «arr.getIdentifier()»[«maximumElems»];
				«ENDIF»
				«IF maximumElems<0»
					typedef «innerType» *«arr.getIdentifier()»;
				«ENDIF»	
				«var tmp = this.record.add(arr.getIdentifier())»
			«ELSEIF record.contains(innerType)&& record.contains(arr.getIdentifier())»
			«ELSE»
				«help1( model,new ArrayViewer(model.queryVertex(innerType).get())) »
				«var maximumElems=getMaximumElems(arr.getViewedVertex())»
				«IF maximumElems>0»
					typedef «innerType» «arr.getIdentifier()»[«maximumElems»];
				«ENDIF»
				«IF maximumElems<0»
					typedef «innerType» *«arr.getIdentifier()»;
				«ENDIF»	
				«var tmp = this.record.add(arr.getIdentifier())»
			«ENDIF»							
		'''
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

	def String findType(ForSyDeSystemGraph model,Vertex datablock) {
	    var a =(new TypedDataBlockViewer(datablock)).getDataTypePort(model)
	    if(!a.isPresent()){
	    	return null
	    }
		return a.get().getIdentifier()
	}
}
