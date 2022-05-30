package template.baremetal_multi


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
class SubsystemInitSrc implements InitTemplate{
	
	override create() {
		var model= Generator.model
		var integerValues = model.vertexSet().stream()
		.filter([v|IntegerValue.conforms(v)])
		.map([v|IntegerValue.safeCast(v).get()])
		.collect(Collectors.toSet())
		'''
			#include "../inc/config.h"
			#include "../inc/subsystem_init.h"
			#include "../inc/circular_fifo_lib.h"
			#include "../inc/datatype_definition.h"
			/*
			==============================================
				Extern Variables 
			==============================================
			*/	
				«FOR value:integerValues »
					extern int «value.getIdentifier()»;
				«ENDFOR»
				
				#if MULTICORE==1
				«FOR channel: Generator.sdfchannelSet»
					«var sdfname=channel.getIdentifier()»
					«var type = Query.findSDFChannelDataType(Generator.model,channel)»
					«IF Query.isOnOneCoreChannel(model,channel)»
						/* extern sdfchannel «sdfname»*/
						extern «type» buffer_«sdfname»[];
						extern int buffer_«sdfname»_size;
						extern circular_fifo_«type» fifo_«sdfname»;
					«ELSE»
						extern cheap fifo_admin_«sdfname»;
						extern unsigned int buffer_«sdfname»_size;
						extern unsigned int token_«sdfname»_size;
						extern volatile «type» buffer_«sdfname»[];
					«ENDIF»
				«ENDFOR»			
				#endif			
			int init_subsystem(){
				«FOR channel : Generator.sdfchannelSet»
					«var sdfname=channel.getIdentifier()»
					«IF Query.isOnOneCoreChannel(model,channel)»
						init_channel_«Query.findSDFChannelDataType(Generator.model,channel)»(&fifo_«sdfname»,buffer_«sdfname»,buffer_«sdfname»_size);
					«ELSE»
						if (cheap_init_r (fifo_admin_«sdfname», (void *) buffer_«sdfname», buffer_«sdfname»_size, token_«sdfname»_size) == NULL) {
						  //xil_printf("%04u/%010u: cheap_init_r failed\n", (uint32_t)(t>>32),(uint32_t)t);
						  return 1;
						}				
					«ENDIF»
				«ENDFOR»		
				
				«FOR channel : Generator.sdfchannelSet»
					«var sdfchannel=SDFChannel.safeCast(channel).get()»
					«IF sdfchannel.getNumOfInitialTokens()!==null&&sdfchannel.getNumOfInitialTokens()>0»
						«var b = (sdfchannel.getProperties().get("__initialTokenValues_ordering__").unwrap() as HashMap<String,Integer>) »
						«FOR k:b.keySet()»
							«IF Query.isOnOneCoreChannel(model,channel)»
								write_non_blocking_«Query.findSDFChannelDataType(Generator.model,channel)»(&fifo_«sdfchannel.getIdentifier()»,«k»);
							«ELSE»
								//while(  )
							«ENDIF»
						«ENDFOR»
					«ENDIF»
				«ENDFOR»
				
				
				
				return 0;
				}
		'''
	}
	
	override getFileName() {
		return "subsystem_init"
	}
	
}
