package template.baremetal;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.core.VertexProperty;
import forsyde.io.java.typed.viewers.decision.sdf.BoundedSDFChannel;
import forsyde.io.java.typed.viewers.decision.sdf.BoundedSDFChannelViewer;
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel;
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
      String type = Query.findSDFChannelDataType(Generator.model, sdfchannel);
      Map<String, VertexProperty> properties = sdfchannel.getProperties();
      StringConcatenation _builder = new StringConcatenation();
      String sdfname = sdfchannel.getIdentifier();
      _builder.newLineIfNotEmpty();
      _builder.append("#include \"../inc/circular_fifo_lib.h\"");
      _builder.newLine();
      {
        Boolean _conforms = BoundedSDFChannel.conforms(sdfchannel);
        if ((_conforms).booleanValue()) {
          BoundedSDFChannelViewer viewer = new BoundedSDFChannelViewer(sdfchannel);
          _builder.newLineIfNotEmpty();
          Integer maximumTokens = viewer.getMaximumTokens();
          _builder.newLineIfNotEmpty();
          _builder.append("volatile ");
          _builder.append(type);
          _builder.append(" buffer_");
          _builder.append(sdfname);
          _builder.append("[");
          _builder.append(((maximumTokens).intValue() + 1));
          _builder.append("];");
          _builder.newLineIfNotEmpty();
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
          _builder.append("spinlock spinlock_");
          _builder.append(sdfname);
          _builder.append("={.flag=0};");
          _builder.newLineIfNotEmpty();
        } else {
          _builder.append("volatile ");
          _builder.append(type);
          _builder.append(" buffer_");
          _builder.append(sdfname);
          _builder.append("[");
          Integer _numOfInitialTokens = SDFChannel.safeCast(sdfchannel).get().getNumOfInitialTokens();
          int _plus = ((_numOfInitialTokens).intValue() + 1);
          _builder.append(_plus);
          _builder.append("];");
          _builder.newLineIfNotEmpty();
          _builder.append("int buffer_");
          _builder.append(sdfname);
          _builder.append("_size = ");
          Integer _numOfInitialTokens_1 = SDFChannel.safeCast(sdfchannel).get().getNumOfInitialTokens();
          int _plus_1 = ((_numOfInitialTokens_1).intValue() + 1);
          _builder.append(_plus_1);
          _builder.append(";");
          _builder.newLineIfNotEmpty();
          _builder.append("circular_fifo_");
          _builder.append(type);
          _builder.append(" fifo_");
          _builder.append(sdfname);
          _builder.append(";");
          _builder.newLineIfNotEmpty();
          _builder.append("spinlock spinlock_");
          _builder.append(sdfname);
          _builder.append("={.flag=0};\t");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.newLine();
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
}
