package template.RTOS.channel;

import forsyde.io.java.core.Vertex;
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel;
import generator.generator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.Template;
import utils.Global;
import utils.Name;
import utils.Save;

@SuppressWarnings("all")
public class channelInc implements Template {
  public void create() {
    final Predicate<Vertex> _function = new Predicate<Vertex>() {
      public boolean test(final Vertex v) {
        return (v.hasTrait("impl::TokenizableDataBlock")).booleanValue();
      }
    };
    final Consumer<Vertex> _function_1 = new Consumer<Vertex>() {
      public void accept(final Vertex v) {
        Save.save(channelInc.this.path(v), channelInc.this.createHeader(v));
      }
    };
    Global.model.vertexSet().stream().filter(_function).forEach(_function_1);
  }
  
  public String createHeader(final Vertex vertex) {
    String _xblockexpression = null;
    {
      final String name = Name.name(vertex);
      SDFChannel channel = SDFChannel.enforce(vertex);
      String tmp = name.toUpperCase();
      Long token_size = channel.getTokenSizeInBits();
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("#ifndef                   ");
      _builder.append(tmp);
      _builder.append("_FREERTOS_H_");
      _builder.newLineIfNotEmpty();
      _builder.append("#define                   ");
      _builder.append(tmp);
      _builder.append("_FREERTOS_H_");
      _builder.newLineIfNotEmpty();
      _builder.append("#include <stdlib.h>");
      _builder.newLine();
      _builder.append("#include <stdint.h>\t");
      _builder.newLine();
      _builder.append("#include <stdio.h> ");
      _builder.newLine();
      _builder.append("#include \"FreeRTOS.h\"");
      _builder.newLine();
      _builder.append("#include \"queue.h\"");
      _builder.newLine();
      _builder.append("#include \"semphr.h\"");
      _builder.newLine();
      _builder.append("#include \"timers.h\"");
      _builder.newLine();
      _builder.newLine();
      _builder.append("/*");
      _builder.newLine();
      _builder.append("define token ");
      _builder.newLine();
      _builder.append("*/");
      _builder.newLine();
      _builder.append("typedef ");
      {
        if (((token_size).longValue() == 8)) {
          _builder.append("uint8_t");
        } else {
          if (((token_size).longValue() == 16)) {
            _builder.append("uint16_t");
          } else {
            if (((token_size).longValue() == 32)) {
              _builder.append("uin32_t");
            } else {
              _builder.append(" uint32_t");
            }
          }
        }
      }
      _builder.append(" token_");
      _builder.append(name);
      _builder.append(" ;\t\t\t\t\t");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("#endif\t\t");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.newLine();
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
  
  private String path(final Vertex vertex) {
    String _name = Name.name(vertex);
    String _plus = ((generator.root + "/include/freertos_sdfchannel_") + _name);
    return (_plus + ".h");
  }
}
