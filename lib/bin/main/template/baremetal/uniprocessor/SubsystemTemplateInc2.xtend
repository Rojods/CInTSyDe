package template.baremetal.uniprocessor
import template.templateInterface.SubsystemTemplate

import java.util.stream.Collectors
import generator.Generator
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import fileAnnotation.FileType
import fileAnnotation.FileTypeAnno
import generator.Schedule
import utils.Query
import forsyde.io.java.typed.viewers.values.IntegerValue

@FileTypeAnno(type=FileType.C_INCLUDE)
class SubsystemTemplateInc2 implements SubsystemTemplate{
	
	override create(Schedule s) {
		var model= Generator.model
		var integerValues = model.vertexSet().stream()
		.filter([v|IntegerValue.conforms(v)])
		.map([v|IntegerValue.safeCast(v).get()])
		.collect(Collectors.toSet())
		'''
			#ifndef SUBSYSTEM_INCLUDE_HELP_H_
			#define SUBSYSTEM_INCLUDE_HELP_H_
			
			/*
			****************************************************************
			The aim of this .h file is to help subsystem.c
			Only the subsystem.c includes this file.
			****************************************************************
			*/
			#include "datatype_definition.h"
			#include "circular_fifo_lib.h"
			/*
			==============================================
				Extern Variables 
			==============================================
			*/		
			«FOR value:integerValues »
			extern int «value.getIdentifier()»;
			«ENDFOR»				
			
			«externChannel()»
			
			#endif
		'''
	}
	def String externChannel(){

		'''
			«FOR channel: Generator.sdfchannelSet»
				«var sdfname=channel.getIdentifier()»
				«var type = Query.findSDFChannelDataType(Generator.model,channel)»
				/* extern sdfchannel «sdfname»*/
				extern «type» buffer_«sdfname»[];
				extern int buffer_«sdfname»_size;
				extern circular_fifo_«type» fifo_«sdfname»;
				
			«ENDFOR»
		'''
	}	
	override getFileName() {
		return "subsystem_include_help"
	}
	
}
