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
import forsyde.io.java.core.ForSyDeSystemGraph
import utils.Query

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
			
//			 var anno = t.getClass(). getAnnotation(FileTypeAnno)
//			 		 	
//			
//			 if(anno.type()==FileType.C_INCLUDE){
//			 	Save.save(t.create(v),Generator.root+t.savePath());
//			 }
//			 
//			 if(anno.type()==FileType.C_SOURCE){
//			 	Save.save(t.create(v),Generator.root+t.savePath())
//			 }
			save(Generator.model,v,t) 
		] )
	}

	def void add(ActorTemplate t){
		templates.add(t)
	}
	def save(ForSyDeSystemGraph model, Vertex actor, ActorTemplate t){
		if(Generator.platform!=2){
			Save.save(t.create(actor),Generator.root+"/tile/"+t.savePath());
		}else{
			var Vertex tile=Query.findTile(Generator.model,actor)	
			if(tile!==null){
				Save.save(t.create(actor),Generator.root+"/"+tile.getIdentifier()+"/"+t.savePath())
			}	
		}
	}	
}