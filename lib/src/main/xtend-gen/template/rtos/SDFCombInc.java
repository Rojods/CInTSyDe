package template.rtos;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.Vertex;
import template.templateInterface.ActorTemplate;

@FileTypeAnno(type = FileType.C_INCLUDE)
@SuppressWarnings("all")
public class SDFCombInc implements ActorTemplate {
  public String create(final Vertex vertex) {
    throw new Error("Unresolved compilation problems:"
      + "\nno viable alternative at input \'�\'"
      + "\nmismatched input \'�_H_\\r\\n\\t\\t#define ACTOR_«\' expecting \'}\'"
      + "\nThe method or field � is undefined"
      + "\nThis expression is not allowed in this context, since it doesn\'t cause any side effects.");
  }
}
