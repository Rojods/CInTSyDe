package template.baremetal;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.typed.viewers.impl.Executable;
import java.util.Set;
import template.templateInterface.ActorTemplate;

@FileTypeAnno(type = FileType.C_INCLUDE)
@SuppressWarnings("all")
public class SDFCombTemplateInc implements ActorTemplate {
  private Set<Executable> a;
  
  public String create(final Vertex actor) {
    throw new Error("Unresolved compilation problems:"
      + "\nno viable alternative at input \'�\'"
      + "\nmismatched input \'�\\r\\n\\t\\t«\' expecting \'}\'"
      + "\nThe method or field � is undefined"
      + "\nThis expression is not allowed in this context, since it doesn\'t cause any side effects.");
  }
  
  private Object tmp /* Skipped initializer because of errors */;
}
