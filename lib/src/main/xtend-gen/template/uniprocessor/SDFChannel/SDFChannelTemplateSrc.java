package template.uniprocessor.SDFChannel;

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
  @Override
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
          {
            if ((Generator.fifoType == 1)) {
              _builder.append("volatile ");
              _builder.append(type);
              _builder.append(" buffer_");
              _builder.append(sdfname);
              _builder.append("[");
              _builder.append(((maximumTokens).intValue() + 1));
              _builder.append("];");
              _builder.newLineIfNotEmpty();
              _builder.append("int channel_");
              _builder.append(sdfname);
              _builder.append("_size=");
              _builder.append(maximumTokens);
              _builder.append(";");
              _builder.newLineIfNotEmpty();
              _builder.append("/*Because of circular fifo, the buffer_size=channel_size+1 */");
              _builder.newLine();
              _builder.append("int buffer_");
              _builder.append(sdfname);
              _builder.append("_size = ");
              _builder.append(((maximumTokens).intValue() + 1));
              _builder.append(";");
              _builder.newLineIfNotEmpty();
              _builder.append("circular_fifo_");
              _builder.append(type);
              _builder.append(" fifo_");
              _builder.append(sdfname);
              _builder.append(";");
              _builder.newLineIfNotEmpty();
            }
          }
          {
            if ((Generator.fifoType == 2)) {
              _builder.append("void* buffer_");
              _builder.append(sdfname);
              _builder.append("[");
              _builder.append(((maximumTokens).intValue() + 1));
              _builder.append("];");
              _builder.newLineIfNotEmpty();
              _builder.append("size_t buffer_");
              _builder.append(sdfname);
              _builder.append("_size = ");
              _builder.append(((maximumTokens).intValue() + 1));
              _builder.append(";");
              _builder.newLineIfNotEmpty();
              _builder.append("circular_fifo  fifo_");
              _builder.append(sdfname);
              _builder.append(";");
              _builder.newLineIfNotEmpty();
              _builder.append("spinlock spinlock_");
              _builder.append(sdfname);
              _builder.append("={.flag=0};");
              _builder.newLineIfNotEmpty();
            }
          }
          {
            if ((Generator.fifoType == 3)) {
              _builder.append("void* buffer_");
              _builder.append(sdfname);
              _builder.append("[");
              _builder.append(((maximumTokens).intValue() + 1));
              _builder.append("];");
              _builder.newLineIfNotEmpty();
              _builder.append("size_t buffer_");
              _builder.append(sdfname);
              _builder.append("_size = ");
              _builder.append(((maximumTokens).intValue() + 1));
              _builder.append(";");
              _builder.newLineIfNotEmpty();
              _builder.append("//size_t token_size_");
              _builder.append(sdfname);
              _builder.append(" = ");
              _builder.newLineIfNotEmpty();
              _builder.append("circular_fifo  fifo_");
              _builder.append(sdfname);
              _builder.append(";");
              _builder.newLineIfNotEmpty();
              _builder.append("spinlock spinlock_");
              _builder.append(sdfname);
              _builder.append("={.flag=0};");
              _builder.newLineIfNotEmpty();
            }
          }
        } else {
          {
            if ((Generator.fifoType == 1)) {
              _builder.append("volatile ");
              _builder.append(type);
              _builder.append(" buffer_");
              _builder.append(sdfname);
              _builder.append("[2];");
              _builder.newLineIfNotEmpty();
              _builder.append("int channel_");
              _builder.append(sdfname);
              _builder.append("_size = 1;");
              _builder.newLineIfNotEmpty();
              _builder.append("/*Because of circular fifo, the buffer_size=channel_size+1 */");
              _builder.newLine();
              _builder.append("int buffer_");
              _builder.append(sdfname);
              _builder.append("_size = 2;");
              _builder.newLineIfNotEmpty();
              _builder.append("circular_fifo_");
              _builder.append(type);
              _builder.append(" fifo_");
              _builder.append(sdfname);
              _builder.append(";");
              _builder.newLineIfNotEmpty();
            }
          }
          {
            if ((Generator.fifoType == 2)) {
              _builder.append("void* buffer_");
              _builder.append(sdfname);
              _builder.append("[2];");
              _builder.newLineIfNotEmpty();
              _builder.append("size_t buffer_");
              _builder.append(sdfname);
              _builder.append("_size = 2;");
              _builder.newLineIfNotEmpty();
              _builder.append("circular_fifo  fifo_");
              _builder.append(sdfname);
              _builder.append(";");
              _builder.newLineIfNotEmpty();
              _builder.append("spinlock spinlock_");
              _builder.append(sdfname);
              _builder.append("={.flag=0};");
              _builder.newLineIfNotEmpty();
            }
          }
        }
      }
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
}
