package template.baremetal_multi;

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

@FileTypeAnno(type = FileType.C_SOURCE)
@SuppressWarnings("all")
public class DataTypeSrc implements InitTemplate {
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
      _builder.append("#include \"../inc/datatype_definition.h\"");
      _builder.newLine();
      _builder.newLine();
      {
        for(final IntegerValue value : integerValues) {
          _builder.append("int ");
          String _identifier = value.getIdentifier();
          _builder.append(_identifier);
          _builder.append("=");
          Integer _intValue = value.getIntValue();
          _builder.append(_intValue);
          _builder.append(";");
          _builder.newLineIfNotEmpty();
        }
      }
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
  
  @Override
  public String getFileName() {
    return "data_definition";
  }
}
