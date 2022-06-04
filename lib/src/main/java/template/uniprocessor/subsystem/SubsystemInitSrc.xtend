package template.uniprocessor.subsystem
import fileAnnotation.FileType

import fileAnnotation.FileTypeAnno
import template.templateInterface.InitTemplate
import generator.Generator
import utils.Query
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import java.util.HashMap
import forsyde.io.java.typed.viewers.values.IntegerValue
import java.util.stream.Collectors

@FileTypeAnno(type=FileType.C_SOURCE)
@Deprecated
class SubsystemInitSrc implements InitTemplate{
	
	override create() {
		var model= Generator.model
		var integerValues = model.vertexSet().stream()
		.filter([v|IntegerValue.conforms(v)])
		.map([v|IntegerValue.safeCast(v).get()])
		.collect(Collectors.toSet())
		'''
			#include "../inc/subsystem_init.h"
			#include "../inc/datatype_definition.h"
			#include "../inc/circular_fifo_lib.h"
			
			/*
			*********************************************************
			Initialize All the Channels
			Should be called before subsystem_single_uniprocessor()
			*********************************************************
			*/
			void init_subsystem(){
			/* Extern Variables */
			«FOR value:integerValues »
				extern int «value.getIdentifier()»;
			«ENDFOR»	
			
			«externChannel()»		
			
			/* initialize the channels*/
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
			}
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
		return "subsystem_init"
	}
	
}
