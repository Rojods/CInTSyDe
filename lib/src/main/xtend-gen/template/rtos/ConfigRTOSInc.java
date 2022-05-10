package template.rtos;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import template.templateInterface.InitTemplate;

@FileTypeAnno(type = FileType.C_INCLUDE)
@SuppressWarnings("all")
public class ConfigRTOSInc implements InitTemplate {
  public String create() {
    throw new Error("Unresolved compilation problems:"
      + "\nno viable alternative at input \'Â\'"
      + "\nno viable alternative at input \'Â\'"
      + "\nno viable alternative at input \'»\\r\\n\\t\\t\\t#endif\\t\\t\\r\\n\\t\\t\'\'\'\'"
      + "\nThe method or field Â is undefined"
      + "\nThe method or field Â is undefined"
      + "\nThe method or field ENDFORÂ is undefined"
      + "\nThe field Generator.sdfcombSet refers to the missing type Vertex"
      + "\ngetIdentifier cannot be resolved"
      + "\ntoUpperCase cannot be resolved");
  }
  
  public String getFileName() {
    return "config";
  }
}
