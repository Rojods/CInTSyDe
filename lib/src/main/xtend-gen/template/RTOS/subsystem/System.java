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
public class System implements Template {
  public void create() {
    Save.save(this.pathsrc(), this.src());
    Save.save(this.pathinc(), this.inc());
  }
  
  public String src() {
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
      _builder.newLine();
      _builder.append("#include \"../include/system.h\"");
      _builder.newLine();
      _builder.append("#include \"../include/freertos_StartTask.h\"");
      _builder.newLine();
      _builder.append("#include \"../include/freertos_config.h\"");
      _builder.newLine();
      _builder.append("#include \"FreeRTOS.h\"");
      _builder.newLine();
      _builder.append("#include \"task.h\"");
      _builder.newLine();
      _builder.append("#include \"semphr.h\"");
      _builder.newLine();
      _builder.append("#include \"timers.h\"");
      _builder.newLine();
      _builder.append("/*******************");
      _builder.newLine();
      _builder.append("*\tStartTask stack*");
      _builder.newLine();
      _builder.append("********************/");
      _builder.newLine();
      _builder.append("extern StackType_t task_StartTask_stk[TASK_STACKSIZE]; ");
      _builder.newLine();
      _builder.append("extern StaticTask_t tcb_start;");
      _builder.newLine();
      _builder.newLine();
      _builder.append("int subsystem(){");
      _builder.newLine();
      _builder.newLine();
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("xTaskCreateStatic(StartTask,");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("\"init\",");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("TASK_STACKSIZE,");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("NULL,");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("configMAX_PRIORITIES-1,");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("task_StartTask_stk,");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("&tcb_start");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append(");");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("vTaskStartScheduler();");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("return 0;");
      _builder.newLine();
      _builder.append("}\t\t");
      _builder.newLine();
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
  
  private String pathsrc() {
    return (generator.root + "/source/system.c");
  }
  
  public String inc() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("#ifndef SYSTEM_FREERTOS_H_");
    _builder.newLine();
    _builder.append("#define SYSTEM_FREERTOS_H_");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("int subsystem();");
    _builder.newLine();
    _builder.append("#endif\t\t");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    return _builder.toString();
  }
  
  private String pathinc() {
    return (generator.root + "/include/system.h");
  }
}
