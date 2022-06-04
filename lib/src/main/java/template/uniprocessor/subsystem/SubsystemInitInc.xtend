package template.uniprocessor.subsystem
import template.templateInterface.SubsystemTemplate

import java.util.stream.Collectors
import generator.Generator
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import fileAnnotation.FileType
import fileAnnotation.FileTypeAnno
import generator.Schedule
import utils.Query
import forsyde.io.java.typed.viewers.values.IntegerValue
import template.templateInterface.InitTemplate

@FileTypeAnno(type=FileType.C_INCLUDE)
@Deprecated
class SubsystemInitInc implements InitTemplate{
	
	override create() {
		var model= Generator.model
		var integerValues = model.vertexSet().stream()
		.filter([v|IntegerValue.conforms(v)])
		.map([v|IntegerValue.safeCast(v).get()])
		.collect(Collectors.toSet())
		'''
			#ifndef SUBSYSTEM_INIT_H_
			#define SUBSYSTEM_INIT_H_
			«««
«««			/*
«««			==============================================
«««				Extern Variables 
«««			==============================================
«««			*/		
«««			«FOR value:integerValues »
«««			extern int «value.getIdentifier()»;
«««			«ENDFOR»				
«««			
«««			«externChannel()»
«««			/*
«««			==============================================
«««				Function Prototype
«««			==============================================
«»			
			void init_subsystem();			
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
		return "subsystem_init"
	}

	
}
