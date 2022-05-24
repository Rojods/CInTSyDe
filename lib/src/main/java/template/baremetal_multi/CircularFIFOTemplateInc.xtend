package template.baremetal_multi

import fileAnnotation.FileType
import fileAnnotation.FileTypeAnno
import forsyde.io.java.core.Vertex
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import generator.Generator
import java.util.Set
import java.util.stream.Collectors
import template.templateInterface.InitTemplate
import utils.Query

@FileTypeAnno(type=FileType.C_INCLUDE)
class CircularFIFOTemplateInc implements InitTemplate {

	Set<Vertex> typeVertexSet
	new() {		
		val model = Generator.model

		typeVertexSet=model.vertexSet().stream()
			.filter([v|SDFChannel.conforms(v)])
			.map([v|Query.findSDFChannelDataType(model,v)])
			.map([s|Query.findVertexByName(model,s)])
			.collect(Collectors.toSet())
		if(typeVertexSet.contains(null)){
			typeVertexSet.remove(null)
		}	

	}

	override create() {
		'''
			#ifndef CIRCULAR_FIFO_LIB_H_
			#define CIRCULAR_FIFO_LIB_H_
			#include "config.h"
		
			/*
			************************************************************
			This header file defines all the prototype of token types in
			SDFChannels
			************************************************************
			*/
			
			
			#include "datatype_definition.h"
			
			#include "spinlock.h"	
			
			«IF typeVertexSet.size()!=0»		
				«FOR v : typeVertexSet SEPARATOR "" AFTER ""»
					«foo(v)»
				«ENDFOR»
			«ENDIF»

			
			#endif
		'''
	}

	override getFileName() {
		return "circular_fifo_lib"
	}
	def String foo(Vertex v){
		'''
		«val type=v.getIdentifier()»
		/*
		=============================================================
				If Token type is «type» 
		==============================================================
		*/
		typedef struct 
		{
		    «type»* buffer;
		    size_t front;
		    size_t rear;
			size_t size;	    
		}circular_fifo_«type»;
		
		void init_channel_«type»(circular_fifo_«type» *channel ,«type»* buffer, size_t size);
		int read_non_blocking_«type»(circular_fifo_«type»* src,«type»* dst );
		int read_blocking_«type»(circular_fifo_«type»* src,«type»* dst,spinlock *lock);
		int write_non_blocking_«type»(circular_fifo_«type»* dst,«type» src );
		int write_blocking_«type»(circular_fifo_«type»* dst,«type» src,spinlock *lock);	
					
		«««		«ELSE»
«««			«var maximumElems =getMaximumElems(v)»
«««				«IF maximumElems>0»
««««««			«var innerType = Query.getInnerType(Generator.model,v)»
«««			/*
«««			=============================================================
«««							If Token type is «type» 
«««			=============================================================
«««			*/
«««			typedef struct 
«««			{
«««			    «type»* buffer;
«««			    size_t front;
«««			    size_t rear;
«««				size_t size;	    
«««			}circular_fifo_«type»;
«««			
«««			void init_channel_«type»(circular_fifo_«type» *channel ,«type»* buffer, size_t size);
«««			int read_non_blocking_«type»(circular_fifo_«type»* channel,«type»* dst );
«««			int read_blocking_«type»(circular_fifo_«type»* ptr,«type»* dst,spinlock *lock);
«««			int write_non_blocking_«type»(circular_fifo_«type»* ptr,«type»* src );
«««			int write_blocking_«type»(circular_fifo_«type»* ptr,«type»* src,spinlock *lock);				
«««				«ENDIF»				
«««		«ENDIF»
		'''
	}
	
	private def getMaximumElems(Vertex typeVertex) {
		var maximumElems = 0
		if (typeVertex.getProperties().get("maximumElems") !== null) {
			maximumElems = (typeVertex.getProperties().get("maximumElems").unwrap() as Integer)
		} else {
			maximumElems = (typeVertex.getProperties().get("production").unwrap() as Integer)
		}
		return maximumElems
	}
	
//	def String primitiveChannelPrototype(VertexTrait trait) {
//		'''
//			«IF trait!==VertexTrait.TYPING_DATATYPES_ARRAY»
//				«var typeSet=Generator.model.vertexSet().stream().filter([v|v.hasTrait(trait)]).collect(Collectors.toSet())»
//				«FOR typeVertex : typeSet SEPARATOR "" AFTER ""»
//					«var type=typeVertex.getIdentifier()»
//					/*
//					=============================================================
//									«type» Prototype
//					=============================================================
//					*/
//					typedef struct 
//					{
//					    «type»* buffer;
//					    size_t front;
//					    size_t rear;
//					size_t size;	    
//					}circular_fifo_«type»;
//					
//					void init_channel_«type»(circular_fifo_«type» *channel ,«type»* buffer, size_t size);
//					int read_non_blocking_«type»(circular_fifo_«type»* channel,«type»* dst );
//					int read_blocking_«type»(circular_fifo_«type»* ptr,«type»* dst,spinlock *lock);
//					int write_non_blocking_«type»(circular_fifo_«type»* ptr,«type»* src );
//					int write_blocking_«type»(circular_fifo_«type»* ptr,«type»* src,spinlock *lock);	
//						
//				«ENDFOR»
//			«ELSE»
//				«var typeSet=Generator.model.vertexSet().stream().filter([v|v.hasTrait(trait)]).collect(Collectors.toSet())»
//				«FOR typeVertex : typeSet SEPARATOR "" AFTER ""»
//					«val type=typeVertex.getIdentifier()»
//					«var maximumElems =help(typeVertex)»
//						«IF maximumElems>0»
//							«var innerType = Generator.model.outgoingEdgesOf(typeVertex).stream().filter([e|e.hasTrait(EdgeTrait.TYPING_DATATYPES_DATADEFINITION)]).filter([e|e.getSource()==type && e.getSourcePort().get()=="innerType"]).findAny().get().getTarget()»
//							/*
//							=============================================================
//											«type» Prototype
//							=============================================================
//							*/
//							typedef struct 
//							{
//							    «type»* buffer;
//							    size_t front;
//							    size_t rear;
//								size_t size;	    
//							}circular_fifo_«type»;
//							
//							void init_channel_«type»(circular_fifo_«type» *channel ,«type»* buffer, size_t size);
//							int read_non_blocking_«type»(circular_fifo_«type»* channel,«type»* dst );
//							int read_blocking_«type»(circular_fifo_«type»* ptr,«type»* dst,spinlock *lock);
//							int write_non_blocking_«type»(circular_fifo_«type»* ptr,«type»* src );
//							int write_blocking_«type»(circular_fifo_«type»* ptr,«type»* src,spinlock *lock);				
//						«ENDIF»			
//				«ENDFOR»
//			«ENDIF»
//		'''
//	}
//
//	def void addPrimitiveVertexTrait(VertexTrait primitiveTrait) {
//		primitiveTraitSet.add(primitiveTrait)
//	}



}
