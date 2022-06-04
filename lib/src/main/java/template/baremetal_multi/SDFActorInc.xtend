package template.baremetal_multi

import fileAnnotation.FileType
import fileAnnotation.FileTypeAnno
import forsyde.io.java.core.Vertex
import forsyde.io.java.typed.viewers.impl.Executable
import forsyde.io.java.typed.viewers.moc.sdf.SDFActorViewer
import generator.Generator
import java.util.Set
import template.templateInterface.ActorTemplate

@FileTypeAnno(type=FileType.C_INCLUDE)
class SDFActorInc implements ActorTemplate{
	Set<Executable> a
	override create(Vertex actor) {
		this.a=   (new SDFActorViewer(actor)).getCombFunctionsPort(Generator.model)
		'''
			«var name = actor.getIdentifier()»
			«var tmp=name.toUpperCase()+"_H_"»
			#ifndef  «tmp»
			#define «tmp»			
			void actor_«name»();
			#endif
		'''
		
	}
	
}
