package template.RTOS.actor;

import forsyde.io.java.core.EdgeInfo;
import forsyde.io.java.core.EdgeTrait;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel;
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb;
import generator.generator;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.Template;
import template.nonRTOS.actor.actorhelp;
import utils.Global;
import utils.Name;
import utils.Query;
import utils.Save;

@SuppressWarnings("all")
public class actorSrc implements Template {
  public void create() {
    final Predicate<Vertex> _function = new Predicate<Vertex>() {
      public boolean test(final Vertex v) {
        return (SDFComb.conforms(v)).booleanValue();
      }
    };
    Set<Vertex> set = Global.model.vertexSet().stream().filter(_function).collect(Collectors.<Vertex>toSet());
    for (final Vertex v : set) {
      Save.save(this.path(v), this.createSource(v));
    }
  }
  
  public String createSource(final Vertex vertex) {
    String _xblockexpression = null;
    {
      final Predicate<EdgeInfo> _function = new Predicate<EdgeInfo>() {
        public boolean test(final EdgeInfo edgeinfo) {
          return (edgeinfo.hasTrait(EdgeTrait.MOC_SDF_SDFDATAEDGE) || edgeinfo.hasTrait(EdgeTrait.IMPL_DATAMOVEMENT));
        }
      };
      final Function<EdgeInfo, String> _function_1 = new Function<EdgeInfo, String>() {
        public String apply(final EdgeInfo e) {
          return e.getSourcePort().get();
        }
      };
      Set<String> out = Global.model.outgoingEdgesOf(vertex).stream().filter(_function).<String>map(_function_1).collect(Collectors.<String>toSet());
      final Predicate<EdgeInfo> _function_2 = new Predicate<EdgeInfo>() {
        public boolean test(final EdgeInfo edgeinfo) {
          return (edgeinfo.hasTrait(EdgeTrait.MOC_SDF_SDFDATAEDGE) || edgeinfo.hasTrait(EdgeTrait.IMPL_DATAMOVEMENT));
        }
      };
      final Function<EdgeInfo, String> _function_3 = new Function<EdgeInfo, String>() {
        public String apply(final EdgeInfo e) {
          return e.getTargetPort().get();
        }
      };
      Set<String> in = Global.model.incomingEdgesOf(vertex).stream().filter(_function_2).<String>map(_function_3).collect(Collectors.<String>toSet());
      TreeSet<String> inputPorts = new TreeSet<String>(in);
      TreeSet<String> outputPorts = new TreeSet<String>(out);
      String name = Name.name(vertex);
      final Predicate<Vertex> _function_4 = new Predicate<Vertex>() {
        public boolean test(final Vertex v) {
          return (SDFChannel.conforms(v)).booleanValue();
        }
      };
      final Function<Vertex, SDFChannel> _function_5 = new Function<Vertex, SDFChannel>() {
        public SDFChannel apply(final Vertex v) {
          return SDFChannel.enforce(v);
        }
      };
      Set<SDFChannel> sdfchannels = Global.model.vertexSet().stream().filter(_function_4).<SDFChannel>map(_function_5).collect(Collectors.<SDFChannel>toSet());
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("#include <stdlib.h>");
      _builder.newLine();
      _builder.append("#include <stdio.h>");
      _builder.newLine();
      _builder.append("#include \"../include/freertos_sdfcomb_");
      _builder.append(name);
      _builder.append(".h\"");
      _builder.newLineIfNotEmpty();
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
      {
        boolean _hasElements = false;
        for(final SDFChannel ch : sdfchannels) {
          if (!_hasElements) {
            _hasElements = true;
          } else {
            _builder.appendImmediate(" \n", "");
          }
          _builder.append("#include \"../include/freertos_sdfchannel_");
          String _name = Name.name(ch);
          _builder.append(_name);
          _builder.append(".h\"");
          _builder.newLineIfNotEmpty();
        }
        if (_hasElements) {
          _builder.append("\n");
        }
      }
      _builder.append("/*");
      _builder.newLine();
      _builder.append("* Message Queue");
      _builder.newLine();
      _builder.append("*/");
      _builder.newLine();
      {
        boolean _hasElements_1 = false;
        for(final String port : inputPorts) {
          if (!_hasElements_1) {
            _hasElements_1 = true;
          } else {
            _builder.appendImmediate("", "");
          }
          _builder.append("extern QueueHandle_t msg_queue_");
          String _channelName = Query.getChannelName(vertex, port, Global.model);
          _builder.append(_channelName);
          _builder.append(";");
          _builder.newLineIfNotEmpty();
        }
        if (_hasElements_1) {
          _builder.append("");
        }
      }
      {
        boolean _hasElements_2 = false;
        for(final String port_1 : outputPorts) {
          if (!_hasElements_2) {
            _hasElements_2 = true;
          } else {
            _builder.appendImmediate("", "");
          }
          _builder.append("extern QueueHandle_t msg_queue_");
          String _channelName_1 = Query.getChannelName(vertex, port_1, Global.model);
          _builder.append(_channelName_1);
          _builder.append(";");
          _builder.newLineIfNotEmpty();
        }
        if (_hasElements_2) {
          _builder.append("");
        }
      }
      _builder.newLine();
      _builder.newLine();
      _builder.append("/*");
      _builder.newLine();
      _builder.append("* Task Stack");
      _builder.newLine();
      _builder.append("*/");
      _builder.newLine();
      _builder.append("StackType_t task_");
      _builder.append(name);
      _builder.append("_stk[TASK_STACKSIZE];");
      _builder.newLineIfNotEmpty();
      _builder.append("StaticTask_t tcb_");
      _builder.append(name);
      _builder.append(";");
      _builder.newLineIfNotEmpty();
      _builder.append("/*");
      _builder.newLine();
      _builder.append("* Soft Timer and Semaphore");
      _builder.newLine();
      _builder.append("*/");
      _builder.newLine();
      _builder.append("SemaphoreHandle_t task_sem_");
      _builder.append(name);
      _builder.append(";");
      _builder.newLineIfNotEmpty();
      _builder.append("TimerHandle_t task_timer_");
      _builder.append(name);
      _builder.append(";");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      {
        boolean _hasElements_3 = false;
        for(final String port_2 : inputPorts) {
          if (!_hasElements_3) {
            _hasElements_3 = true;
          } else {
            _builder.appendImmediate(" \n", "");
          }
          _builder.append("inline static void read_channel_in_");
          _builder.append(port_2);
          _builder.append("(QueueHandle_t src_msg_queue, size_t num, token_");
          String _sDFChannelName = Query.getSDFChannelName(vertex, port_2, Global.model);
          _builder.append(_sDFChannelName);
          _builder.append("  dst[]){");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("for(size_t i=0;i <num;++i){");
          _builder.newLine();
          _builder.append("\t\t");
          _builder.append("// portMAX_DELAY: INCLUDE_vTaskSuspend is set to 1 in FreeRTOSConfig.h.");
          _builder.newLine();
          _builder.append("\t\t");
          _builder.append("// block forever");
          _builder.newLine();
          _builder.append("\t\t");
          _builder.append("xQueueReceive(src_msg_queue,dst+i, portMAX_DELAY);\t\t");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("}");
          _builder.newLine();
          _builder.append("}");
          _builder.newLine();
        }
        if (_hasElements_3) {
          _builder.append("\n");
        }
      }
      _builder.newLine();
      {
        boolean _hasElements_4 = false;
        for(final String port_3 : outputPorts) {
          if (!_hasElements_4) {
            _hasElements_4 = true;
          } else {
            _builder.appendImmediate(" \n", "");
          }
          _builder.append("inline static void write_channel_in_");
          _builder.append(port_3);
          _builder.append("(token_");
          String _sDFChannelName_1 = Query.getSDFChannelName(vertex, port_3, Global.model);
          _builder.append(_sDFChannelName_1);
          _builder.append(" src[],size_t num,QueueHandle_t dst_msg_queue){");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("for(size_t i=0 ; i < num ;++i){");
          _builder.newLine();
          _builder.append("\t\t");
          _builder.append("// portMAX_DELAY: INCLUDE_vTaskSuspend is set to 1 in FreeRTOSConfig.h.");
          _builder.newLine();
          _builder.append("\t\t");
          _builder.append("// block forever");
          _builder.newLine();
          _builder.append("\t\t");
          _builder.append("BaseType_t ret=\txQueueSend(dst_msg_queue,src+i,portMAX_DELAY);");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("}");
          _builder.newLine();
          _builder.append("}");
          _builder.newLine();
        }
        if (_hasElements_4) {
          _builder.append("\n");
        }
      }
      _builder.append("void timer_");
      _builder.append(name);
      _builder.append("_callback(TimerHandle_t xTimer){");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("xSemaphoreGive(task_sem_");
      _builder.append(name, "\t");
      _builder.append(");");
      _builder.newLineIfNotEmpty();
      _builder.append("}\t\t\t\t");
      _builder.newLine();
      _builder.newLine();
      _builder.append("//void func_actorName_combinator(portName[], portName_rate ....)");
      _builder.newLine();
      _builder.append("inline static void combinator(");
      CharSequence _funcParameterSignature = actorhelp.funcParameterSignature(vertex, inputPorts, outputPorts);
      _builder.append(_funcParameterSignature);
      _builder.append("){");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("printf(\"in actor ");
      _builder.append(name, "\t");
      _builder.append("\\n\");");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.newLine();
      _builder.append("void task_");
      _builder.append(name);
      _builder.append("(void* pdata){");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("//array aiming to storing data from input ports");
      _builder.newLine();
      {
        boolean _hasElements_5 = false;
        for(final String port_4 : inputPorts) {
          if (!_hasElements_5) {
            _hasElements_5 = true;
          } else {
            _builder.appendImmediate("\n", "\t");
          }
          _builder.append("\t");
          _builder.append("long ");
          _builder.append(port_4, "\t");
          _builder.append("_rate = ");
          int _portRate = Query.getPortRate(SDFComb.enforce(vertex), port_4);
          _builder.append(_portRate, "\t");
          _builder.append(";");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("token_");
          String _channelName_2 = Query.getChannelName(vertex, port_4, Global.model);
          _builder.append(_channelName_2, "\t");
          _builder.append(" ");
          _builder.append(port_4, "\t");
          _builder.append("[");
          _builder.append(port_4, "\t");
          _builder.append("_rate];");
          _builder.newLineIfNotEmpty();
        }
        if (_hasElements_5) {
          _builder.append("\n", "\t");
        }
      }
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("//array aiming to writing data to input ports");
      _builder.newLine();
      {
        boolean _hasElements_6 = false;
        for(final String port_5 : outputPorts) {
          if (!_hasElements_6) {
            _hasElements_6 = true;
          } else {
            _builder.appendImmediate("\n", "\t");
          }
          _builder.append("\t");
          _builder.append("long ");
          _builder.append(port_5, "\t");
          _builder.append("_rate = ");
          int _portRate_1 = Query.getPortRate(SDFComb.enforce(vertex), port_5);
          _builder.append(_portRate_1, "\t");
          _builder.append(";");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("token_");
          String _sDFChannelName_2 = Query.getSDFChannelName(vertex, port_5, Global.model);
          _builder.append(_sDFChannelName_2, "\t");
          _builder.append(" ");
          _builder.append(port_5, "\t");
          _builder.append("[");
          _builder.append(port_5, "\t");
          _builder.append("_rate];");
          _builder.newLineIfNotEmpty();
        }
        if (_hasElements_6) {
          _builder.append("\n", "\t");
        }
      }
      _builder.append("\t");
      _builder.append("while(1){");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("/*");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("*\tread from channel");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("*/");
      _builder.newLine();
      {
        boolean _hasElements_7 = false;
        for(final String port_6 : inputPorts) {
          if (!_hasElements_7) {
            _hasElements_7 = true;
          } else {
            _builder.appendImmediate("", "\t\t");
          }
          _builder.append("\t\t");
          _builder.append("read_channel_in_");
          _builder.append(port_6, "\t\t");
          _builder.append("(msg_queue_");
          String _channelName_3 = Query.getChannelName(vertex, port_6, Global.model);
          _builder.append(_channelName_3, "\t\t");
          _builder.append(",");
          _builder.append(port_6, "\t\t");
          _builder.append("_rate,");
          _builder.append(port_6, "\t\t");
          _builder.append(");");
          _builder.newLineIfNotEmpty();
        }
        if (_hasElements_7) {
          _builder.append("", "\t\t");
        }
      }
      _builder.append("\t\t");
      _builder.append("/*");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("*\tcombinator function");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("*/");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("combinator(");
      String _funcParameter = actorhelp.funcParameter(vertex, inputPorts, outputPorts);
      _builder.append(_funcParameter, "\t\t");
      _builder.append(");\t");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("/*");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("*\twrite from channel");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("*/");
      _builder.newLine();
      {
        boolean _hasElements_8 = false;
        for(final String port_7 : outputPorts) {
          if (!_hasElements_8) {
            _hasElements_8 = true;
          } else {
            _builder.appendImmediate("", "\t\t");
          }
          _builder.append("\t\t");
          _builder.append("write_channel_in_");
          _builder.append(port_7, "\t\t");
          _builder.append("(");
          _builder.append(port_7, "\t\t");
          _builder.append(",");
          _builder.append(port_7, "\t\t");
          _builder.append("_rate,msg_queue_");
          String _sDFChannelName_3 = Query.getSDFChannelName(vertex, port_7, Global.model);
          _builder.append(_sDFChannelName_3, "\t\t");
          _builder.append(");");
          _builder.newLineIfNotEmpty();
        }
        if (_hasElements_8) {
          _builder.append("", "\t\t");
        }
      }
      _builder.append("\t\t");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("xSemaphoreTake(task_sem_");
      _builder.append(name, "\t\t");
      _builder.append(", portMAX_DELAY);\t");
      _builder.newLineIfNotEmpty();
      _builder.append("\t\t\t");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("}");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      _builder.newLine();
      _builder.newLine();
      _builder.newLine();
      _builder.newLine();
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
  
  private String path(final Vertex vertex) {
    String _name = Name.name(vertex);
    String _plus = ((generator.root + "/source/freertos_sdfcomb_") + _name);
    return (_plus + ".c");
  }
}
