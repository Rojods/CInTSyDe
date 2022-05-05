package template.baremetal.uniprocessor

import template.templateInterface.SubsystemTemplate

import java.util.stream.Collectors
import generator.Generator
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import fileAnnotation.FileType
import fileAnnotation.FileTypeAnno
import generator.Schedule
import java.util.HashMap
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb
import utils.Query

@FileTypeAnno(type=FileType.C_SOURCE)
class SubsystemTemplateSrc implements SubsystemTemplate {

	override String create(Schedule s) {
		var model= Generator.model
		var sdfcomb = model.vertexSet().stream()
						.filter([v|SDFComb.conforms(v)])
						.collect(Collectors.toSet())
		'''
			#include "../inc/subsystem_include_help.h"
			#include "../inc/subsystem.h"
			«FOR v :sdfcomb»
			#include "../inc/sdfcomb_«v.getIdentifier()».h"
			«ENDFOR»
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
					«FOR set : Generator.uniprocessorSchedule.entrySet() SEPARATOR "" AFTER ""»
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
	def String initChannels() {
		'''
			«FOR channel : Generator.sdfchannelSet»
				«var sdfname=channel.getIdentifier()»
				init_channel_«Query.findSDFChannelDataType(Generator.model,channel)»(&fifo_«sdfname»,buffer_«sdfname»,buffer_«sdfname»_size);
			«ENDFOR»		
			
			«FOR channel : Generator.sdfchannelSet»
				«var sdfchannel=SDFChannel.safeCast(channel).get()»
				«IF sdfchannel.getNumOfInitialTokens()!==null&&sdfchannel.getNumOfInitialTokens()>0»
				
					«var b = (sdfchannel.getProperties().get("__initialTokenValues_ordering__").unwrap() as HashMap<String,Integer>) »
					«FOR k:b.keySet()»
					buffer_«sdfchannel.getIdentifier()»[«b.get(k)»]=«k»;
					«ENDFOR»
				«ENDIF»
			«ENDFOR»
		'''
	}

	override getFileName() {
		return "subsystem"
	}

}
