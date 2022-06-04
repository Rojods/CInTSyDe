package template.uniprocessor.fifo.fifo1;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.core.VertexProperty;
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel;
import generator.Generator;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.templateInterface.InitTemplate;
import utils.Query;

@FileTypeAnno(type = FileType.C_INCLUDE)
@SuppressWarnings("all")
public class FIFOInc1 implements InitTemplate {
  private Set<Vertex> typeVertexSet;
  
  public FIFOInc1() {
    final ForSyDeSystemGraph model = Generator.model;
    final Predicate<Vertex> _function = (Vertex v) -> {
      return (SDFChannel.conforms(v)).booleanValue();
    };
    final Function<Vertex, String> _function_1 = (Vertex v) -> {
      return Query.findSDFChannelDataType(model, v);
    };
    final Function<String, Vertex> _function_2 = (String s) -> {
      return Query.findVertexByName(model, s);
    };
    this.typeVertexSet = model.vertexSet().stream().filter(_function).<String>map(_function_1).<Vertex>map(_function_2).collect(Collectors.<Vertex>toSet());
    boolean _contains = this.typeVertexSet.contains(null);
    if (_contains) {
      this.typeVertexSet.remove(null);
    }
  }
  
  @Override
  public String create() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#ifndef CIRCULAR_FIFO_LIB_H_");
    _builder.newLine();
    _builder.append("#define CIRCULAR_FIFO_LIB_H_");
    _builder.newLine();
    _builder.append("#include \"config.h\"");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/*");
    _builder.newLine();
    _builder.append("************************************************************");
    _builder.newLine();
    _builder.append("This header file defines all the prototype of token types in");
    _builder.newLine();
    _builder.append("SDFChannels");
    _builder.newLine();
    _builder.append("************************************************************");
    _builder.newLine();
    _builder.append("*/");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.append("#include \"datatype_definition.h\"");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#include \"spinlock.h\"\t");
    _builder.newLine();
    _builder.newLine();
    {
      int _size = this.typeVertexSet.size();
      boolean _notEquals = (_size != 0);
      if (_notEquals) {
        {
          boolean _hasElements = false;
          for(final Vertex v : this.typeVertexSet) {
            if (!_hasElements) {
              _hasElements = true;
            } else {
              _builder.appendImmediate("", "");
            }
            String _foo = this.foo(v);
            _builder.append(_foo);
            _builder.newLineIfNotEmpty();
          }
          if (_hasElements) {
            _builder.append("");
          }
        }
      }
    }
    _builder.newLine();
    _builder.newLine();
    _builder.append("#endif");
    _builder.newLine();
    return _builder.toString();
  }
  
  @Override
  public String getFileName() {
    return "circular_fifo_lib";
  }
  
  public String foo(final Vertex v) {
    StringConcatenation _builder = new StringConcatenation();
    final String type = v.getIdentifier();
    _builder.newLineIfNotEmpty();
    _builder.append("/*");
    _builder.newLine();
    _builder.append("=============================================================");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("If Token type is ");
    _builder.append(type, "\t\t\t");
    _builder.append(" ");
    _builder.newLineIfNotEmpty();
    _builder.append("=============================================================");
    _builder.newLine();
    _builder.append("*/");
    _builder.newLine();
    _builder.append("typedef struct ");
    _builder.newLine();
    _builder.append("{");
    _builder.newLine();
    _builder.append("    ");
    _builder.append(type, "    ");
    _builder.append("* buffer;");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("size_t front;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("size_t rear;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("size_t size;\t    ");
    _builder.newLine();
    _builder.append("}circular_fifo_");
    _builder.append(type);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("void init_channel_");
    _builder.append(type);
    _builder.append("(circular_fifo_");
    _builder.append(type);
    _builder.append(" *channel ,");
    _builder.append(type);
    _builder.append("* buffer, size_t size);");
    _builder.newLineIfNotEmpty();
    _builder.append("int read_non_blocking_");
    _builder.append(type);
    _builder.append("(circular_fifo_");
    _builder.append(type);
    _builder.append("* src,");
    _builder.append(type);
    _builder.append("* dst );");
    _builder.newLineIfNotEmpty();
    _builder.append("int read_blocking_");
    _builder.append(type);
    _builder.append("(circular_fifo_");
    _builder.append(type);
    _builder.append("* src,");
    _builder.append(type);
    _builder.append("* dst,spinlock *lock);");
    _builder.newLineIfNotEmpty();
    _builder.append("int write_non_blocking_");
    _builder.append(type);
    _builder.append("(circular_fifo_");
    _builder.append(type);
    _builder.append("* dst,");
    _builder.append(type);
    _builder.append(" src );");
    _builder.newLineIfNotEmpty();
    _builder.append("int write_blocking_");
    _builder.append(type);
    _builder.append("(circular_fifo_");
    _builder.append(type);
    _builder.append("* dst,");
    _builder.append(type);
    _builder.append(" src,spinlock *lock);\t");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    return _builder.toString();
  }
  
  private int getMaximumElems(final Vertex typeVertex) {
    int maximumElems = 0;
    VertexProperty _get = typeVertex.getProperties().get("maximumElems");
    boolean _tripleNotEquals = (_get != null);
    if (_tripleNotEquals) {
      Object _unwrap = typeVertex.getProperties().get("maximumElems").unwrap();
      maximumElems = (((Integer) _unwrap)).intValue();
    } else {
      Object _unwrap_1 = typeVertex.getProperties().get("production").unwrap();
      maximumElems = (((Integer) _unwrap_1)).intValue();
    }
    return maximumElems;
  }
}
