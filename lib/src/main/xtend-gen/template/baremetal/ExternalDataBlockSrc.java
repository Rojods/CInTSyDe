package template.baremetal;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import template.templateInterface.InitTemplate;

@FileTypeAnno(type = FileType.C_SOURCE)
@SuppressWarnings("all")
public class ExternalDataBlockSrc implements InitTemplate {
  public String create() {
    throw new Error("Unresolved compilation problems:"
      + "\nno viable alternative at input \'�\'"
      + "\nno viable alternative at input \'�\\r\\n\\t\\t\'\'\'\'"
      + "\nThe method findAllExternalDataBlocks(ForSyDeSystemGraph) is undefined for the type Class<Query>"
      + "\nThe method or field externDataBlocks� is undefined"
      + "\nThe method or field � is undefined"
      + "\nThe method or field ENDFOR� is undefined"
      + "\nThe field Generator.model refers to the missing type ForSyDeSystemGraph"
      + "\ngetIdentifier cannot be resolved");
  }
  
  public String getFileName() {
    return "extern_datablock";
  }
}
