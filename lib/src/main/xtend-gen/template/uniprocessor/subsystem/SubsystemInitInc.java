package template.uniprocessor.subsystem;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.typed.viewers.values.IntegerValue;
import generator.Generator;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.templateInterface.InitTemplate;
import utils.Query;

@FileTypeAnno(type = FileType.C_INCLUDE)
@Deprecated
@SuppressWarnings("all")
public class SubsystemInitInc implements InitTemplate {
  @Override
  public String create() {
    String _xblockexpression = null;
    {
      ForSyDeSystemGraph model = Generator.model;
      final Predicate<Vertex> _function = (Vertex v) -> {
        return (IntegerValue.conforms(v)).booleanValue();
      };
      final Function<Vertex, IntegerValue> _function_1 = (Vertex v) -> {
        return IntegerValue.safeCast(v).get();
      };
      Set<IntegerValue> integerValues = model.vertexSet().stream().filter(_function).<IntegerValue>map(_function_1).collect(Collectors.<IntegerValue>toSet());
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("#ifndef SUBSYSTEM_INIT_H_");
      _builder.newLine();
      _builder.append("#define SUBSYSTEM_INIT_H_");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.newLine();
      _builder.append("void init_subsystem();\t\t\t");
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
  
  @Override
  public String getFileName() {
    return "subsystem_init";
  }
}
