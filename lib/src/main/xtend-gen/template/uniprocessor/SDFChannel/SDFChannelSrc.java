package template.uniprocessor.SDFChannel;

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

@SuppressWarnings("all")
public class SDFChannelSrc implements ChannelTemplate {
  private Vertex sdfchannel;
  
  @Override
  public String savePath() {
    String _identifier = this.sdfchannel.getIdentifier();
    String _plus = ("/sdfchannel/sdfchannel_" + _identifier);
    return (_plus + ".c");
  }
  
  @Override
  public String create(final Vertex sdfchannel) {
    String _xblockexpression = null;
    {
      ForSyDeSystemGraph model = Generator.model;
      this.sdfchannel = sdfchannel;
      String type = Query.findSDFChannelDataType(Generator.model, sdfchannel);
      Map<String, VertexProperty> properties = sdfchannel.getProperties();
      StringConcatenation _builder = new StringConcatenation();
      _builder.newLine();
      String sdfname = sdfchannel.getIdentifier();
      _builder.newLineIfNotEmpty();
      _builder.append("#include \"sdfchannel_");
      _builder.append(sdfname);
      _builder.append(".h\"");
      _builder.newLineIfNotEmpty();
      _builder.append("#include \"../../circular_fifo_lib/circular_fifo_lib.h\"");
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
              _builder.append("\t");
              _builder.append("\t");
              _builder.append("volatile ");
              _builder.append(type, "\t\t");
              _builder.append(" buffer_");
              _builder.append(sdfname, "\t\t");
              _builder.append("[");
              _builder.append(((maximumTokens).intValue() + 1), "\t\t");
              _builder.append("];");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("\t");
              _builder.append("int channel_");
              _builder.append(sdfname, "\t\t");
              _builder.append("_size=");
              _builder.append(maximumTokens, "\t\t");
              _builder.append(";");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("\t");
              _builder.append("/*Because of circular fifo, the buffer_size=channel_size+1 */");
              _builder.newLine();
              _builder.append("\t");
              _builder.append("\t");
              _builder.append("int buffer_");
              _builder.append(sdfname, "\t\t");
              _builder.append("_size = ");
              _builder.append(((maximumTokens).intValue() + 1), "\t\t");
              _builder.append(";");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("\t");
              _builder.append("circular_fifo_");
              _builder.append(type, "\t\t");
              _builder.append(" fifo_");
              _builder.append(sdfname, "\t\t");
              _builder.append(";");
              _builder.newLineIfNotEmpty();
            }
          }
          {
            if ((Generator.fifoType == 2)) {
              _builder.append("\t");
              _builder.append("\t");
              _builder.append("circular_fifo fifo_");
              _builder.append(sdfname, "\t\t");
              _builder.append(";");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("\t");
              _builder.append("volatile ");
              _builder.append(type, "\t\t");
              _builder.append(" buffer_");
              _builder.append(sdfname, "\t\t");
              _builder.append("[");
              _builder.append(((maximumTokens).intValue() + 1), "\t\t");
              _builder.append("];");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("\t");
              _builder.append("int channel_");
              _builder.append(sdfname, "\t\t");
              _builder.append("_size=");
              _builder.append(maximumTokens, "\t\t");
              _builder.append(";");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("\t");
              _builder.append("/*Because of circular fifo, the buffer_size=channel_size+1 */");
              _builder.newLine();
              _builder.append("\t");
              _builder.append("\t");
              _builder.append("int buffer_");
              _builder.append(sdfname, "\t\t");
              _builder.append("_size = ");
              _builder.append(((maximumTokens).intValue() + 1), "\t\t");
              _builder.append(";");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("\t");
              _builder.newLine();
            }
          }
          _builder.append("\t\t\t");
          _builder.newLine();
        } else {
          {
            if ((Generator.fifoType == 1)) {
              _builder.append("\t");
              _builder.append("volatile ");
              _builder.append(type, "\t");
              _builder.append(" buffer_");
              _builder.append(sdfname, "\t");
              _builder.append("[2];");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("int channel_");
              _builder.append(sdfname, "\t");
              _builder.append("_size = 1;");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("/*Because of circular fifo, the buffer_size=channel_size+1 */");
              _builder.newLine();
              _builder.append("\t");
              _builder.append("int buffer_");
              _builder.append(sdfname, "\t");
              _builder.append("_size = 2;");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("circular_fifo_");
              _builder.append(type, "\t");
              _builder.append(" fifo_");
              _builder.append(sdfname, "\t");
              _builder.append(";");
              _builder.newLineIfNotEmpty();
            }
          }
          {
            if ((Generator.fifoType == 2)) {
              _builder.append("\t");
              _builder.append("\t");
              _builder.append("circular_fifo fifo_");
              _builder.append(sdfname, "\t\t");
              _builder.append(";");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("\t");
              _builder.append("volatile ");
              _builder.append(type, "\t\t");
              _builder.append(" buffer_");
              _builder.append(sdfname, "\t\t");
              _builder.append("[2];");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("\t");
              _builder.append("int channel_");
              _builder.append(sdfname, "\t\t");
              _builder.append("_size=1;");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("\t");
              _builder.append("/*Because of circular fifo, the buffer_size=channel_size+1 */");
              _builder.newLine();
              _builder.append("\t");
              _builder.append("\t");
              _builder.append("int buffer_");
              _builder.append(sdfname, "\t\t");
              _builder.append("_size = 2;");
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
