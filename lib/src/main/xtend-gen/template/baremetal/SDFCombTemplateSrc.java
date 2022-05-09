package template.baremetal;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.Vertex;
import java.util.Set;
import template.templateInterface.ActorTemplate;

@FileTypeAnno(type = FileType.C_SOURCE)
@SuppressWarnings("all")
public class SDFCombTemplateSrc implements ActorTemplate {
  private Set<Vertex> implActorSet;
  
  private Set<Vertex> inputSDFChannelSet;
  
  private Set<Vertex> outputSDFChannelSet;
  
  public String create(final Vertex actor) {
    throw new Error("Unresolved compilation problems:"
      + "\nno viable alternative at input \'Â\'"
      + "\nmismatched input \'»\\r\\n\\t\\t\\t/* Includes-------------------------- */\\r\\n\\t\\t\\t#include \"../inc/config.h\"\\r\\n\\t\\t\\t#include \"../inc/datatype_definition.h\"\\r\\n\\t\\t\\t#include \"../inc/circular_fifo_lib.h\"\\r\\n\\t\\t\\t#include \"../inc/sdfcomb_Â«\' expecting \'}\'"
      + "\nThis expression is not allowed in this context, since it doesn\'t cause any side effects."
      + "\nThe field SDFCombTemplateSrc.Â refers to the missing type Â«");
  }
  
  private /* Â« */Object Â;
}
