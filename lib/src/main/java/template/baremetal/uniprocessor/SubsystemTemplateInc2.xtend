package template.baremetal.uniprocessor
import template.templateInterface.SubsystemTemplate

import java.util.stream.Collectors
import generator.Generator
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import fileAnnotation.FileType
import fileAnnotation.FileTypeAnno
import generator.Schedule

@FileTypeAnno(type=FileType.C_INCLUDE)
class SubsystemTemplateInc2 implements SubsystemTemplate{
	
	override create(Schedule s) {
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
			«externChannel()»
			
			#endif
		'''
	}
	def String externChannel(){

		'''
			«FOR channel: Generator.sdfchannelSet»
				«var sdfname=channel.getIdentifier()»
				/* extern sdfchannel «sdfname»*/
				extern type buffer_«sdfname»[];
				extern int buffer_«sdfname»_size;
				extern circular_fifo_type fifo_«sdfname»;
				
			«ENDFOR»
		'''
	}	
	override getFileName() {
		return "subsystem_include_help"
	}
	
}
