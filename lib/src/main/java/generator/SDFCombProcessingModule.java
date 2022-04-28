package generator;

import forsyde.io.java.typed.viewers.moc.sdf.SDFComb;

import forsyde.io.java.core.Vertex;
import java.util.Set;
import template.templateInterface.ActorTemplate;
import java.util.HashSet;
import utils.Save;
import utils.Name;
import fileAnnotation.FileTypeAnno;
import fileAnnotation.FileType;

public class SDFCombProcessingModule implements ModuleInterface {
	Set<ActorTemplate> templates;
	static String inc = Generator.root + "/inc/";
	static String src = Generator.root + "/inc/";

	public SDFCombProcessingModule() {
		templates = new HashSet<>();
	}

	@Override
	public void create() {
		Generator.model.vertexSet().stream().filter(v->SDFComb.conforms(v)).forEach(v->process(v));
	}

	public void process(Vertex v){
		templates.stream().forEach( t->{
			
				 FileTypeAnno anno = t.getClass().getAnnotation(FileTypeAnno.class);
				 if(anno.type()==FileType.C_INCLUDE){
					 Save.save(Generator.root+"/inc/sdfcomb_"+Name.name(v)+".h",t.create(v));
				 }
				 
				 if(anno.type()==FileType.C_SOURCE){
				 	Save.save(Generator.root+"/src/sdfcomb_"+Name.name(v)+".c",t.create(v));
				 }			
			}
		);
	}

	public void add(ActorTemplate t) {
		templates.add(t);
	}

}
