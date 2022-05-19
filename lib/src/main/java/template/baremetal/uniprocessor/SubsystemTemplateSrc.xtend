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
		var model = Generator.model
		var sdfcomb = model.vertexSet().stream().filter([v|SDFComb.conforms(v)]).collect(Collectors.toSet())
		'''
			#include "../inc/subsystem.h"
			#include <stdio.h>
			«FOR v : sdfcomb»
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
			int fire_subsystem_single_uniprocessor(){
					«FOR set : Generator.uniprocessorSchedule.entrySet() SEPARATOR "" AFTER ""»
					printf("%s\n","enter «set.getValue().getIdentifier()»");
						«IF Generator.TESTING==1&&Generator.PC==1»
						actor_«set.getValue().getIdentifier()»();
						«ENDIF»
					«ENDFOR»	
			}
			
«««			void init_subsystem(){
«««				«initChannels()»
«««			}
		'''
	}

	def String initChannels() {
		'''
		/*
		*********************************************************
		Initialize All the Channels
		Should be called before subsystem_single_uniprocessor()
		*********************************************************
		*/
			«FOR channel : Generator.sdfchannelSet»
				«var sdfname=channel.getIdentifier()»
				init_channel_«Query.findSDFChannelDataType(Generator.model,channel)»(&fifo_«sdfname»,buffer_«sdfname»,buffer_«sdfname»_size);
			«ENDFOR»		
			
			«FOR channel : Generator.sdfchannelSet»
				«var sdfchannel=SDFChannel.safeCast(channel).get()»
				«IF sdfchannel.getNumOfInitialTokens()!==null&&sdfchannel.getNumOfInitialTokens()>0»
					«var b = (sdfchannel.getProperties().get("__initialTokenValues_ordering__").unwrap() as HashMap<String,Integer>) »
					«FOR k:b.keySet()»
						write_non_blocking_«Query.findSDFChannelDataType(Generator.model,channel)»(&fifo_«sdfchannel.getIdentifier()»,«k»);
					«ENDFOR»
				«ENDIF»
			«ENDFOR»
		'''
	}

	override getFileName() {
		return "subsystem"
	}

}
