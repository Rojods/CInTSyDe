package template.baremetal;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.typed.viewers.decision.sdf.BoundedSDFChannelViewer;
import template.templateInterface.ChannelTemplate;

/**
 * without distinguish if the sdfchannel is a state variable
 */
@FileTypeAnno(type = FileType.C_SOURCE)
@SuppressWarnings("all")
public class SDFChannelTemplateSrc implements ChannelTemplate {
  public String create(final Vertex sdfchannel) {
    throw new Error("Unresolved compilation problems:"
      + "\nno viable alternative at input \'�\'"
      + "\nmismatched input \'�\\r\\n\\t\\t\\t#include \"../inc/circular_fifo_lib.h\"\\r\\n\\t\\t\\t«\' expecting \'}\'"
      + "\nThis expression is not allowed in this context, since it doesn\'t cause any side effects."
      + "\nThe field SDFChannelTemplateSrc.� refers to the missing type «");
  }
  
  private BoundedSDFChannelViewer viewer /* Skipped initializer because of errors */;
  
  private Integer maximumTokens = this.viewer.getMaximumTokens();
  
  private /* « */Object �;
}
