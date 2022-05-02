package template.baremetal;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.core.VertexTrait;
import generator.Generator;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.templateInterface.InitTemplate;

@FileTypeAnno(type = FileType.C_INCLUDE)
@SuppressWarnings("all")
public class CircularFIFOTemplateInc implements InitTemplate {
  private Set<VertexTrait> primitiveTraitSet;
  
  public CircularFIFOTemplateInc() {
    HashSet<VertexTrait> _hashSet = new HashSet<VertexTrait>();
    this.primitiveTraitSet = _hashSet;
    this.primitiveTraitSet.add(VertexTrait.TYPING_DATATYPES_FLOAT);
    this.primitiveTraitSet.add(VertexTrait.TYPING_DATATYPES_DOUBLE);
    this.primitiveTraitSet.add(VertexTrait.TYPING_DATATYPES_INTEGER);
  }
  
  public String create() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#ifndef CIRCULAR_FIFO_LIB_H_");
    _builder.newLine();
    _builder.append("#define CIRCULAR_FIFO_LIB_H_");
    _builder.newLine();
    _builder.append("#include \"datatype_definition.h\"");
    _builder.newLine();
    _builder.append("#include \"spinlock.h\"");
    _builder.newLine();
    _builder.newLine();
    {
      boolean _hasElements = false;
      for(final VertexTrait primitiveTrait : this.primitiveTraitSet) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate("", "");
        }
        String _primitiveChannelPrototype = this.primitiveChannelPrototype(primitiveTrait);
        _builder.append(_primitiveChannelPrototype);
        _builder.append("\t");
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
  
  public String primitiveChannelPrototype(final VertexTrait trait) {
    StringConcatenation _builder = new StringConcatenation();
    final Predicate<Vertex> _function = new Predicate<Vertex>() {
      public boolean test(final Vertex v) {
        return (v.hasTrait(trait)).booleanValue();
      }
    };
    Set<Vertex> typeSet = Generator.model.vertexSet().stream().filter(_function).collect(Collectors.<Vertex>toSet());
    _builder.newLineIfNotEmpty();
    {
      boolean _hasElements = false;
      for(final Vertex typeVertex : typeSet) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate("", "");
        }
        String type = typeVertex.getIdentifier();
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
        _builder.append("\t");
        _builder.newLine();
      }
      if (_hasElements) {
        _builder.append("");
      }
    }
    return _builder.toString();
  }
  
  public void addPrimitiveVertexTrait(final VertexTrait primitiveTrait) {
    this.primitiveTraitSet.add(primitiveTrait);
  }
}
