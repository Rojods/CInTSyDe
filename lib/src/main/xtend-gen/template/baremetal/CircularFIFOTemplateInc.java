package template.baremetal;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.core.VertexProperty;
import forsyde.io.java.core.VertexTrait;
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
public class CircularFIFOTemplateInc implements InitTemplate {
  private Set<VertexTrait> primitiveTraitSet;
  
  private Set<Vertex> typeVertexSet;
  
  public CircularFIFOTemplateInc() {
    final ForSyDeSystemGraph model = Generator.model;
    final Predicate<Vertex> _function = new Predicate<Vertex>() {
      public boolean test(final Vertex v) {
        return (SDFChannel.conforms(v)).booleanValue();
      }
    };
    final Function<Vertex, String> _function_1 = new Function<Vertex, String>() {
      public String apply(final Vertex v) {
        return Query.findSDFChannelDataType(model, v);
      }
    };
    final Function<String, Vertex> _function_2 = new Function<String, Vertex>() {
      public Vertex apply(final String s) {
        return Query.findVertexByName(model, s);
      }
    };
    this.typeVertexSet = model.vertexSet().stream().filter(_function).<String>map(_function_1).<Vertex>map(_function_2).collect(Collectors.<Vertex>toSet());
  }
  
  public String create() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#ifndef CIRCULAR_FIFO_LIB_H_");
    _builder.newLine();
    _builder.append("#define CIRCULAR_FIFO_LIB_H_");
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
    _builder.append("#include \"spinlock.h\"\t\t\t");
    _builder.newLine();
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
    _builder.append("#endif");
    _builder.newLine();
    return _builder.toString();
  }
  
  public String getFileName() {
    return "circular_fifo_lib";
  }
  
  public String foo(final Vertex v) {
    StringConcatenation _builder = new StringConcatenation();
    final String type = v.getIdentifier();
    _builder.newLineIfNotEmpty();
    {
      Boolean _hasTrait = v.hasTrait(VertexTrait.TYPING_DATATYPES_ARRAY);
      boolean _not = (!(_hasTrait).booleanValue());
      if (_not) {
        _builder.append("/*");
        _builder.newLine();
        _builder.append("=============================================================");
        _builder.newLine();
        _builder.append("\t\t\t\t");
        _builder.append(type, "\t\t\t\t");
        _builder.append(" Prototype");
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
        _builder.append("* channel,");
        _builder.append(type);
        _builder.append("* dst );");
        _builder.newLineIfNotEmpty();
        _builder.append("int read_blocking_");
        _builder.append(type);
        _builder.append("(circular_fifo_");
        _builder.append(type);
        _builder.append("* ptr,");
        _builder.append(type);
        _builder.append("* dst,spinlock *lock);");
        _builder.newLineIfNotEmpty();
        _builder.append("int write_non_blocking_");
        _builder.append(type);
        _builder.append("(circular_fifo_");
        _builder.append(type);
        _builder.append("* ptr,");
        _builder.append(type);
        _builder.append("* src );");
        _builder.newLineIfNotEmpty();
        _builder.append("int write_blocking_");
        _builder.append(type);
        _builder.append("(circular_fifo_");
        _builder.append(type);
        _builder.append("* ptr,");
        _builder.append(type);
        _builder.append("* src,spinlock *lock);\t");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t\t");
        _builder.newLine();
      } else {
        int maximumElems = this.help(v);
        _builder.newLineIfNotEmpty();
        {
          if ((maximumElems > 0)) {
            String innerType = Query.getInnerType(Generator.model, v);
            _builder.newLineIfNotEmpty();
            _builder.append("/*");
            _builder.newLine();
            _builder.append("=============================================================");
            _builder.newLine();
            _builder.append("\t\t\t\t");
            _builder.append(type, "\t\t\t\t");
            _builder.append(" Prototype");
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
            _builder.append("* channel,");
            _builder.append(type);
            _builder.append("* dst );");
            _builder.newLineIfNotEmpty();
            _builder.append("int read_blocking_");
            _builder.append(type);
            _builder.append("(circular_fifo_");
            _builder.append(type);
            _builder.append("* ptr,");
            _builder.append(type);
            _builder.append("* dst,spinlock *lock);");
            _builder.newLineIfNotEmpty();
            _builder.append("int write_non_blocking_");
            _builder.append(type);
            _builder.append("(circular_fifo_");
            _builder.append(type);
            _builder.append("* ptr,");
            _builder.append(type);
            _builder.append("* src );");
            _builder.newLineIfNotEmpty();
            _builder.append("int write_blocking_");
            _builder.append(type);
            _builder.append("(circular_fifo_");
            _builder.append(type);
            _builder.append("* ptr,");
            _builder.append(type);
            _builder.append("* src,spinlock *lock);\t\t\t\t");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    return _builder.toString();
  }
  
  private int help(final Vertex typeVertex) {
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
