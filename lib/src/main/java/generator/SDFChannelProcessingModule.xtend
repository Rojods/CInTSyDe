package generator

import java.util.Set
import template.templateInterface.ChannelTemplate
import java.util.HashSet
import forsyde.io.java.core.Vertex
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import fileAnnotation.FileType
import fileAnnotation.FileTypeAnno
import utils.Save
import utils.Name

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
			 var anno = t.getClass(). getAnnotation(FileTypeAnno)
			 
			 if(anno.type()==FileType.C_INCLUDE){
			 	Save.save(Generator.root+"/inc/sdfchannel_"+Name.name(v)+".h",t.create(v));
			 }
			 
			 if(anno.type()==FileType.C_SOURCE){
			 	Save.save(Generator.root+"/src/sdfchannel_"+Name.name(v)+".c",t.create(v))
			 }
		] )
	}
	
	def void add(ChannelTemplate t){
		templates.add(t)
	}
	

}