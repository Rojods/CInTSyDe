package template.RTOS.actor;

import forsyde.io.java.core.Vertex;
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel;
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb;
import generator.generator;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.Template;
import utils.Global;
import utils.Name;
import utils.Save;

@SuppressWarnings("all")
public class actorInc implements Template {
  public void create() {
    final Predicate<Vertex> _function = new Predicate<Vertex>() {
      public boolean test(final Vertex v) {
        return (SDFComb.conforms(v)).booleanValue();
      }
    };
    Set<Vertex> set = Global.model.vertexSet().stream().filter(_function).collect(Collectors.<Vertex>toSet());
    for (final Vertex v : set) {
      Save.save(this.path(v), this.createHeader(v));
    }
  }
  
  public String createHeader(final Vertex vertex) {
    String _xblockexpression = null;
    {
      String name = Name.name(vertex);
      String tmp = name.toUpperCase();
      tmp = (tmp + "_H_");
      final Predicate<Vertex> _function = new Predicate<Vertex>() {
        public boolean test(final Vertex v) {
          return (SDFChannel.conforms(v)).booleanValue();
        }
      };
      final Function<Vertex, SDFChannel> _function_1 = new Function<Vertex, SDFChannel>() {
        public SDFChannel apply(final Vertex v) {
          return SDFChannel.enforce(v);
        }
      };
      Set<SDFChannel> sdfchannels = Global.model.vertexSet().stream().filter(_function).<SDFChannel>map(_function_1).collect(Collectors.<SDFChannel>toSet());
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("#ifndef  ");
      _builder.append(tmp);
      _builder.newLineIfNotEmpty();
      _builder.append("#define ");
      _builder.append(tmp);
      _builder.newLineIfNotEmpty();
      _builder.append("#include \"FreeRTOS.h\"");
      _builder.newLine();
      _builder.append("#include \"task.h\"");
      _builder.newLine();
      _builder.append("#include \"semphr.h\"");
      _builder.newLine();
      _builder.append("#include \"timers.h\"");
      _builder.newLine();
      _builder.append("void task_");
      _builder.append(name);
      _builder.append("(void* pdata);");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("void timer_");
      _builder.append(name);
      _builder.append("_callback(TimerHandle_t xTimer);");
      _builder.newLineIfNotEmpty();
      _builder.append("#endif\t\t");
      _builder.newLine();
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
  
  private String path(final Vertex vertex) {
    String _name = Name.name(vertex);
    String _plus = ((generator.root + "/include/freertos_sdfcomb_") + _name);
    return (_plus + ".h");
  }
}
