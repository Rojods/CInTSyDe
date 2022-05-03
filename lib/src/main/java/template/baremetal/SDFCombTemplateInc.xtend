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
import forsyde.io.java.typed.viewers.moc.sdf.SDFCombViewer
import forsyde.io.java.typed.viewers.impl.Executable

@FileTypeAnno(type=FileType.C_INCLUDE)
class SDFCombTemplateInc implements ActorTemplate{
	Set<Executable> a
	override create(Vertex actor) {
		this.a=   (new SDFCombViewer(actor)).getCombFunctionsPort(Generator.model)
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