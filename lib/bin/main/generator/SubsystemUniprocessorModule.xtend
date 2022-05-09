package generator

import java.util.Set

import template.templateInterface.SubsystemTemplate
import fileAnnotation.FileTypeAnno
import fileAnnotation.FileType
import utils.Save
import java.util.HashSet

class SubsystemUniprocessorModule implements ModuleInterface {
	Set<SubsystemTemplate> templates
	new (){
		templates = new HashSet
	}	
	override create() {
		process()
	}
	def process(){
		templates.stream().forEach( [t| 
			
			 var anno = t.getClass(). getAnnotation(FileTypeAnno)
			 
			 if(anno.type()==FileType.C_INCLUDE){
			 	Save.save(Generator.root+"/inc/"+t.getFileName()+".h",t.create(null));
			 }
			 
			 if(anno.type()==FileType.C_SOURCE){
			 	Save.save(Generator.root+"/src/"+t.getFileName()+".c",t.create(null))
			 }
			 
		] )		
	}
	def add(SubsystemTemplate t){
		templates.add(t)
	}
	
}