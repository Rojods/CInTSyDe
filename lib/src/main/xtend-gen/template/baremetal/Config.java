package template.baremetal;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import template.templateInterface.InitTemplate;

@FileTypeAnno(type = FileType.C_INCLUDE)
@SuppressWarnings("all")
public class Config implements InitTemplate {
  public String create() {
    throw new Error("Unresolved compilation problems:"
      + "\nno viable alternative at input \'Â\'"
      + "\nno viable alternative at input \'Â\'"
      + "\nno viable alternative at input \'»\\r\\n\\t\\t\\t#endif\\t\\t\\r\\n\\t\\t\'\'\'\'"
      + "\nThe method or field SDFChannel is undefined"
      + "\nThe method or field Â is undefined"
      + "\nThe method or field Â is undefined"
      + "\nThe method or field ENDFORÂ is undefined"
      + "\nThe field Generator.model refers to the missing type ForSyDeSystemGraph"
      + "\nvertexSet cannot be resolved"
      + "\nstream cannot be resolved"
      + "\nfilter cannot be resolved"
      + "\nconforms cannot be resolved"
      + "\ncollect cannot be resolved"
      + "\ngetIdentifier cannot be resolved"
      + "\ntoUpperCase cannot be resolved");
  }
  
  public String getFileName() {
    return "config";
  }
}
