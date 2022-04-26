package template.RTOS.subsystem;

import forsyde.io.java.core.Vertex;
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel;
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb;
import generator.generator;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.Template;
import utils.Global;
import utils.Save;

@SuppressWarnings("all")
public class StartTaskInc implements Template {
  public void create() {
    Save.save(this.path(), this.createHeader());
  }
  
  public String createHeader() {
    String _xblockexpression = null;
    {
      final Predicate<Vertex> _function = new Predicate<Vertex>() {
        public boolean test(final Vertex v) {
          return (SDFChannel.conforms(v)).booleanValue();
        }
      };
      Set<Vertex> sdfChannelSet = Global.model.vertexSet().stream().filter(_function).collect(Collectors.<Vertex>toSet());
      final Predicate<Vertex> _function_1 = new Predicate<Vertex>() {
        public boolean test(final Vertex v) {
          return (SDFComb.conforms(v)).booleanValue();
        }
      };
      Set<Vertex> sdfCombSet = Global.model.vertexSet().stream().filter(_function_1).collect(Collectors.<Vertex>toSet());
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("#ifndef STARTTASK_H_");
      _builder.newLine();
      _builder.append("#define STARTTASK_H_");
      _builder.newLine();
      _builder.newLine();
      _builder.append("#include \"FreeRTOS.h\"");
      _builder.newLine();
      _builder.append("#include \"task.h\"");
      _builder.newLine();
      _builder.append("#include \"semphr.h\"");
      _builder.newLine();
      _builder.append("#include \"timers.h\"");
      _builder.newLine();
      _builder.newLine();
      _builder.append("void StartTask(void* pdata);\t");
      _builder.newLine();
      _builder.append("#endif");
      _builder.newLine();
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
  
  private String path() {
    return (generator.root + "/include/freertos_StartTask.h");
  }
}
