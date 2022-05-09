package template.baremetal.multiprocessor;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import generator.Schedule;
import template.templateInterface.SubsystemTemplate;

@FileTypeAnno(type = FileType.C_SOURCE)
@SuppressWarnings("all")
public class SubsystemTemplateSrcMulti implements SubsystemTemplate {
  private Schedule s;
  
  public String create(final Schedule schedule) {
    throw new Error("Unresolved compilation problems:"
      + "\nno viable alternative at input \'Â\'"
      + "\nmismatched input \'».h\"\\r\\n\\t\\t\\t\\r\\n\\t\\t\\tvoid fire_subsystem_Â«\' expecting \'}\'"
      + "\nThe method or field Â is undefined"
      + "\nThis expression is not allowed in this context, since it doesn\'t cause any side effects.");
  }
  
  private /* schedule.slots */Object SEPARATOR;
}
