package template.baremetal.uniprocessor;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.Vertex;
import generator.Generator;
import generator.Schedule;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.templateInterface.SubsystemTemplate;

@FileTypeAnno(type = FileType.C_INCLUDE)
@SuppressWarnings("all")
public class SubsystemTemplateInc2 implements SubsystemTemplate {
  public String create(final Schedule s) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#ifndef SUBSYSTEM_INCLUDE_HELP_H_");
    _builder.newLine();
    _builder.append("#define SUBSYSTEM_INCLUDE_HELP_H_");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/*");
    _builder.newLine();
    _builder.append("****************************************************************");
    _builder.newLine();
    _builder.append("The aim of this .h file is to help subsystem.c");
    _builder.newLine();
    _builder.append("Only the subsystem.c includes this file.");
    _builder.newLine();
    _builder.append("****************************************************************");
    _builder.newLine();
    _builder.append("*/");
    _builder.newLine();
    _builder.append("#include \"datatype_definition.h\"");
    _builder.newLine();
    _builder.append("#include \"circular_fifo_lib.h\"");
    _builder.newLine();
    _builder.append("/*");
    _builder.newLine();
    _builder.append("==============================================");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("Extern Variables ");
    _builder.newLine();
    _builder.append("==============================================");
    _builder.newLine();
    _builder.append("*/\t\t");
    _builder.newLine();
    String _externChannel = this.externChannel();
    _builder.append(_externChannel);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("#endif");
    _builder.newLine();
    return _builder.toString();
  }
  
  public String externChannel() {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final Vertex channel : Generator.sdfchannelSet) {
        String sdfname = channel.getIdentifier();
        _builder.newLineIfNotEmpty();
        _builder.append("/* extern sdfchannel ");
        _builder.append(sdfname);
        _builder.append("*/");
        _builder.newLineIfNotEmpty();
        _builder.append("extern type buffer_");
        _builder.append(sdfname);
        _builder.append("[];");
        _builder.newLineIfNotEmpty();
        _builder.append("extern int buffer_");
        _builder.append(sdfname);
        _builder.append("_size;");
        _builder.newLineIfNotEmpty();
        _builder.append("extern circular_fifo_type fifo_");
        _builder.append(sdfname);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.newLine();
      }
    }
    return _builder.toString();
  }
  
  public String getFileName() {
    return "subsystem_include_help";
  }
}
