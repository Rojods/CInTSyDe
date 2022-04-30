package template.baremetal.multiprocessor;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import generator.Schedule;
import template.templateInterface.SubsystemTemplate;

@FileTypeAnno(type = FileType.C_INCLUDE)
@SuppressWarnings("all")
public class SubsystemTemplateIncMulti implements SubsystemTemplate {
  public String create(final Schedule s) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
  
  public String getFileName() {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
}
