package template.rtos


import template.templateInterface.InitTemplate

import generator.Generator
import fileAnnotation.FileTypeAnno
import fileAnnotation.FileType
import fileAnnotation.FileTypeAnno
import utils.Query

@FileTypeAnno(type=FileType.C_SOURCE)
class Channel implements InitTemplate{
	
	override create() {
		var model=Generator.model
		'''
		#include "FreeRTOS.h"
		#include "semphr.h"
		#include "timers.h"	
		#include "queue.h"
		#include "../inc/datatype_definition.h"
		«FOR sdfchannel:Generator.sdfchannelSet SEPARATOR "" AFTER""»
		«var sdfname = sdfchannel.getIdentifier()»
		/*
		============================================
		SDFChannel «sdfname» Message Queue
		============================================
		*/
		/* msg queue */
		QueueHandle_t msg_queue_«sdfname»;
		/* maximum number of tokens in message queue */
		int queue_length_«sdfname» = «Query.getBufferSize(sdfchannel)»;
		/* size of token */
		long item_size_«sdfname» = sizeof(«Query.findSDFChannelDataType(model,model.queryVertex(sdfname).get())»);
		
		«ENDFOR»
		

		'''
	}
	
	override getFileName() {
		return "sdfchannel"
	}
	
}