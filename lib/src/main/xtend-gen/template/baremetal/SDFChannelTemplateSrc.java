package template.baremetal;

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
      _builder.append("\t\t\t");
      _builder.append("#include \"../inc/config.h\"");
      _builder.newLine();
      _builder.append("\t\t\t");
      String sdfname = sdfchannel.getIdentifier();
      _builder.newLineIfNotEmpty();
      _builder.append("\t\t\t");
      _builder.append("#if SINGLECORE==1");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("#include \"../inc/circular_fifo_lib.h\"");
      _builder.newLine();
      {
        Boolean _conforms = BoundedSDFChannel.conforms(sdfchannel);
        if ((_conforms).booleanValue()) {
          _builder.append("\t\t\t\t");
          BoundedSDFChannelViewer viewer = new BoundedSDFChannelViewer(sdfchannel);
          _builder.newLineIfNotEmpty();
          _builder.append("\t\t\t\t");
          Integer maximumTokens = viewer.getMaximumTokens();
          _builder.newLineIfNotEmpty();
          _builder.append("\t\t\t\t");
          _builder.append("volatile ");
          _builder.append(type, "\t\t\t\t");
          _builder.append(" buffer_");
          _builder.append(sdfname, "\t\t\t\t");
          _builder.append("[");
          _builder.append(((maximumTokens).intValue() + 1), "\t\t\t\t");
          _builder.append("];");
          _builder.newLineIfNotEmpty();
          _builder.append("\t\t\t\t");
          _builder.append("int channel_");
          _builder.append(sdfname, "\t\t\t\t");
          _builder.append("_size=");
          _builder.append(maximumTokens, "\t\t\t\t");
          _builder.append(";");
          _builder.newLineIfNotEmpty();
          _builder.append("\t\t\t\t");
          _builder.append("/*Because of circular fifo, the buffer_size=channel_size+1 */");
          _builder.newLine();
          _builder.append("\t\t\t\t");
          _builder.append("int buffer_");
          _builder.append(sdfname, "\t\t\t\t");
          _builder.append("_size = ");
          _builder.append(((maximumTokens).intValue() + 1), "\t\t\t\t");
          _builder.append(";");
          _builder.newLineIfNotEmpty();
          _builder.append("\t\t\t\t");
          _builder.append("circular_fifo_");
          _builder.append(type, "\t\t\t\t");
          _builder.append(" fifo_");
          _builder.append(sdfname, "\t\t\t\t");
          _builder.append(";");
          _builder.newLineIfNotEmpty();
          _builder.append("\t\t\t\t");
          _builder.append("spinlock spinlock_");
          _builder.append(sdfname, "\t\t\t\t");
          _builder.append("={.flag=0};");
          _builder.newLineIfNotEmpty();
        } else {
          _builder.append("\t\t\t\t");
          _builder.append("volatile ");
          _builder.append(type, "\t\t\t\t");
          _builder.append(" buffer_");
          _builder.append(sdfname, "\t\t\t\t");
          _builder.append("[2];");
          _builder.newLineIfNotEmpty();
          _builder.append("\t\t\t\t");
          _builder.append("int channel_");
          _builder.append(sdfname, "\t\t\t\t");
          _builder.append("_size = 1;");
          _builder.newLineIfNotEmpty();
          _builder.append("\t\t\t\t");
          _builder.append("/*");
          _builder.newLine();
          _builder.append("\t\t\t\t");
          _builder.append("\t");
          _builder.append("Because of circular fifo, the ");
          _builder.newLine();
          _builder.append("\t\t\t\t");
          _builder.append("\t");
          _builder.append("buffer_size=channel_size+1");
          _builder.newLine();
          _builder.append("\t\t\t\t");
          _builder.append("*/");
          _builder.newLine();
          _builder.append("\t\t\t\t");
          _builder.append("int buffer_");
          _builder.append(sdfname, "\t\t\t\t");
          _builder.append("_size = 2;");
          _builder.newLineIfNotEmpty();
          _builder.append("\t\t\t\t");
          _builder.append("circular_fifo_");
          _builder.append(type, "\t\t\t\t");
          _builder.append(" fifo_");
          _builder.append(sdfname, "\t\t\t\t");
          _builder.append(";");
          _builder.newLineIfNotEmpty();
          _builder.append("\t\t\t\t");
          _builder.append("spinlock spinlock_");
          _builder.append(sdfname, "\t\t\t\t");
          _builder.append("={.flag=0};\t");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.append("\t\t\t");
      _builder.append("#endif");
      _builder.newLine();
      _builder.append("////////////////////////////////////////////////////////////////////\t\t\t");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("#if MULTICORE==1");
      _builder.newLine();
      {
        Boolean _conforms_1 = BoundedSDFChannel.conforms(sdfchannel);
        if ((_conforms_1).booleanValue()) {
          _builder.append("\t\t\t\t");
          BoundedSDFChannelViewer viewer_1 = new BoundedSDFChannelViewer(sdfchannel);
          _builder.newLineIfNotEmpty();
          _builder.append("\t\t\t\t");
          Integer maximumTokens_1 = viewer_1.getMaximumTokens();
          _builder.newLineIfNotEmpty();
          {
            boolean _isOnOneCoreChannel = Query.isOnOneCoreChannel(model, sdfchannel);
            if (_isOnOneCoreChannel) {
              _builder.append("\t\t\t\t");
              _builder.append("volatile ");
              _builder.append(type, "\t\t\t\t");
              _builder.append(" buffer_");
              _builder.append(sdfname, "\t\t\t\t");
              _builder.append("[");
              _builder.append(((maximumTokens_1).intValue() + 1), "\t\t\t\t");
              _builder.append("];");
              _builder.newLineIfNotEmpty();
              _builder.append("\t\t\t\t");
              _builder.append("int channel_");
              _builder.append(sdfname, "\t\t\t\t");
              _builder.append("_size=");
              _builder.append(maximumTokens_1, "\t\t\t\t");
              _builder.append(";");
              _builder.newLineIfNotEmpty();
              _builder.append("\t\t\t\t");
              _builder.append("/*Because of circular fifo, the buffer_size=channel_size+1 */");
              _builder.newLine();
              _builder.append("\t\t\t\t");
              _builder.append("int buffer_");
              _builder.append(sdfname, "\t\t\t\t");
              _builder.append("_size = ");
              _builder.append(((maximumTokens_1).intValue() + 1), "\t\t\t\t");
              _builder.append(";");
              _builder.newLineIfNotEmpty();
              _builder.append("\t\t\t\t");
              _builder.append("circular_fifo_");
              _builder.append(type, "\t\t\t\t");
              _builder.append(" fifo_");
              _builder.append(sdfname, "\t\t\t\t");
              _builder.append(";");
              _builder.newLineIfNotEmpty();
              _builder.append("\t\t\t\t");
              _builder.append("spinlock spinlock_");
              _builder.append(sdfname, "\t\t\t\t");
              _builder.append("={.flag=0};");
              _builder.newLineIfNotEmpty();
            } else {
              _builder.append("volatile cheap const fifo_admin_");
              _builder.append(sdfname);
              _builder.append(";");
              _builder.newLineIfNotEmpty();
              _builder.append("unsigned int buffer_");
              _builder.append(sdfname);
              _builder.append("_size=");
              int _bufferSize = Query.getBufferSize(sdfchannel);
              _builder.append(_bufferSize);
              _builder.append(";");
              _builder.newLineIfNotEmpty();
              _builder.append("unsigned int token_");
              _builder.append(sdfname);
              _builder.append("_size=");
              long _tokenSize = Query.getTokenSize(sdfchannel);
              _builder.append(_tokenSize);
              _builder.append("\t;");
              _builder.newLineIfNotEmpty();
              _builder.append("volatile ");
              _builder.append(type);
              _builder.append(" buffer_");
              _builder.append(sdfname);
              _builder.append("[");
              _builder.append(maximumTokens_1);
              _builder.append("];\t\t\t");
              _builder.newLineIfNotEmpty();
            }
          }
        } else {
          {
            boolean _isOnOneCoreChannel_1 = Query.isOnOneCoreChannel(model, sdfchannel);
            if (_isOnOneCoreChannel_1) {
              _builder.append("\t\t\t\t");
              _builder.append("volatile ");
              _builder.append(type, "\t\t\t\t");
              _builder.append(" buffer_");
              _builder.append(sdfname, "\t\t\t\t");
              _builder.append("[2];");
              _builder.newLineIfNotEmpty();
              _builder.append("\t\t\t\t");
              _builder.append("int channel_");
              _builder.append(sdfname, "\t\t\t\t");
              _builder.append("_size = 1;");
              _builder.newLineIfNotEmpty();
              _builder.append("\t\t\t\t");
              _builder.append("/* Because of circular fifo, the buffer_size=channel_size+1 */");
              _builder.newLine();
              _builder.append("\t\t\t\t");
              _builder.append("int buffer_");
              _builder.append(sdfname, "\t\t\t\t");
              _builder.append("_size = 2;");
              _builder.newLineIfNotEmpty();
              _builder.append("\t\t\t\t");
              _builder.append("circular_fifo_");
              _builder.append(type, "\t\t\t\t");
              _builder.append(" fifo_");
              _builder.append(sdfname, "\t\t\t\t");
              _builder.append(";");
              _builder.newLineIfNotEmpty();
              _builder.append("\t\t\t\t");
              _builder.append("spinlock spinlock_");
              _builder.append(sdfname, "\t\t\t\t");
              _builder.append("={.flag=0};\t");
              _builder.newLineIfNotEmpty();
            } else {
              _builder.append("volatile cheap const fifo_admin_");
              _builder.append(sdfname);
              _builder.append(";");
              _builder.newLineIfNotEmpty();
              _builder.append("unsigned int buffer_");
              _builder.append(sdfname);
              _builder.append("_size=1;");
              _builder.newLineIfNotEmpty();
              _builder.append("unsigned int token_");
              _builder.append(sdfname);
              _builder.append("_size=");
              long _tokenSize_1 = Query.getTokenSize(sdfchannel);
              _builder.append(_tokenSize_1);
              _builder.append("\t;");
              _builder.newLineIfNotEmpty();
              _builder.append("volatile ");
              _builder.append(type);
              _builder.append(" buffer_");
              _builder.append(sdfname);
              _builder.append("[1];\t\t\t\t\t\t\t");
              _builder.newLineIfNotEmpty();
            }
          }
        }
      }
      _builder.append("\t\t\t");
      _builder.append("#endif");
      _builder.newLine();
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
}
