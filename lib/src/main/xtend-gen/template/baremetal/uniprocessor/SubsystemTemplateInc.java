package template.baremetal.uniprocessor;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import generator.Schedule;
import template.templateInterface.SubsystemTemplate;

@FileTypeAnno(type = FileType.C_INCLUDE)
@SuppressWarnings("all")
public class SubsystemTemplateInc implements SubsystemTemplate {
  public String create(final Schedule s) {
    throw new Error("Unresolved compilation problems:"
      + "\nno viable alternative at input \'Â\'"
      + "\nmismatched input \'»_H_\\r\\n\\t\\t\\t#define SUBSYSTEM_Â«\' expecting \'}\'"
      + "\nThe method or field Â is undefined"
      + "\nThis expression is not allowed in this context, since it doesn\'t cause any side effects.");
  }
}
