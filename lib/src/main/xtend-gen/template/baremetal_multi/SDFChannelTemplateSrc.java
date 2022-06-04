package template.baremetal_multi;

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
      _builder.append("#include \"../inc/spinlock.h\"");
      _builder.newLine();
      _builder.append("#include \"../inc/datatype_definition.h\"");
      _builder.newLine();
      String channelname = sdfchannel.getIdentifier();
      _builder.newLineIfNotEmpty();
      _builder.append("#include \"../inc/circular_fifo_lib.h\"");
      _builder.newLine();
      _builder.append("#include <cheap_s.h>");
      _builder.newLine();
      _builder.append("#define tile0_comm1 0x80020000");
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
            boolean _isOnOneCoreChannel = Query.isOnOneCoreChannel(model, sdfchannel);
            if (_isOnOneCoreChannel) {
              _builder.append("\t");
              _builder.append("/* Channel On One Processor */");
              _builder.newLine();
              _builder.append("\t");
              _builder.append("volatile ");
              _builder.append(type, "\t");
              _builder.append(" buffer_");
              _builder.append(channelname, "\t");
              _builder.append("[");
              _builder.append(((maximumTokens).intValue() + 1), "\t");
              _builder.append("];");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("int channel_");
              _builder.append(channelname, "\t");
              _builder.append("_size=");
              _builder.append(maximumTokens, "\t");
              _builder.append(";");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("int buffer_");
              _builder.append(channelname, "\t");
              _builder.append("_size = ");
              _builder.append(((maximumTokens).intValue() + 1), "\t");
              _builder.append("; //Because of circular fifo, the buffer_size=channel_size+1 ");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("circular_fifo_");
              _builder.append(type, "\t");
              _builder.append(" fifo_");
              _builder.append(channelname, "\t");
              _builder.append(";");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("spinlock spinlock_");
              _builder.append(channelname, "\t");
              _builder.append("={.flag=0};");
              _builder.newLineIfNotEmpty();
            } else {
              _builder.append("\t");
              _builder.append("/* Channel Between Two Processors */");
              _builder.newLine();
              _builder.append("\t");
              _builder.append(" ");
              _builder.append("volatile cheap const fifo_admin_");
              _builder.append(channelname, "\t ");
              _builder.append("=(cheap) ");
              String _upperCase = channelname.toUpperCase();
              _builder.append(_upperCase, "\t ");
              _builder.append("_ADDR;");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append(" ");
              _builder.append("volatile ");
              _builder.append(type, "\t ");
              _builder.append(" * const fifo_data_");
              _builder.append(channelname, "\t ");
              _builder.append("=(");
              _builder.append(type, "\t ");
              _builder.append("  *)((cheap) ");
              String _upperCase_1 = channelname.toUpperCase();
              _builder.append(_upperCase_1, "\t ");
              _builder.append("_ADDR +1);\t\t\t ");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append(" ");
              _builder.append("unsigned int buffer_");
              _builder.append(channelname, "\t ");
              _builder.append("_size=");
              int _bufferSize = Query.getBufferSize(sdfchannel);
              _builder.append(_bufferSize, "\t ");
              _builder.append(";");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append(" ");
              _builder.append("unsigned int token_");
              _builder.append(channelname, "\t ");
              _builder.append("_size=");
              long _tokenSize = Query.getTokenSize(sdfchannel);
              _builder.append(_tokenSize, "\t ");
              _builder.append("\t;");
              _builder.newLineIfNotEmpty();
            }
          }
        } else {
          {
            boolean _isOnOneCoreChannel_1 = Query.isOnOneCoreChannel(model, sdfchannel);
            if (_isOnOneCoreChannel_1) {
              _builder.append("\t");
              _builder.append("/* Channel On One Processor */");
              _builder.newLine();
              _builder.append("\t");
              _builder.append("volatile ");
              _builder.append(type, "\t");
              _builder.append(" buffer_");
              _builder.append(channelname, "\t");
              _builder.append("[2];");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("unsigned int channel_");
              _builder.append(channelname, "\t");
              _builder.append("_size = 1;");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("unsigned int buffer_");
              _builder.append(channelname, "\t");
              _builder.append("_size = 2; // Because of circular fifo, the buffer_size=channel_size+1 ");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("circular_fifo_");
              _builder.append(type, "\t");
              _builder.append(" fifo_");
              _builder.append(channelname, "\t");
              _builder.append(";");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("spinlock spinlock_");
              _builder.append(channelname, "\t");
              _builder.append("={.flag=0};\t");
              _builder.newLineIfNotEmpty();
            } else {
              _builder.append("\t");
              _builder.append("/* Channel Between Two Processors */");
              _builder.newLine();
              _builder.append("\t");
              _builder.newLine();
              _builder.append(" \t\t\t\t\t ");
              _builder.append("volatile cheap const fifo_admin_");
              _builder.append(channelname, " \t\t\t\t\t ");
              _builder.append("=(cheap) ");
              String _upperCase_2 = channelname.toUpperCase();
              _builder.append(_upperCase_2, " \t\t\t\t\t ");
              _builder.append("_ADDR;");
              _builder.newLineIfNotEmpty();
              _builder.append(" \t\t\t\t\t ");
              _builder.append("volatile ");
              _builder.append(type, " \t\t\t\t\t ");
              _builder.append(" * const fifo_data_");
              _builder.append(channelname, " \t\t\t\t\t ");
              _builder.append("=(");
              _builder.append(type, " \t\t\t\t\t ");
              _builder.append("  *)((cheap) ");
              String _upperCase_3 = channelname.toUpperCase();
              _builder.append(_upperCase_3, " \t\t\t\t\t ");
              _builder.append("_ADDR +1); \t\t\t\t\t ");
              _builder.newLineIfNotEmpty();
              _builder.append(" \t\t\t\t\t ");
              _builder.append("unsigned int buffer_");
              _builder.append(channelname, " \t\t\t\t\t ");
              _builder.append("_size=1;");
              _builder.newLineIfNotEmpty();
              _builder.append(" \t\t\t\t\t ");
              _builder.append("unsigned int token_");
              _builder.append(channelname, " \t\t\t\t\t ");
              _builder.append("_size=");
              long _tokenSize_1 = Query.getTokenSize(sdfchannel);
              _builder.append(_tokenSize_1, " \t\t\t\t\t ");
              _builder.append("\t;");
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
