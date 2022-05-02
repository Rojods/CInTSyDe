package template.baremetal

import template.templateInterface.InitTemplate
import generator.Generator
import forsyde.io.java.core.VertexTrait
import java.util.stream.Collectors
import fileAnnotation.FileTypeAnno
import fileAnnotation.FileType
import java.util.Set
import java.util.HashSet

@FileTypeAnno(type=FileType.C_INCLUDE)
class CircularFIFOTemplateInc implements InitTemplate {
	private Set<VertexTrait> primitiveTraitSet

	new() {
		primitiveTraitSet = new HashSet
		primitiveTraitSet.add(VertexTrait.TYPING_DATATYPES_FLOAT)
		primitiveTraitSet.add(VertexTrait.TYPING_DATATYPES_DOUBLE)
		primitiveTraitSet.add(VertexTrait.TYPING_DATATYPES_INTEGER)
	}

	override create() {
		'''
			#ifndef CIRCULAR_FIFO_LIB_H_
			#define CIRCULAR_FIFO_LIB_H_
			#include "datatype_definition.h"
			#include "spinlock.h"
			
			«FOR primitiveTrait : primitiveTraitSet SEPARATOR"" AFTER""»
			«primitiveChannelPrototype(primitiveTrait)»	
			«ENDFOR»
			#endif
		'''
	}

	override getFileName() {
		return "circular_fifo_lib"
	}

	def String primitiveChannelPrototype(VertexTrait trait) {
		'''
			«var typeSet=Generator.model.vertexSet().stream().filter([v|v.hasTrait(trait)]).collect(Collectors.toSet())»
			«FOR typeVertex : typeSet SEPARATOR "" AFTER ""»
				«var type=typeVertex.getIdentifier()»
				/*
				=============================================================
								«type» Prototype
				=============================================================
				*/
				typedef struct 
				{
				    «type»* buffer;
				    size_t front;
				    size_t rear;
				size_t size;	    
				}circular_fifo_«type»;
				
				void init_channel_«type»(circular_fifo_«type» *channel ,«type»* buffer, size_t size);
				int read_non_blocking_«type»(circular_fifo_«type»* channel,«type»* dst );
				int read_blocking_«type»(circular_fifo_«type»* ptr,«type»* dst,spinlock *lock);
				int write_non_blocking_«type»(circular_fifo_«type»* ptr,«type»* src );
				int write_blocking_«type»(circular_fifo_«type»* ptr,«type»* src,spinlock *lock);	
					
			«ENDFOR»
		'''
	}
	def void addPrimitiveVertexTrait(VertexTrait primitiveTrait){
		primitiveTraitSet.add(primitiveTrait)
	}

}
