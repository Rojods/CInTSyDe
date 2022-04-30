package template.baremetal.uniprocessor

import template.templateInterface.SubsystemTemplate

import java.util.stream.Collectors
import generator.Generator
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import fileAnnotation.FileType
import fileAnnotation.FileTypeAnno
import generator.Schedule

@FileTypeAnno(type=FileType.C_SOURCE)
class SubsystemTemplateSrc implements SubsystemTemplate{
	
	override String create(Schedule s){
		'''
			#include "../inc/subsystem_include_help.h"
			/*
			==============================================
				Extern Variables are decalred in the 
				subsystem_include_help.h
			==============================================
			*/
			/*
			==============================================
				Subsystem Function
			==============================================
			*/	
			void initChannels();
			int subsystem_single_uniprocessor(){
				
				/* Initilize Channels */
				initChannels();
				
				/*    SDFdelay        */
				
				while(1){
					«FOR set :Generator.uniprocessorSchedule.entrySet() SEPARATOR "" AFTER ""»
						actor_«set.getValue().getIdentifier()»();
					«ENDFOR»	
					
				}
			}
			
			void initChannels(){
				«initChannels()»
			}
		'''
	}
//	def String externChannel(){
//
//		'''
//		«FOR channel: Generator.sdfchannelSet»
//		«var sdfname=channel.getIdentifier()»
//		/* extern sdfchannel «sdfname»*/
//		extern type buffer_«sdfname»[];
//		extern int buffer_«sdfname»_size;
//		extern circular_fifo_type fifo_«sdfname»;
//		
//		«ENDFOR»
//		'''
//	}
	def String initChannels(){
		'''
			«FOR channel: Generator.sdfchannelSet»
				«var sdfname=channel.getIdentifier()»
				init_channel_type(&fifo_«sdfname»,buffer_«sdfname»,buffer_«sdfname»_size);
			«ENDFOR»		
		'''
	}
	
	override getFileName() {
		return "subsystem"
	}
	

	
}
