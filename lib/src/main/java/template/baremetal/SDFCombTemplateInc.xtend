package template.baremetal

import forsyde.io.java.core.Vertex
import forsyde.io.java.core.VertexAcessor
import forsyde.io.java.core.VertexAcessor.VertexPortDirection
import forsyde.io.java.core.VertexTrait
import generator.Generator
import java.util.Set
import template.templateInterface.ActorTemplate
import utils.Name
import fileAnnotation.FileTypeAnno
import fileAnnotation.FileType

@FileTypeAnno(type=FileType.C_INCLUDE)
class SDFCombTemplateInc implements ActorTemplate{
	Set<Vertex> implActorSet
	override create(Vertex actor) {
		implActorSet = VertexAcessor.getMultipleNamedPort(Generator.model
			,actor, "combFunctions"
			,VertexTrait.IMPL_ANSICBLACKBOXEXECUTABLE
			, VertexPortDirection.OUTGOING
			)
		'''
		«var name = actor.getIdentifier()»
		«var tmp=name.toUpperCase()+"_H_"»
		#ifndef  «tmp»
		#define «tmp»
		#include "datatype_definition.h"
		void actor_«name»();
		#endif
		'''
	}
	
}