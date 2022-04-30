package template.baremetal;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.core.VertexProperty;
import java.util.Map;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.templateInterface.ChannelTemplate;

/**
 * without distinguish if the sdfchannel is a state variable
 */
@FileTypeAnno(type = FileType.C_SOURCE)
@SuppressWarnings("all")
public class SDFChannelTemplateSrc implements ChannelTemplate {
  public String create(final Vertex sdfchannel) {
    String _xblockexpression = null;
    {
      Map<String, VertexProperty> properties = sdfchannel.getProperties();
      StringConcatenation _builder = new StringConcatenation();
      String sdfname = sdfchannel.getIdentifier();
      _builder.newLineIfNotEmpty();
      _builder.append("#include \"../inc/circular_fifo_lib.h\"");
      _builder.newLine();
      {
        Boolean _hasTrait = sdfchannel.hasTrait("decision::sdf::BoundedSDFChannel");
        if ((_hasTrait).booleanValue()) {
          Object _unwrap = properties.get("maximumTokens").unwrap();
          Integer maximumTokens = ((Integer) _unwrap);
          _builder.newLineIfNotEmpty();
          _builder.append("type buffer_");
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
          _builder.append("circular_fifo_type fifo_");
          _builder.append(sdfname);
          _builder.append(";");
          _builder.newLineIfNotEmpty();
          _builder.append("spinlock spinlock_");
          _builder.append(sdfname);
          _builder.append("={.flag=0};");
          _builder.newLineIfNotEmpty();
        } else {
          _builder.append("type buffer_");
          _builder.append(sdfname);
          _builder.append("[2];");
          _builder.newLineIfNotEmpty();
          _builder.append("int buffer_");
          _builder.append(sdfname);
          _builder.append("_size = 2;");
          _builder.newLineIfNotEmpty();
          _builder.append("circular_fifo_type fifo_");
          _builder.append(sdfname);
          _builder.append(";");
          _builder.newLineIfNotEmpty();
          _builder.append("spinlock spinlock_");
          _builder.append(sdfname);
          _builder.append("={.flag=0};\t\t\t");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.newLine();
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
}
