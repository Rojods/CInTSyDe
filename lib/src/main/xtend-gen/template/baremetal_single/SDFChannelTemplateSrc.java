package template.baremetal_single;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.core.VertexProperty;
import forsyde.io.java.typed.viewers.decision.sdf.BoundedSDFChannel;
import forsyde.io.java.typed.viewers.decision.sdf.BoundedSDFChannelViewer;
import generator.Generator;
import java.util.Map;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.templateInterface.ChannelTemplate;
import utils.Query;

/**
 * without distinguish if the sdfchannel is a state variable
 */
@FileTypeAnno(type = FileType.C_SOURCE)
@SuppressWarnings("all")
public class SDFChannelTemplateSrc implements ChannelTemplate {
  public String create(final Vertex sdfchannel) {
    String _xblockexpression = null;
    {
      ForSyDeSystemGraph model = Generator.model;
      String type = Query.findSDFChannelDataType(Generator.model, sdfchannel);
      Map<String, VertexProperty> properties = sdfchannel.getProperties();
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("#include \"../inc/config.h\"");
      _builder.newLine();
      String sdfname = sdfchannel.getIdentifier();
      _builder.newLineIfNotEmpty();
      _builder.append("#include \"../inc/circular_fifo_lib.h\"");
      _builder.newLine();
      {
        Boolean _conforms = BoundedSDFChannel.conforms(sdfchannel);
        if ((_conforms).booleanValue()) {
          _builder.append("\t");
          BoundedSDFChannelViewer viewer = new BoundedSDFChannelViewer(sdfchannel);
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          Integer maximumTokens = viewer.getMaximumTokens();
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("void* buffer_");
          _builder.append(sdfname, "\t");
          _builder.append("[");
          _builder.append(((maximumTokens).intValue() + 1), "\t");
          _builder.append("];");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("size_t buffer_");
          _builder.append(sdfname, "\t");
          _builder.append("_size = ");
          _builder.append(((maximumTokens).intValue() + 1), "\t");
          _builder.append(";");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("ref_fifo  fifo_");
          _builder.append(sdfname, "\t");
          _builder.append(";");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("spinlock spinlock_");
          _builder.append(sdfname, "\t");
          _builder.append("={.flag=0};");
          _builder.newLineIfNotEmpty();
        } else {
          _builder.append("\t");
          _builder.newLine();
          _builder.append("\t");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("void* buffer_");
          _builder.append(sdfname, "\t");
          _builder.append("[2];");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("size_t buffer_");
          _builder.append(sdfname, "\t");
          _builder.append("_size = 2;");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("ref_fifo  fifo_");
          _builder.append(sdfname, "\t");
          _builder.append(";");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("spinlock spinlock_");
          _builder.append(sdfname, "\t");
          _builder.append("={.flag=0};");
          _builder.newLineIfNotEmpty();
        }
      }
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
}
