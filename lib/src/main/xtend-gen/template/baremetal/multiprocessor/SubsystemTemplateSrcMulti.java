package template.baremetal.multiprocessor;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel;
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
      _builder.append("\t\t");
      _builder.append("#include \"../inc/subsystem_");
      String _identifier = this.s.tile.getIdentifier();
      _builder.append(_identifier, "\t\t");
      _builder.append(".h\"");
      _builder.newLineIfNotEmpty();
      {
        boolean _hasElements = false;
        for(final Vertex channel : schedule.channels) {
          if (!_hasElements) {
            _hasElements = true;
          } else {
            _builder.appendImmediate("", "\t\t");
          }
          _builder.append("\t\t");
          String channelName = Name.name(channel);
          _builder.newLineIfNotEmpty();
          {
            Boolean _conforms = SDFChannel.conforms(channel);
            if ((_conforms).booleanValue()) {
              _builder.append("\t\t");
              _builder.append("extern circularFIFO_");
              _builder.append(channelName, "\t\t");
              _builder.append(" channel_");
              _builder.append(channelName, "\t\t");
              _builder.append(";");
              _builder.newLineIfNotEmpty();
              _builder.append("\t\t");
              _builder.append("extern token_");
              _builder.append(channelName, "\t\t");
              _builder.append(" arr_");
              _builder.append(channelName, "\t\t");
              _builder.append("[];");
              _builder.newLineIfNotEmpty();
              _builder.append("\t\t");
              _builder.append("extern int buffersize_");
              _builder.append(channelName, "\t\t");
              _builder.append(";");
              _builder.newLineIfNotEmpty();
            } else {
              _builder.append("\t\t");
              _builder.append("extern circularFIFO_");
              _builder.append(channelName, "\t\t");
              _builder.append(" channel_");
              _builder.append(channelName, "\t\t");
              _builder.append(";");
              _builder.newLineIfNotEmpty();
            }
          }
        }
        if (_hasElements) {
          _builder.append("", "\t\t");
        }
      }
      _builder.append("\t\t");
      _builder.append("void subsystem_");
      String _identifier_1 = tile.getIdentifier();
      _builder.append(_identifier_1, "\t\t");
      _builder.append("(){");
      _builder.newLineIfNotEmpty();
      _builder.append("\t\t\t");
      _builder.newLine();
      _builder.append("//\t\t\twhile(1){");
      _builder.newLine();
      {
        boolean _hasElements_1 = false;
        for(final Vertex actor : schedule.slots) {
          if (!_hasElements_1) {
            _hasElements_1 = true;
          } else {
            _builder.appendImmediate("", "\t\t\t\t");
          }
          {
            if ((actor != null)) {
              _builder.append("\t\t\t\t");
              _builder.append("actor_");
              String _name = Name.name(actor);
              _builder.append(_name, "\t\t\t\t");
              _builder.append("();");
              _builder.newLineIfNotEmpty();
            }
          }
        }
        if (_hasElements_1) {
          _builder.append("\n", "\t\t\t\t");
        }
      }
      _builder.append("\t\t\t");
      _builder.newLine();
      _builder.append("//\t\t\t}\t");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("}\t");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("void init_");
      String _identifier_2 = tile.getIdentifier();
      _builder.append(_identifier_2, "\t\t");
      _builder.append("(){");
      _builder.newLineIfNotEmpty();
      {
        boolean _hasElements_2 = false;
        for(final Vertex channel_1 : schedule.channels) {
          if (!_hasElements_2) {
            _hasElements_2 = true;
          } else {
            _builder.appendImmediate("", "\t\t\t");
          }
          _builder.append("\t\t\t");
          String channelName_1 = Name.name(channel_1);
          _builder.newLineIfNotEmpty();
          {
            Boolean _conforms_1 = SDFChannel.conforms(channel_1);
            if ((_conforms_1).booleanValue()) {
              _builder.append("\t\t\t");
              _builder.append("init_circularFIFO_");
              _builder.append(channelName_1, "\t\t\t");
              _builder.append("(&channel_");
              _builder.append(channelName_1, "\t\t\t");
              _builder.append(",arr_");
              _builder.append(channelName_1, "\t\t\t");
              _builder.append(",buffersize_");
              _builder.append(channelName_1, "\t\t\t");
              _builder.append(");");
              _builder.newLineIfNotEmpty();
            }
          }
        }
        if (_hasElements_2) {
          _builder.append("", "\t\t\t");
        }
      }
      _builder.append("\t\t");
      _builder.append("}");
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
