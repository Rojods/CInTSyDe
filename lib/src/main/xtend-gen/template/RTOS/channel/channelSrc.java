package template.RTOS.channel;

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
public class channelSrc implements Template {
  public void create() {
    final Predicate<Vertex> _function = new Predicate<Vertex>() {
      public boolean test(final Vertex v) {
        return (v.hasTrait("impl::TokenizableDataBlock")).booleanValue();
      }
    };
    final Consumer<Vertex> _function_1 = new Consumer<Vertex>() {
      public void accept(final Vertex v) {
        Save.save(channelSrc.this.path(v), channelSrc.this.createSource(v));
      }
    };
    Global.model.vertexSet().stream().filter(_function).forEach(_function_1);
  }
  
  public String createSource(final Vertex vertex) {
    String _xblockexpression = null;
    {
      String name = vertex.getIdentifier();
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("#include \"../include/freertos_sdfchannel_");
      String _replace = name.replace("/", "_");
      _builder.append(_replace);
      _builder.append(".h\"");
      _builder.newLineIfNotEmpty();
      _builder.append("QueueHandle_t msg_queue_");
      _builder.append(name);
      _builder.append(";");
      _builder.newLineIfNotEmpty();
      _builder.append("int queue_length_");
      _builder.append(name);
      _builder.append(" = ");
      int _bufferSize = Query.getBufferSize(vertex);
      _builder.append(_bufferSize);
      _builder.append(";");
      _builder.newLineIfNotEmpty();
      _builder.append("long item_size_");
      _builder.append(name);
      _builder.append(" = ");
      long _tokenSize = Query.getTokenSize(vertex);
      _builder.append(_tokenSize);
      _builder.append(";");
      _builder.newLineIfNotEmpty();
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
  
  private String path(final Vertex vertex) {
    String _name = Name.name(vertex);
    String _plus = ((generator.root + "/source/freertos_sdfchannel_") + _name);
    return (_plus + ".c");
  }
}
