package generator

import forsyde.io.java.typed.viewers.moc.sdf.SDFComb
import forsyde.io.java.core.Vertex
import java.util.Set
import template.templateInterface.ActorTemplate
import java.util.HashSet
import utils.Save

class SDFCombProcessingModule  implements ModuleInterface{
	Set<ActorTemplate> templates
	static String inc=Generator.root+"/inc/";
	static String src=Generator.root+"/inc/";
	new (){
		templates= new HashSet
	}
	
	override create() {
		println("1")
		Generator.model.vertexSet().stream().filter([v|SDFComb::conforms(v)]).forEach([v|process(v)])
	}
	
	def void process(Vertex v){
		templates.stream().forEach( [t| 
			 println(t.create(v)) 
			 println("=======================")
		] )
	}
	
	def void add(ActorTemplate t){
		templates.add(t)
	}
	
}