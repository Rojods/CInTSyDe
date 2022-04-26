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
import utils.Name;
import utils.Query;
import utils.Save;

@SuppressWarnings("all")
public class StartTaskSrc implements Template {
  public void create() {
    Save.save(this.path(), this.createSource());
  }
  
  public String createSource() {
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
      final Predicate<Vertex> _function_2 = new Predicate<Vertex>() {
        public boolean test(final Vertex v) {
          return (v.hasTrait("impl::TokenizableDataBlock")).booleanValue();
        }
      };
      final Predicate<Vertex> _function_3 = new Predicate<Vertex>() {
        public boolean test(final Vertex v) {
          Boolean _hasTrait = v.hasTrait("moc::sdf::SDFChannel");
          return (!(_hasTrait).booleanValue());
        }
      };
      Set<Vertex> system_in_out = Global.model.vertexSet().stream().filter(_function_2).filter(_function_3).collect(Collectors.<Vertex>toSet());
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("#include \"../include/freertos_StartTask.h\"");
      _builder.newLine();
      {
        boolean _hasElements = false;
        for(final Vertex channel : sdfChannelSet) {
          if (!_hasElements) {
            _hasElements = true;
          } else {
            _builder.appendImmediate("", "");
          }
          _builder.append("#include \"../include/freertos_sdfchannel_");
          String _name = Name.name(channel);
          _builder.append(_name);
          _builder.append(".h\"");
          _builder.newLineIfNotEmpty();
        }
        if (_hasElements) {
          _builder.append("\n");
        }
      }
      {
        boolean _hasElements_1 = false;
        for(final Vertex sdf : sdfCombSet) {
          if (!_hasElements_1) {
            _hasElements_1 = true;
          } else {
            _builder.appendImmediate("", "");
          }
          _builder.append("#include \"../include/freertos_sdfcomb_");
          String _name_1 = Name.name(sdf);
          _builder.append(_name_1);
          _builder.append(".h\"");
          _builder.newLineIfNotEmpty();
        }
        if (_hasElements_1) {
          _builder.append("\n");
        }
      }
      _builder.append("#include \"../include/freertos_config.h\"");
      _builder.newLine();
      _builder.append("/*******************");
      _builder.newLine();
      _builder.append("*\tTask stack     *");
      _builder.newLine();
      _builder.append("********************/");
      _builder.newLine();
      _builder.append("StackType_t task_StartTask_stk[TASK_STACKSIZE]; ");
      _builder.newLine();
      _builder.append("StaticTask_t tcb_start;");
      _builder.newLine();
      {
        boolean _hasElements_2 = false;
        for(final Vertex sdf_1 : sdfCombSet) {
          if (!_hasElements_2) {
            _hasElements_2 = true;
          } else {
            _builder.appendImmediate("", "");
          }
          _builder.append("extern StackType_t task_");
          String _name_2 = Name.name(sdf_1);
          _builder.append(_name_2);
          _builder.append("_stk[TASK_STACKSIZE];");
          _builder.newLineIfNotEmpty();
          _builder.append("extern StaticTask_t tcb_");
          String _name_3 = Name.name(sdf_1);
          _builder.append(_name_3);
          _builder.append(";");
          _builder.newLineIfNotEmpty();
        }
        if (_hasElements_2) {
          _builder.append("\n");
        }
      }
      _builder.append("/*******************");
      _builder.newLine();
      _builder.append("*\t Message Queue *");
      _builder.newLine();
      _builder.append("********************/");
      _builder.newLine();
      {
        boolean _hasElements_3 = false;
        for(final Vertex channel_1 : sdfChannelSet) {
          if (!_hasElements_3) {
            _hasElements_3 = true;
          } else {
            _builder.appendImmediate("\n", "");
          }
          _builder.append("extern int queue_length_");
          String _name_4 = Name.name(channel_1);
          _builder.append(_name_4);
          _builder.append(";");
          _builder.newLineIfNotEmpty();
          _builder.append("extern long item_size_");
          String _name_5 = Name.name(channel_1);
          _builder.append(_name_5);
          _builder.append(";");
          _builder.newLineIfNotEmpty();
          _builder.append("extern QueueHandle_t msg_queue_");
          String _name_6 = Name.name(channel_1);
          _builder.append(_name_6);
          _builder.append(";");
          _builder.newLineIfNotEmpty();
        }
        if (_hasElements_3) {
          _builder.append("\n");
        }
      }
      {
        boolean _hasElements_4 = false;
        for(final Vertex e : system_in_out) {
          if (!_hasElements_4) {
            _hasElements_4 = true;
          } else {
            _builder.appendImmediate("", "");
          }
          _builder.append("extern QueueHandle_t msg_queue_");
          String _name_7 = Name.name(e);
          _builder.append(_name_7);
          _builder.append(";");
          _builder.newLineIfNotEmpty();
        }
        if (_hasElements_4) {
          _builder.append("\n");
        }
      }
      _builder.newLine();
      _builder.append("/**************************");
      _builder.newLine();
      _builder.append("*\t\t\tSoft Timer and semaphore");
      _builder.newLine();
      _builder.append("***************************/");
      _builder.newLine();
      {
        boolean _hasElements_5 = false;
        for(final Vertex sdf_2 : sdfCombSet) {
          if (!_hasElements_5) {
            _hasElements_5 = true;
          } else {
            _builder.appendImmediate("", "");
          }
          _builder.append("extern TimerHandle_t task_timer_");
          String _name_8 = Name.name(sdf_2);
          _builder.append(_name_8);
          _builder.append(";");
          _builder.newLineIfNotEmpty();
        }
        if (_hasElements_5) {
          _builder.append("\n");
        }
      }
      {
        boolean _hasElements_6 = false;
        for(final Vertex sdf_3 : sdfCombSet) {
          if (!_hasElements_6) {
            _hasElements_6 = true;
          } else {
            _builder.appendImmediate("", "");
          }
          _builder.append("extern  SemaphoreHandle_t task_sem_");
          String _name_9 = Name.name(sdf_3);
          _builder.append(_name_9);
          _builder.append(";");
          _builder.newLineIfNotEmpty();
        }
        if (_hasElements_6) {
          _builder.append("\n");
        }
      }
      _builder.newLine();
      _builder.newLine();
      _builder.append("void StartTask(void* pdata){");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("/*");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("Message Queue creation");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("*/");
      _builder.newLine();
      {
        boolean _hasElements_7 = false;
        for(final Vertex channel_2 : sdfChannelSet) {
          if (!_hasElements_7) {
            _hasElements_7 = true;
          } else {
            _builder.appendImmediate("", "\t");
          }
          _builder.append("\t");
          String name = Name.name(channel_2);
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("msg_queue_");
          _builder.append(name, "\t");
          _builder.append("=xQueueCreate(queue_length_");
          _builder.append(name, "\t");
          _builder.append(",item_size_");
          _builder.append(name, "\t");
          _builder.append(");");
          _builder.newLineIfNotEmpty();
        }
        if (_hasElements_7) {
          _builder.append("\n", "\t");
        }
      }
      _builder.append("\t");
      _builder.append("/*");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("Soft Timer amd Semephore Initilization");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("*/\t\t\t\t");
      _builder.newLine();
      {
        boolean _hasElements_8 = false;
        for(final Vertex sdf_4 : sdfCombSet) {
          if (!_hasElements_8) {
            _hasElements_8 = true;
          } else {
            _builder.appendImmediate("", "\t");
          }
          _builder.append("\t");
          _builder.append("task_timer_");
          String _name_10 = Name.name(sdf_4);
          _builder.append(_name_10, "\t");
          _builder.append("=xTimerCreate(");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("\t\t\t\t\t\t\t\t\t\t");
          _builder.append("\"timer_");
          String _name_11 = Name.name(sdf_4);
          _builder.append(_name_11, "\t\t\t\t\t\t\t\t\t\t\t");
          _builder.append("\"");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("\t\t\t\t\t\t\t\t\t\t");
          _builder.append(", pdMS_TO_TICKS(");
          long _wCET = Query.getWCET(sdf_4, Global.model);
          _builder.append(_wCET, "\t\t\t\t\t\t\t\t\t\t\t");
          _builder.append(")");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("\t\t\t\t\t\t\t\t\t\t");
          _builder.append(", pdTRUE");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("\t\t\t\t\t\t\t\t\t\t");
          _builder.append(",0");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("\t\t\t\t\t\t\t\t\t\t");
          _builder.append(",timer_");
          String _name_12 = Name.name(sdf_4);
          _builder.append(_name_12, "\t\t\t\t\t\t\t\t\t\t\t");
          _builder.append("_callback");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("\t\t\t\t\t\t\t\t\t\t");
          _builder.append(");");
          _builder.newLine();
          _builder.append("\t");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("task_sem_");
          String _name_13 = Name.name(sdf_4);
          _builder.append(_name_13, "\t");
          _builder.append("=xSemaphoreCreateBinary();");
          _builder.newLineIfNotEmpty();
        }
        if (_hasElements_8) {
          _builder.append("\n", "\t");
        }
      }
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("/*");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("Tasks Initilization");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("*/");
      _builder.newLine();
      {
        boolean _hasElements_9 = false;
        for(final Vertex sdf_5 : sdfCombSet) {
          if (!_hasElements_9) {
            _hasElements_9 = true;
          } else {
            _builder.appendImmediate("\n", "\t");
          }
          _builder.append("\t");
          _builder.append("xTaskCreateStatic(task_");
          String _name_14 = Name.name(sdf_5);
          _builder.append(_name_14, "\t");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("\t\t\t\t\t");
          _builder.append(",\"");
          String _name_15 = Name.name(sdf_5);
          _builder.append(_name_15, "\t\t\t\t\t\t");
          _builder.append("\"");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("\t\t\t\t\t");
          _builder.append(",TASK_STACKSIZE");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("\t\t\t\t\t");
          _builder.append(",NULL");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("\t\t\t\t\t");
          _builder.append(",configMAX_PRIORITIES-2");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("\t\t\t\t\t");
          _builder.append(",task_");
          String _name_16 = Name.name(sdf_5);
          _builder.append(_name_16, "\t\t\t\t\t\t");
          _builder.append("_stk,");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("\t\t\t\t\t");
          _builder.append("&tcb_");
          String _name_17 = Name.name(sdf_5);
          _builder.append(_name_17, "\t\t\t\t\t\t");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("\t\t\t\t\t");
          _builder.append(");");
          _builder.newLine();
        }
        if (_hasElements_9) {
          _builder.append("\n", "\t");
        }
      }
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("/*");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("Start the soft timer");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("*/");
      _builder.newLine();
      {
        boolean _hasElements_10 = false;
        for(final Vertex sdf_6 : sdfCombSet) {
          if (!_hasElements_10) {
            _hasElements_10 = true;
          } else {
            _builder.appendImmediate("", "\t");
          }
          _builder.append("\t");
          _builder.append("xTimerStart(task_timer_");
          String _name_18 = Name.name(sdf_6);
          _builder.append(_name_18, "\t");
          _builder.append(", portMAX_DELAY);");
          _builder.newLineIfNotEmpty();
        }
        if (_hasElements_10) {
          _builder.append("\n", "\t");
        }
      }
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("vTaskDelete(NULL); ");
      _builder.newLine();
      _builder.append("}\t\t");
      _builder.newLine();
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
  
  private String path() {
    return (generator.root + "/source/freertos_StartTask.c");
  }
}
