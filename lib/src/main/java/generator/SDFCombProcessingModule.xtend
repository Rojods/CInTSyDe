package generator

import forsyde.io.java.typed.viewers.moc.sdf.SDFActor


import forsyde.io.java.core.Vertex
import java.util.Set
import template.templateInterface.ActorTemplate
import java.util.HashSet
import utils.Save
import utils.Name
import fileAnnotation.FileTypeAnno
import java.lang.reflect.*
import fileAnnotation.FileType

class SDFCombProcessingModule  implements ModuleInterface{
	Set<ActorTemplate> templates
	new (){
		templates= new HashSet
	}
	
	override create() {

		Generator.model.vertexSet().stream().filter([v|SDFActor::conforms(v)]).forEach([v|process(v)])
	}
	
	def void process(Vertex v){
		templates.stream().forEach( [t| 
			
			 var anno = t.getClass(). getAnnotation(FileTypeAnno)
			 
			 if(anno.type()==FileType.C_INCLUDE){
			 	Save.save(Generator.root+"/inc/sdfcomb_"+Name.name(v)+".h",t.create(v));
			 }
			 
			 if(anno.type()==FileType.C_SOURCE){
			 	Save.save(Generator.root+"/src/sdfcomb_"+Name.name(v)+".c",t.create(v))
			 }
			 
		] )
	}

	def void add(ActorTemplate t){
		templates.add(t)
	}
	
}