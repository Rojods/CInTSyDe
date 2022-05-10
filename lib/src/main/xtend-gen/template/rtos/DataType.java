package template.rtos;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import java.util.HashSet;
import java.util.Set;
import template.templateInterface.InitTemplate;

@FileTypeAnno(type = FileType.C_INCLUDE)
@SuppressWarnings("all")
public class DataType implements InitTemplate {
  private Set<String> record = new HashSet<String>();
  
  public DataType() {
  }
  
  public String getFileName() {
    return "datatype_definition";
  }
  
  public String create() {
    throw new Error("Unresolved compilation problems:"
      + "\nno viable alternative at input \'Â\'"
      + "\nmismatched input \'»\\r\\n\\t\\t\\t\\r\\n\\t\\t\\t/*\\r\\n\\t\\t\\t==============================================================\\r\\n\\t\\t\\t\\t\\tTYPING_DATATYPES_FLOAT\\r\\n\\t\\t\\t==============================================================\\r\\n\\t\\t\\t*/\\r\\n\\t\\t\\tÂ«\' expecting \'}\'"
      + "\nThe method or field VertexTrait is undefined"
      + "\nThe method or field VertexTrait is undefined"
      + "\nThe method doubleTypeDef(ForSyDeSystemGraph) is undefined"
      + "\nThe method or field Â is undefined"
      + "\nThe field Generator.model refers to the missing type ForSyDeSystemGraph"
      + "\nThis expression is not allowed in this context, since it doesn\'t cause any side effects."
      + "\nvertexSet cannot be resolved"
      + "\nstream cannot be resolved"
      + "\nfilter cannot be resolved"
      + "\nhasTrait cannot be resolved"
      + "\nMOC_SDF_SDFCHANNEL cannot be resolved"
      + "\n! cannot be resolved"
      + "\n&& cannot be resolved"
      + "\nhasTrait cannot be resolved"
      + "\nTYPING_TYPEDDATABLOCK cannot be resolved"
      + "\ncollect cannot be resolved");
  }
  
  private /* outset */Object SEPARATOR;
}
