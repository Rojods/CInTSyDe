package template.rtos;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.Vertex;
import java.util.Set;
import template.templateInterface.ActorTemplate;

@FileTypeAnno(type = FileType.C_SOURCE)
@SuppressWarnings("all")
public class SDFCombTemplateSrcRTOS implements ActorTemplate {
  private Set<Vertex> implActorSet;
  
  private Set<Vertex> inputSDFChannelSet;
  
  private Set<Vertex> outputSDFChannelSet;
  
  public String create(final Vertex actor) {
    throw new Error("Unresolved compilation problems:"
      + "\nno viable alternative at input \'�\'"
      + "\nmismatched input \'�_STACKSIZE];\\r\\n\\t\\t\\t\\tStaticTask_t tcb_«\' expecting \'}\'"
      + "\nThe method or field name� is undefined"
      + "\nThe method or field name� is undefined"
      + "\nThe method or field � is undefined"
      + "\nThis expression is not allowed in this context, since it doesn\'t cause any side effects.");
  }
  
  private /* this.inputSDFChannelSet */Object SEPARATOR;
}
