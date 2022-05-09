package template.baremetal.uniprocessor;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.typed.viewers.values.IntegerValue;
import generator.Generator;
import generator.Schedule;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.templateInterface.SubsystemTemplate;
import utils.Query;

@FileTypeAnno(type = FileType.C_INCLUDE)
@SuppressWarnings("all")
public class SubsystemTemplateInc2 implements SubsystemTemplate {
  public String create(final Schedule s) {
    String _xblockexpression = null;
    {
      ForSyDeSystemGraph model = Generator.model;
      final Predicate<Vertex> _function = new Predicate<Vertex>() {
        public boolean test(final Vertex v) {
          return (IntegerValue.conforms(v)).booleanValue();
        }
      };
      final Function<Vertex, IntegerValue> _function_1 = new Function<Vertex, IntegerValue>() {
        public IntegerValue apply(final Vertex v) {
          return IntegerValue.safeCast(v).get();
        }
      };
      Set<IntegerValue> integerValues = model.vertexSet().stream().filter(_function).<IntegerValue>map(_function_1).collect(Collectors.<IntegerValue>toSet());
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
      {
        for(final IntegerValue value : integerValues) {
          _builder.append("extern int ");
          String _identifier = value.getIdentifier();
          _builder.append(_identifier);
          _builder.append(";");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.newLine();
      String _externChannel = this.externChannel();
      _builder.append(_externChannel);
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("#endif");
      _builder.newLine();
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
  
  public String externChannel() {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final Vertex channel : Generator.sdfchannelSet) {
        String sdfname = channel.getIdentifier();
        _builder.newLineIfNotEmpty();
        String type = Query.findSDFChannelDataType(Generator.model, channel);
        _builder.newLineIfNotEmpty();
        _builder.append("/* extern sdfchannel ");
        _builder.append(sdfname);
        _builder.append("*/");
        _builder.newLineIfNotEmpty();
        _builder.append("extern ");
        _builder.append(type);
        _builder.append(" buffer_");
        _builder.append(sdfname);
        _builder.append("[];");
        _builder.newLineIfNotEmpty();
        _builder.append("extern int buffer_");
        _builder.append(sdfname);
        _builder.append("_size;");
        _builder.newLineIfNotEmpty();
        _builder.append("extern circular_fifo_");
        _builder.append(type);
        _builder.append(" fifo_");
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
