package template.rtos;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import template.templateInterface.InitTemplate;

@FileTypeAnno(type = FileType.C_SOURCE)
@SuppressWarnings("all")
public class SoftTimerTemplateSrc implements InitTemplate {
  public String create() {
    throw new Error("Unresolved compilation problems:"
      + "\nno viable alternative at input \'�\'"
      + "\nno viable alternative at input \'�\'"
      + "\nno viable alternative at input \'�\\r\\n\\t\\t\'\'\'\'"
      + "\nThe method or field � is undefined"
      + "\nThe method or field � is undefined"
      + "\nThe method or field name� is undefined"
      + "\nThe method or field name� is undefined"
      + "\nThe method or field name� is undefined"
      + "\nThe method or field ENDFOR� is undefined"
      + "\nThe field Generator.sdfcombSet refers to the missing type Vertex"
      + "\ngetIdentifier cannot be resolved");
  }
  
  public String getFileName() {
    return "soft_timer";
  }
}
