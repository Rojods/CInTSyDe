package template.rtos;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.Vertex;
import generator.Generator;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.templateInterface.InitTemplate;
import utils.Query;

@FileTypeAnno(type = FileType.C_SOURCE)
@SuppressWarnings("all")
public class StartTaskTemplateSrcRTOS implements InitTemplate {
  public String create() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#include \"../inc/config.h\"");
    _builder.newLine();
    _builder.append("/*");
    _builder.newLine();
    _builder.append("=================================================");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("Define Task Stack");
    _builder.newLine();
    _builder.append("=================================================");
    _builder.newLine();
    _builder.append("*/");
    _builder.newLine();
    _builder.append("StackType_t task_StartTask_stk[TASK_STACKSIZE]; ");
    _builder.newLine();
    _builder.append("StaticTask_t tcb_start;");
    _builder.newLine();
    _builder.append("/*");
    _builder.newLine();
    _builder.append("=================================================");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("Declare Extern Task Stack");
    _builder.newLine();
    _builder.append("=================================================");
    _builder.newLine();
    _builder.append("*/\t\t");
    _builder.newLine();
    _builder.append("/*");
    _builder.newLine();
    _builder.append("=================================================");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("Declare Extern Message Queue");
    _builder.newLine();
    _builder.append("=================================================");
    _builder.newLine();
    _builder.append("*/\t");
    _builder.newLine();
    _builder.append("/*");
    _builder.newLine();
    _builder.append("=================================================");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("Declare Extern Software Timer and Semaphore");
    _builder.newLine();
    _builder.append("=================================================");
    _builder.newLine();
    _builder.append("*/\t");
    _builder.newLine();
    _builder.append("void init_msg_queue();");
    _builder.newLine();
    _builder.append("void init_soft_timer();");
    _builder.newLine();
    _builder.append("void init_semaphore();");
    _builder.newLine();
    _builder.append("void init_actor_task();\t\t");
    _builder.newLine();
    _builder.append("void timer_start(){");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* Initilize Message Queue     */");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("init_msg_queue();");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* Initilize Software Timer    */");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("init_soft_timer();");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* Initilize Timer\'s Semaphore */");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("init_semaphore();");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* Initilize Actor Tasks       */");
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
    _builder.append("/* Delete start task           */");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("vTaskDelete(NULL); ");
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
    _builder.append("}");
    _builder.newLine();
    _builder.append("static void init_soft_timer(){");
    _builder.newLine();
    {
      boolean _hasElements_2 = false;
      for(final Vertex actor : Generator.sdfcombSet) {
        if (!_hasElements_2) {
          _hasElements_2 = true;
        } else {
          _builder.appendImmediate("", "");
        }
        String name = actor.getIdentifier();
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
        int _wCET = Query.getWCET(actor, Generator.model);
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
      for(final Vertex actor_1 : Generator.sdfcombSet) {
        if (!_hasElements_3) {
          _hasElements_3 = true;
        } else {
          _builder.appendImmediate("", "");
        }
        String name_1 = actor_1.getIdentifier();
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
        _builder.append("task_sem_");
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
      for(final Vertex actor_2 : Generator.sdfcombSet) {
        if (!_hasElements_4) {
          _hasElements_4 = true;
        } else {
          _builder.appendImmediate("", "\t");
        }
        _builder.append("\t");
        String name_2 = actor_2.getIdentifier();
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
      for(final Vertex actor_3 : Generator.sdfcombSet) {
        if (!_hasElements_5) {
          _hasElements_5 = true;
        } else {
          _builder.appendImmediate("", "\t");
        }
        _builder.append("\t");
        String name_3 = actor_3.getIdentifier();
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
    return _builder.toString();
  }
  
  public String getFileName() {
    return "start_task";
  }
}
