package template.rtos;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.typed.viewers.moc.sdf.SDFActor;
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel;
import forsyde.io.java.typed.viewers.values.IntegerValue;
import generator.Generator;
import java.util.HashMap;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.templateInterface.InitTemplate;
import utils.Query;

@FileTypeAnno(type = FileType.C_SOURCE)
@SuppressWarnings("all")
public class StartTaskTemplateSrcRTOS implements InitTemplate {
  @Override
  public String savePath() {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
  
  @Override
  public String create() {
    String _xblockexpression = null;
    {
      ForSyDeSystemGraph model = Generator.model;
      final Predicate<Vertex> _function = (Vertex v) -> {
        return (IntegerValue.conforms(v)).booleanValue();
      };
      final Function<Vertex, IntegerValue> _function_1 = (Vertex v) -> {
        return IntegerValue.safeCast(v).get();
      };
      Set<IntegerValue> integerValues = model.vertexSet().stream().filter(_function).<IntegerValue>map(_function_1).collect(Collectors.<IntegerValue>toSet());
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("#include \"../inc/config.h\"");
      _builder.newLine();
      _builder.append("#include \"../inc/datatype_definition.h\"");
      _builder.newLine();
      _builder.append("#include \"FreeRTOS.h\"");
      _builder.newLine();
      _builder.append("#include \"semphr.h\"");
      _builder.newLine();
      _builder.append("#include \"timers.h\"\t");
      _builder.newLine();
      _builder.append("#include \"queue.h\"");
      _builder.newLine();
      {
        final Predicate<Vertex> _function_2 = (Vertex v) -> {
          return (SDFActor.conforms(v)).booleanValue();
        };
        Set<Vertex> _collect = model.vertexSet().stream().filter(_function_2).collect(Collectors.<Vertex>toSet());
        for(final Vertex actor : _collect) {
          _builder.append("#include \"../inc/sdfcomb_");
          String _identifier = actor.getIdentifier();
          _builder.append(_identifier);
          _builder.append(".h\"");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.append("/*");
      _builder.newLine();
      _builder.append("=================================================");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("Define StartTask Stack");
      _builder.newLine();
      _builder.append("=================================================");
      _builder.newLine();
      _builder.append("*/");
      _builder.newLine();
      _builder.append("StackType_t task_StartTask_stk[STARTTASK_STACKSIZE]; ");
      _builder.newLine();
      _builder.append("StaticTask_t tcb_start;");
      _builder.newLine();
      _builder.append("/*");
      _builder.newLine();
      _builder.append("=================================================");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("Declare Extern Values");
      _builder.newLine();
      _builder.append("=================================================");
      _builder.newLine();
      _builder.append("*/\t\t");
      _builder.newLine();
      {
        for(final IntegerValue value : integerValues) {
          _builder.append("extern int ");
          String _identifier_1 = value.getIdentifier();
          _builder.append(_identifier_1);
          _builder.append(";");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.newLine();
      _builder.newLine();
      _builder.append("static void init_msg_queue();");
      _builder.newLine();
      _builder.append("static void init_soft_timer();");
      _builder.newLine();
      _builder.append("static void init_semaphore();");
      _builder.newLine();
      _builder.append("static void init_actor_task();");
      _builder.newLine();
      _builder.append("static void timer_start();");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.newLine();
      _builder.append("void init_subsystem(){");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("/* Initialize Message Queue     */");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("init_msg_queue();");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("/* Initialize Software Timer    */");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("init_soft_timer();");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("/* Initialize Timer\'s Semaphore */");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("init_semaphore();");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("/* Initialize Actor Tasks       */");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("init_actor_task();");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("/* Start Software Timer        */");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("timer_start();");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("/* Suspend All the Created Tasks */");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("//vTaskStartScheduler();");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("//vTaskDelete(NULL); ");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      _builder.append("static void init_msg_queue(){");
      _builder.newLine();
      {
        boolean _hasElements = false;
        for(final Vertex sdfchannel : Generator.sdfchannelSet) {
          if (!_hasElements) {
            _hasElements = true;
          } else {
            _builder.appendImmediate("", "\t");
          }
          _builder.append("\t");
          String sdfname = sdfchannel.getIdentifier();
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("/* channel ");
          _builder.append(sdfname, "\t");
          _builder.append(" */");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("extern QueueHandle_t msg_queue_");
          _builder.append(sdfname, "\t");
          _builder.append(";");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("extern int queue_length_");
          _builder.append(sdfname, "\t");
          _builder.append(";");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("extern long item_size_");
          _builder.append(sdfname, "\t");
          _builder.append(";");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.newLine();
        }
        if (_hasElements) {
          _builder.append("", "\t");
        }
      }
      {
        boolean _hasElements_1 = false;
        for(final Vertex sdfchannel_1 : Generator.sdfchannelSet) {
          if (!_hasElements_1) {
            _hasElements_1 = true;
          } else {
            _builder.appendImmediate("", "\t");
          }
          _builder.append("\t");
          String sdfname_1 = sdfchannel_1.getIdentifier();
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("msg_queue_");
          _builder.append(sdfname_1, "\t");
          _builder.append("=xQueueCreate(queue_length_");
          _builder.append(sdfname_1, "\t");
          _builder.append(",item_size_");
          _builder.append(sdfname_1, "\t");
          _builder.append(");");
          _builder.newLineIfNotEmpty();
        }
        if (_hasElements_1) {
          _builder.append("", "\t");
        }
      }
      _builder.append("\t");
      _builder.newLine();
      {
        for(final Vertex channel : Generator.sdfchannelSet) {
          SDFChannel sdfchannel_2 = SDFChannel.safeCast(channel).get();
          _builder.newLineIfNotEmpty();
          {
            if (((sdfchannel_2.getNumOfInitialTokens() != null) && ((sdfchannel_2.getNumOfInitialTokens()).intValue() > 0))) {
              Object _unwrap = sdfchannel_2.getProperties().get("__initialTokenValues_ordering__").unwrap();
              HashMap<String, Integer> b = ((HashMap<String, Integer>) _unwrap);
              _builder.append("\t\t\t\t\t");
              _builder.newLineIfNotEmpty();
              {
                Set<String> _keySet = b.keySet();
                for(final String k : _keySet) {
                  _builder.append("xQueueSend(msg_queue_");
                  String _identifier_2 = sdfchannel_2.getIdentifier();
                  _builder.append(_identifier_2);
                  _builder.append(",&");
                  _builder.append(k);
                  _builder.append(",portMAX_DELAY);");
                  _builder.newLineIfNotEmpty();
                }
              }
            }
          }
        }
      }
      _builder.append("}");
      _builder.newLine();
      _builder.append("static void init_soft_timer(){");
      _builder.newLine();
      {
        boolean _hasElements_2 = false;
        for(final Vertex actor_1 : Generator.sdfcombSet) {
          if (!_hasElements_2) {
            _hasElements_2 = true;
          } else {
            _builder.appendImmediate("", "");
          }
          String name = actor_1.getIdentifier();
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("/* actor ");
          _builder.append(name, "\t");
          _builder.append("*/");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("extern TimerHandle_t task_timer_");
          _builder.append(name, "\t");
          _builder.append(";");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("task_timer_");
          _builder.append(name, "\t");
          _builder.append("=xTimerCreate(");
          _builder.newLineIfNotEmpty();
          _builder.append("\t\t\t\t\t\t\t\t\t\t\t");
          _builder.append("\"timer_");
          _builder.append(name, "\t\t\t\t\t\t\t\t\t\t\t");
          _builder.append("\"");
          _builder.newLineIfNotEmpty();
          _builder.append("\t\t\t\t\t\t\t\t\t\t\t");
          _builder.append(", pdMS_TO_TICKS(");
          int _wCET = Query.getWCET(actor_1, Generator.model);
          _builder.append(_wCET, "\t\t\t\t\t\t\t\t\t\t\t");
          _builder.append(")");
          _builder.newLineIfNotEmpty();
          _builder.append("\t\t\t\t\t\t\t\t\t\t\t");
          _builder.append(", pdTRUE");
          _builder.newLine();
          _builder.append("\t\t\t\t\t\t\t\t\t\t\t");
          _builder.append(",0");
          _builder.newLine();
          _builder.append("\t\t\t\t\t\t\t\t\t\t\t");
          _builder.append(",timer_");
          _builder.append(name, "\t\t\t\t\t\t\t\t\t\t\t");
          _builder.append("_callback");
          _builder.newLineIfNotEmpty();
          _builder.append("\t\t\t\t\t\t\t\t\t\t\t");
          _builder.append(");");
          _builder.newLine();
          _builder.append("\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
          _builder.newLine();
        }
        if (_hasElements_2) {
          _builder.append("");
        }
      }
      _builder.append("}");
      _builder.newLine();
      _builder.append("static void init_semaphore(){");
      _builder.newLine();
      {
        boolean _hasElements_3 = false;
        for(final Vertex actor_2 : Generator.sdfcombSet) {
          if (!_hasElements_3) {
            _hasElements_3 = true;
          } else {
            _builder.appendImmediate("", "");
          }
          String name_1 = actor_2.getIdentifier();
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("/* actor ");
          _builder.append(name_1, "\t");
          _builder.append("*/");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("extern SemaphoreHandle_t timer_sem_");
          _builder.append(name_1, "\t");
          _builder.append(";");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("timer_sem_");
          _builder.append(name_1, "\t");
          _builder.append("=xSemaphoreCreateBinary();");
          _builder.newLineIfNotEmpty();
          _builder.append("\t\t\t\t");
          _builder.newLine();
        }
        if (_hasElements_3) {
          _builder.append("");
        }
      }
      _builder.append("}");
      _builder.newLine();
      _builder.append("static void init_actor_task(){");
      _builder.newLine();
      {
        boolean _hasElements_4 = false;
        for(final Vertex actor_3 : Generator.sdfcombSet) {
          if (!_hasElements_4) {
            _hasElements_4 = true;
          } else {
            _builder.appendImmediate("", "\t");
          }
          _builder.append("\t");
          String name_2 = actor_3.getIdentifier();
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("/* actor ");
          _builder.append(name_2, "\t");
          _builder.append("*/");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("extern StackType_t task_");
          _builder.append(name_2, "\t");
          _builder.append("_stk[];");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("extern StaticTask_t tcb_");
          _builder.append(name_2, "\t");
          _builder.append(";");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("xTaskCreateStatic(task_");
          _builder.append(name_2, "\t");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("\t\t\t\t\t");
          _builder.append(",\"");
          _builder.append(name_2, "\t\t\t\t\t\t");
          _builder.append("\"");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("\t\t\t\t\t");
          _builder.append(",");
          String _upperCase = name_2.toUpperCase();
          _builder.append(_upperCase, "\t\t\t\t\t\t");
          _builder.append("_STACKSIZE");
          _builder.newLineIfNotEmpty();
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
          _builder.append(name_2, "\t\t\t\t\t\t");
          _builder.append("_stk,");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("\t\t\t\t\t");
          _builder.append("&tcb_");
          _builder.append(name_2, "\t\t\t\t\t\t");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("\t\t\t\t\t");
          _builder.append(");\t");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("\t\t\t\t\t\t\t");
          _builder.newLine();
        }
        if (_hasElements_4) {
          _builder.append("", "\t");
        }
      }
      _builder.append("}");
      _builder.newLine();
      _builder.append("static void timer_start(){");
      _builder.newLine();
      {
        boolean _hasElements_5 = false;
        for(final Vertex actor_4 : Generator.sdfcombSet) {
          if (!_hasElements_5) {
            _hasElements_5 = true;
          } else {
            _builder.appendImmediate("", "\t");
          }
          _builder.append("\t");
          String name_3 = actor_4.getIdentifier();
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("extern TimerHandle_t task_timer_");
          _builder.append(name_3, "\t");
          _builder.append(";");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("xTimerStart(task_timer_");
          _builder.append(name_3, "\t");
          _builder.append(", portMAX_DELAY);\t\t");
          _builder.newLineIfNotEmpty();
        }
        if (_hasElements_5) {
          _builder.append("", "\t");
        }
      }
      _builder.append("}");
      _builder.newLine();
      _builder.newLine();
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
}
