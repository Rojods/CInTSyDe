package template.nonRTOS.fifo.circular.A;

import forsyde.io.java.core.Vertex;
import generator.generator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.Template;
import utils.Global;
import utils.Name;
import utils.Query;
import utils.Save;

@SuppressWarnings("all")
public class channelInc implements Template {
  public channelInc() {
  }
  
  public void create() {
    final Predicate<Vertex> _function = new Predicate<Vertex>() {
      public boolean test(final Vertex v) {
        return (v.hasTrait("impl::TokenizableDataBlock")).booleanValue();
      }
    };
    final Consumer<Vertex> _function_1 = new Consumer<Vertex>() {
      public void accept(final Vertex v) {
        Save.save(channelInc.this.path(v), channelInc.this.inc(v));
      }
    };
    Global.model.vertexSet().stream().filter(_function).forEach(_function_1);
  }
  
  public String inc(final Vertex vertex) {
    String _xblockexpression = null;
    {
      final String name = Name.name(vertex);
      String tmp = name.toUpperCase();
      long token_size = Query.getTokenSizeInBits(vertex);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("#ifndef                   ");
      _builder.append(tmp);
      _builder.append("_H_");
      _builder.newLineIfNotEmpty();
      _builder.append("#define                   ");
      _builder.append(tmp);
      _builder.append("_H_");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("#include <stdlib.h>");
      _builder.newLine();
      _builder.append("#include <stdint.h>\t");
      _builder.newLine();
      _builder.append("#include <stdio.h> ");
      _builder.newLine();
      _builder.append("#include \"spinlock.h\"");
      _builder.newLine();
      _builder.append("/*");
      _builder.newLine();
      _builder.append("define token ");
      _builder.newLine();
      _builder.append("*/");
      _builder.newLine();
      _builder.append("typedef ");
      {
        if ((token_size == 8)) {
          _builder.append("uint8_t");
        } else {
          if ((token_size == 16)) {
            _builder.append("uint16_t");
          } else {
            if ((token_size == 32)) {
              _builder.append("uint32_t");
            } else {
              if ((token_size == 64)) {
                _builder.append("uint64_t");
              } else {
                _builder.append(" uint64_t");
              }
            }
          }
        }
      }
      _builder.append(" token_");
      _builder.append(name);
      _builder.append(" ;\t");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("/*");
      _builder.newLine();
      _builder.append("this is a circular buffer for ");
      _builder.append(name);
      _builder.newLineIfNotEmpty();
      _builder.append("*/");
      _builder.newLine();
      _builder.append("typedef struct ");
      _builder.newLine();
      _builder.append("{");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("token_");
      _builder.append(name, "    ");
      _builder.append("* buffer;");
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("/*");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("front: the position of the begining");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("*/");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("size_t front;");
      _builder.newLine();
      _builder.append("    ");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("/*");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("rear: the position just after the last element");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("*/");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("size_t rear;");
      _builder.newLine();
      _builder.append("    ");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("/*");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("the size of this buffer");
      _builder.newLine();
      _builder.append("\t ");
      _builder.append("*/");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("size_t size;\t    ");
      _builder.newLine();
      _builder.append("}circularFIFO_");
      _builder.append(name);
      _builder.append(";");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("void init_circularFIFO_");
      _builder.append(name);
      _builder.append("(circularFIFO_");
      _builder.append(name);
      _builder.append("* channel ,token_");
      _builder.append(name);
      _builder.append("* buffer,size_t size);");
      _builder.newLineIfNotEmpty();
      _builder.append("int read_circularFIFO_non_blocking_");
      _builder.append(name);
      _builder.append("(circularFIFO_");
      _builder.append(name);
      _builder.append("* channel, token_");
      _builder.append(name);
      _builder.append("* data);");
      _builder.newLineIfNotEmpty();
      _builder.append("int write_circularFIFO_non_blocking_");
      _builder.append(name);
      _builder.append("(circularFIFO_");
      _builder.append(name);
      _builder.append("* channel, token_");
      _builder.append(name);
      _builder.append(" value);");
      _builder.newLineIfNotEmpty();
      _builder.append("int read_circularFIFO_blocking_");
      _builder.append(name);
      _builder.append("(circularFIFO_");
      _builder.append(name);
      _builder.append("* channel, token_");
      _builder.append(name);
      _builder.append("* data,spinlock* lock);");
      _builder.newLineIfNotEmpty();
      _builder.append("int write_circularFIFO_blocking_");
      _builder.append(name);
      _builder.append("(circularFIFO_");
      _builder.append(name);
      _builder.append("* channel, token_");
      _builder.append(name);
      _builder.append(" value,spinlock* lock);");
      _builder.newLineIfNotEmpty();
      _builder.append("\t\t\t");
      _builder.newLine();
      _builder.append("#endif\t\t");
      _builder.newLine();
      _builder.newLine();
      _builder.newLine();
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
  
  private String path(final Vertex vertex) {
    String _name = Name.name(vertex);
    String _plus = ((generator.root + "/inc/sdfchannel_") + _name);
    return (_plus + ".h");
  }
}
