package template.baremetal;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import java.util.HashSet;
import java.util.Set;
import template.templateInterface.InitTemplate;

@FileTypeAnno(type = FileType.C_INCLUDE)
@SuppressWarnings("all")
public class DataTypeTemplateInc implements InitTemplate {
  private Set<String> record = new HashSet<String>();
  
  public DataTypeTemplateInc() {
  }
  
  public String getFileName() {
    return "datatype_definition";
  }
  
  public String create() {
    throw new Error("Unresolved compilation problems:"
      + "\nno viable alternative at input \'Â\'"
      + "\nmismatched input \'»\\r\\n\\t\\t\\t\\r\\n\\t\\t\\t/*\\r\\n\\t\\t\\t==============================================================\\r\\n\\t\\t\\t\\t\\tTYPING_DATATYPES_FLOAT\\r\\n\\t\\t\\t==============================================================\\r\\n\\t\\t\\t*/\\r\\n\\t\\t\\tÂ«\' expecting \'}\'"
      + "\nThe method doubleTypeDef(ForSyDeSystemGraph) is undefined"
      + "\nThe method or field Â is undefined"
      + "\nThis expression is not allowed in this context, since it doesn\'t cause any side effects.");
  }
  
  private /* outset */Object SEPARATOR;
}
