package generator

import java.util.Set
import template.templateInterface.ChannelTemplate
import java.util.HashSet
import forsyde.io.java.core.Vertex
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel

class SDFChannelProcessingModule implements ModuleInterface{
	Set<ChannelTemplate> templates
	new (){
		templates= new HashSet
	}
	
	override create() {
		Generator.model.vertexSet().stream().filter([v|SDFChannel::conforms(v)]).forEach([v|process(v)])
	}
	
	def void process(Vertex v){
		templates.stream().forEach( [t| 
			t.create(v)
		] )
	}
	
	def void add(ChannelTemplate t){
		templates.add(t)
	}
	

}