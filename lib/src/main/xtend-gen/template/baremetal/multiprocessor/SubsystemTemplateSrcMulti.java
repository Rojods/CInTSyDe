package template.baremetal.multiprocessor;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.Vertex;
import generator.Schedule;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.templateInterface.SubsystemTemplate;
import utils.Name;

@FileTypeAnno(type = FileType.C_SOURCE)
@SuppressWarnings("all")
public class SubsystemTemplateSrcMulti implements SubsystemTemplate {
  private Schedule s;
  
  public String create(final Schedule schedule) {
    String _xblockexpression = null;
    {
      this.s = schedule;
      Vertex tile = schedule.tile;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("#include \"../inc/subsystem_");
      String _identifier = this.s.tile.getIdentifier();
      _builder.append(_identifier);
      _builder.append(".h\"");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("void fire_subsystem_");
      String _identifier_1 = tile.getIdentifier();
      _builder.append(_identifier_1);
      _builder.append("(){");
      _builder.newLineIfNotEmpty();
      {
        boolean _hasElements = false;
        for(final Vertex actor : schedule.slots) {
          if (!_hasElements) {
            _hasElements = true;
          } else {
            _builder.appendImmediate("", "");
          }
          int tmp = 1;
          _builder.newLineIfNotEmpty();
          {
            if ((actor != null)) {
              _builder.append("\t");
              _builder.append("actor_");
              String _name = Name.name(actor);
              _builder.append(_name, "\t");
              _builder.append("();");
              _builder.newLineIfNotEmpty();
            }
          }
        }
        if (_hasElements) {
          _builder.append("");
        }
      }
      _builder.append("}\t");
      _builder.newLine();
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
  
  public String getFileName() {
    String _identifier = this.s.tile.getIdentifier();
    return ("subsystem_tile_" + _identifier);
  }
}
