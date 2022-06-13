package template.baremetal_multi;

import forsyde.io.java.core.Vertex;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.templateInterface.SubsystemTemplate;

@SuppressWarnings("all")
public class SubsystemIncMulti implements SubsystemTemplate {
  private Vertex tile;
  
  @Override
  public String create(final Vertex tile) {
    String _xblockexpression = null;
    {
      this.tile = tile;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("#ifndef SUBSYSTEM_");
      int _hashCode = this.hashCode();
      _builder.append(_hashCode);
      _builder.append("_H_");
      _builder.newLineIfNotEmpty();
      _builder.append("#define SUBSYSTEM_");
      int _hashCode_1 = this.hashCode();
      _builder.append(_hashCode_1);
      _builder.append("_H_");
      _builder.newLineIfNotEmpty();
      _builder.append("/* Includes--------------------*/");
      _builder.newLine();
      _builder.newLine();
      _builder.append("/* Function Prototype----------*/");
      _builder.newLine();
      _builder.append("void subsystem_");
      String _identifier = tile.getIdentifier();
      _builder.append(_identifier);
      _builder.append("();");
      _builder.newLineIfNotEmpty();
      _builder.append("int init_");
      String _identifier_1 = tile.getIdentifier();
      _builder.append(_identifier_1);
      _builder.append("();");
      _builder.newLineIfNotEmpty();
      _builder.append("#endif\t\t");
      _builder.newLine();
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
  
  @Override
  public String savePath() {
    String _identifier = this.tile.getIdentifier();
    String _plus = ("/" + _identifier);
    String _plus_1 = (_plus + "/subsystem_");
    String _identifier_1 = this.tile.getIdentifier();
    String _plus_2 = (_plus_1 + _identifier_1);
    return (_plus_2 + ".h");
  }
}
