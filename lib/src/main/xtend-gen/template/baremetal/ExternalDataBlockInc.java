package template.baremetal;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import template.templateInterface.InitTemplate;

@FileTypeAnno(type = FileType.C_INCLUDE)
@SuppressWarnings("all")
public class ExternalDataBlockInc implements InitTemplate {
  public String create() {
    throw new Error("Unresolved compilation problems:"
      + "\nno viable alternative at input \'�\'"
      + "\nno viable alternative at input \'�\\t\\t\\r\\n\\r\\n\\t\\t#endif \\r\\n\\t\\t\\r\\n\\t\\t\'\'\'\'"
      + "\nThe method or field externDataBlocks� is undefined"
      + "\nThe method or field � is undefined"
      + "\nThe method or field ENDFOR� is undefined"
      + "\ngetIdentifier cannot be resolved"
      + "\ntoUpperCase cannot be resolved");
  }
  
  public String getFileName() {
    return "extern_datablock";
  }
}
