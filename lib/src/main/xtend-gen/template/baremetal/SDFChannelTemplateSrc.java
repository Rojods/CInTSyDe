package template.baremetal;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import template.templateInterface.ChannelTemplate;

/**
 * without distinguish if the sdfchannel is a state variable
 */
@FileTypeAnno(type = FileType.C_SOURCE)
@SuppressWarnings("all")
public class SDFChannelTemplateSrc implements ChannelTemplate {
  public String create(final /* Vertex */Object sdfchannel) {
    throw new Error("Unresolved compilation problems:"
      + "\nno viable alternative at input \'�\'"
      + "\nmismatched input \'�\\r\\n\\t\\t\\t#include \"../inc/circular_fifo_lib.h\"\\r\\n\\t\\t\\t«\' expecting \'}\'"
      + "\nThe method findSDFChannelDataType(ForSyDeSystemGraph, Vertex) is undefined for the type Class<Query>"
      + "\nThe field Generator.model refers to the missing type ForSyDeSystemGraph"
      + "\nThis expression is not allowed in this context, since it doesn\'t cause any side effects."
      + "\nThe field SDFChannelTemplateSrc.� refers to the missing type «"
      + "\ngetProperties cannot be resolved"
      + "\ngetIdentifier cannot be resolved");
  }
  
  private Object viewer /* Skipped initializer because of errors */;
  
  private Object maximumTokens /* Skipped initializer because of errors */;
  
  private /* « */Object �;
}
