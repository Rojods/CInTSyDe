package template.baremetal;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import template.templateInterface.InitTemplate;

@FileTypeAnno(type = FileType.C_INCLUDE)
@SuppressWarnings("all")
public class Config implements InitTemplate {
  public String create() {
    throw new Error("Unresolved compilation problems:"
      + "\nno viable alternative at input \'�\'"
      + "\nno viable alternative at input \'�\'"
      + "\nno viable alternative at input \'�\\r\\n\\t\\t\\t#endif\\t\\t\\r\\n\\t\\t\'\'\'\'"
      + "\nThe method or field � is undefined"
      + "\nThe method or field � is undefined"
      + "\nThe method or field ENDFOR� is undefined");
  }
  
  public String getFileName() {
    return "config";
  }
}
