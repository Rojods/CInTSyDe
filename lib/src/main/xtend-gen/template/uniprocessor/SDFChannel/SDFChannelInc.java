package template.uniprocessor.SDFChannel;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.Vertex;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.templateInterface.ChannelTemplate;

/**
 * without distinguish if the sdfchannel is a state variable
 */
@FileTypeAnno(type = FileType.C_SOURCE)
@SuppressWarnings("all")
public class SDFChannelInc implements ChannelTemplate {
  private Vertex sdfchannel;
  
  @Override
  public String create(final Vertex vertex) {
    String _xblockexpression = null;
    {
      this.sdfchannel = vertex;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("#ifndef  ");
      String _upperCase = vertex.getIdentifier().toUpperCase();
      _builder.append(_upperCase);
      _builder.append("_h_ ");
      _builder.newLineIfNotEmpty();
      _builder.append("#define ");
      String _upperCase_1 = vertex.getIdentifier().toUpperCase();
      _builder.append(_upperCase_1);
      _builder.append("_h_ ");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("#define ");
      String _upperCase_2 = vertex.getIdentifier().toUpperCase();
      _builder.append(_upperCase_2);
      _builder.append("_BLOCKING 0");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("#endif");
      _builder.newLine();
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
  
  @Override
  public String savePath() {
    String _identifier = this.sdfchannel.getIdentifier();
    String _plus = ("/sdfchannel/sdfchannel_" + _identifier);
    return (_plus + ".h");
  }
}
