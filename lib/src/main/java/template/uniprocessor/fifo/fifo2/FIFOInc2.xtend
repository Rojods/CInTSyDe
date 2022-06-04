package template.uniprocessor.fifo.fifo2

import fileAnnotation.FileType
import fileAnnotation.FileTypeAnno
import template.templateInterface.InitTemplate

@FileTypeAnno(type=FileType.C_INCLUDE)
class FIFOInc2 implements InitTemplate{

	new() {		
	}

	override create() {
		'''
			#ifndef CIRCULAR_FIFO_LIB_H_
			#define CIRCULAR_FIFO_LIB_H_
			#include "config.h"			
			#include "datatype_definition.h"
			#include "spinlock.h"	
			/*
			*******************************************************
						copy by reference
			*******************************************************
			*/
						
			typedef struct{
				void**  buffer;
				size_t front;
				size_t rear;
				size_t size;
				
			}circular_fifo;		
				
			void init(circular_fifo* fifo_ptr, void** buffer, size_t size);
			void read_non_blocking(circular_fifo* fifo_ptr, void** dst);
			void write_non_blocking(circular_fifo* fifo_ptr, void* src);					
			#endif
		'''
	}

	override getFileName() {
		return "circular_fifo_lib"
	}

		
}