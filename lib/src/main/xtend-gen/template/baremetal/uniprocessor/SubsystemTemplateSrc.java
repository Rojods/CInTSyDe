package template.baremetal.uniprocessor;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.Vertex;
import generator.Generator;
import generator.Schedule;
import java.util.Map;
import java.util.Set;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.templateInterface.SubsystemTemplate;

@FileTypeAnno(type = FileType.C_SOURCE)
@SuppressWarnings("all")
public class SubsystemTemplateSrc implements SubsystemTemplate {
  public String create(final Schedule s) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#include \"../inc/subsystem_include_help.h\"");
    _builder.newLine();
    _builder.append("/*");
    _builder.newLine();
    _builder.append("==============================================");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("Extern Variables are decalred in the ");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("subsystem_include_help.h");
    _builder.newLine();
    _builder.append("==============================================");
    _builder.newLine();
    _builder.append("*/");
    _builder.newLine();
    _builder.append("/*");
    _builder.newLine();
    _builder.append("==============================================");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("Subsystem Function");
    _builder.newLine();
    _builder.append("==============================================");
    _builder.newLine();
    _builder.append("*/\t");
    _builder.newLine();
    _builder.append("void initChannels();");
    _builder.newLine();
    _builder.append("int subsystem_single_uniprocessor(){");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* Initilize Channels */");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("initChannels();");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/*    SDFdelay        */");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("while(1){");
    _builder.newLine();
    {
      Set<Map.Entry<Integer, Vertex>> _entrySet = Generator.uniprocessorSchedule.entrySet();
      boolean _hasElements = false;
      for(final Map.Entry<Integer, Vertex> set : _entrySet) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate("", "\t\t");
        }
        _builder.append("\t\t");
        _builder.append("actor_");
        String _identifier = set.getValue().getIdentifier();
        _builder.append(_identifier, "\t\t");
        _builder.append("();");
        _builder.newLineIfNotEmpty();
      }
      if (_hasElements) {
        _builder.append("", "\t\t");
      }
    }
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("void initChannels(){");
    _builder.newLine();
    _builder.append("\t");
    String _initChannels = this.initChannels();
    _builder.append(_initChannels, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder.toString();
  }
  
  public String initChannels() {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final Vertex channel : Generator.sdfchannelSet) {
        String sdfname = channel.getIdentifier();
        _builder.newLineIfNotEmpty();
        _builder.append("init_channel_type(&fifo_");
        _builder.append(sdfname);
        _builder.append(",buffer_");
        _builder.append(sdfname);
        _builder.append(",buffer_");
        _builder.append(sdfname);
        _builder.append("_size);");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder.toString();
  }
  
  public String getFileName() {
    return "subsystem";
  }
}
