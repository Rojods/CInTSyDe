package template.rtos;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.core.VertexProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.templateInterface.ActorTemplate;
import utils.Name;
import utils.Query;

@FileTypeAnno(type = FileType.C_SOURCE)
@SuppressWarnings("all")
public class SDFCombTemplateSrcRTOS implements ActorTemplate {
  private Set<Vertex> implActorSet;
  
  public String create(final Vertex vertex) {
    String _xblockexpression = null;
    {
      String name = Name.name(vertex);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("/*");
      _builder.newLine();
      _builder.append("==============================================");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("Define Task Stack");
      _builder.newLine();
      _builder.append("==============================================");
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
      _builder.newLine();
      _builder.newLine();
      _builder.append("==============================================");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("Declare Extern Message Queue Handler");
      _builder.newLine();
      _builder.append("==============================================");
      _builder.newLine();
      _builder.append("*/");
      _builder.newLine();
      _builder.append("/*");
      _builder.newLine();
      _builder.append("==============================================");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("Define Soft Timer and Soft Timer Semaphore");
      _builder.newLine();
      _builder.append("==============================================");
      _builder.newLine();
      _builder.append("*/");
      _builder.newLine();
      _builder.newLine();
      _builder.append("SemaphoreHandle_t timer_sem_");
      _builder.append(name);
      _builder.append(";");
      _builder.newLineIfNotEmpty();
      _builder.append("TimerHandle_t task_timer_");
      _builder.append(name);
      _builder.append(";");
      _builder.newLineIfNotEmpty();
      _builder.append("/*");
      _builder.newLine();
      _builder.append("==============================================");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("Define Task Function");
      _builder.newLine();
      _builder.append("==============================================");
      _builder.newLine();
      _builder.append("*/");
      _builder.newLine();
      _builder.append("void task_");
      _builder.append(name);
      _builder.append("(void* pdata){");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("/* Initilize Memory           */");
      _builder.newLine();
      _builder.append("\t");
      String _initMemory = this.initMemory();
      _builder.append(_initMemory, "\t");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("while(1){");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("/* Read From Channel      */");
      _builder.newLine();
      _builder.append("\t\t");
      String _read = this.read(vertex);
      _builder.append(_read, "\t\t");
      _builder.newLineIfNotEmpty();
      _builder.append("\t\t");
      _builder.append("/* Inline Code            */");
      _builder.newLine();
      _builder.append("\t\t");
      String _inlineCode = this.getInlineCode();
      _builder.append(_inlineCode, "\t\t");
      _builder.newLineIfNotEmpty();
      _builder.append("\t\t");
      _builder.append("/* Write To Channel       */");
      _builder.newLine();
      _builder.append("\t\t");
      String _write = this.write(vertex);
      _builder.append(_write, "\t\t");
      _builder.newLineIfNotEmpty();
      _builder.append("\t\t");
      _builder.append("/* Pend Timer\'s Semaphore */\t");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("xSemaphoreTake(task_sem_");
      _builder.append(name, "\t\t");
      _builder.append(", portMAX_DELAY);\t");
      _builder.newLineIfNotEmpty();
      _builder.append("\t\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("}");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
  
  /**
   * initMemory is copied from initMemory in SDFCombTemplateSrc class
   */
  public String initMemory() {
    Set<String> variableNameRecord = new HashSet<String>();
    String ret = "";
    for (final Vertex impl : this.implActorSet) {
      {
        Object _unwrap = impl.getProperties().get("portToTypeName").unwrap();
        final HashMap<String, String> portToTypeNameHashMap = ((HashMap<String, String>) _unwrap);
        Object _unwrap_1 = impl.getProperties().get("inputPorts").unwrap();
        final ArrayList<String> inputPortList = ((ArrayList<String>) _unwrap_1);
        Object _unwrap_2 = impl.getProperties().get("outputPorts").unwrap();
        final ArrayList<String> outputPortList = ((ArrayList<String>) _unwrap_2);
        HashSet<String> inputPortAndoutputPortSet = new HashSet<String>();
        inputPortAndoutputPortSet.addAll(inputPortList);
        inputPortAndoutputPortSet.addAll(outputPortList);
        String _ret = ret;
        StringConcatenation _builder = new StringConcatenation();
        {
          boolean _hasElements = false;
          for(final String port : inputPortAndoutputPortSet) {
            if (!_hasElements) {
              _hasElements = true;
            } else {
              _builder.appendImmediate("", "");
            }
            {
              boolean _contains = variableNameRecord.contains(port);
              boolean _not = (!_contains);
              if (_not) {
                String _get = portToTypeNameHashMap.get(port);
                _builder.append(_get);
                _builder.append("  ");
                _builder.append(port);
                _builder.append(";");
                _builder.newLineIfNotEmpty();
                boolean tmp = variableNameRecord.add(port);
                _builder.newLineIfNotEmpty();
              }
            }
          }
          if (_hasElements) {
            _builder.append("");
          }
        }
        ret = (_ret + _builder);
      }
    }
    return ret;
  }
  
  /**
   * copied and modified from method read in SDFCombTemplateSrc class
   */
  public String read(final Vertex vertex) {
    String _xblockexpression = null;
    {
      VertexProperty consumption = vertex.getProperties().get("consumption");
      String _xifexpression = null;
      if ((consumption != null)) {
        String _xblockexpression_1 = null;
        {
          Object _unwrap = consumption.unwrap();
          HashMap<String, Integer> inputPortsHashMap = ((HashMap<String, Integer>) _unwrap);
          StringConcatenation _builder = new StringConcatenation();
          {
            Set<String> _keySet = inputPortsHashMap.keySet();
            boolean _hasElements = false;
            for(final String port : _keySet) {
              if (!_hasElements) {
                _hasElements = true;
              } else {
                _builder.appendImmediate("", "");
              }
              {
                Integer _get = inputPortsHashMap.get(port);
                boolean _equals = ((_get).intValue() == 1);
                if (_equals) {
                  _builder.append("read_nonblocking(");
                  _builder.append(port);
                  _builder.append("_channel);");
                  _builder.newLineIfNotEmpty();
                } else {
                  _builder.append("for(int i=0;i<");
                  Integer _get_1 = inputPortsHashMap.get(port);
                  _builder.append(_get_1);
                  _builder.append(";++i){");
                  _builder.newLineIfNotEmpty();
                  _builder.append("\t");
                  _builder.append("read_nonblocking(");
                  _builder.append(port, "\t");
                  _builder.append("_channel);");
                  _builder.newLineIfNotEmpty();
                  _builder.append("}");
                  _builder.newLine();
                }
              }
            }
            if (_hasElements) {
              _builder.append("");
            }
          }
          _xblockexpression_1 = _builder.toString();
        }
        _xifexpression = _xblockexpression_1;
      } else {
        StringConcatenation _builder = new StringConcatenation();
        _xifexpression = _builder.toString();
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  /**
   * copied and modified from method write in SDFCombTemplateSrc class
   */
  public String write(final Vertex vertex) {
    String _xblockexpression = null;
    {
      VertexProperty production = vertex.getProperties().get("production");
      String _xifexpression = null;
      if ((production != null)) {
        String _xblockexpression_1 = null;
        {
          Object _unwrap = production.unwrap();
          HashMap<String, Integer> inputPortsHashMap = ((HashMap<String, Integer>) _unwrap);
          StringConcatenation _builder = new StringConcatenation();
          {
            Set<String> _keySet = inputPortsHashMap.keySet();
            boolean _hasElements = false;
            for(final String port : _keySet) {
              if (!_hasElements) {
                _hasElements = true;
              } else {
                _builder.appendImmediate("", "");
              }
              {
                Integer _get = inputPortsHashMap.get(port);
                boolean _equals = ((_get).intValue() == 1);
                if (_equals) {
                  _builder.append("write(");
                  _builder.append(port);
                  _builder.append("_channel);");
                  _builder.newLineIfNotEmpty();
                } else {
                  _builder.append("for(int i=0;i<");
                  Integer _get_1 = inputPortsHashMap.get(port);
                  _builder.append(_get_1);
                  _builder.append(";++i){");
                  _builder.newLineIfNotEmpty();
                  _builder.append("\t");
                  _builder.append("write(");
                  _builder.append(port, "\t");
                  _builder.append("_channel);");
                  _builder.newLineIfNotEmpty();
                  _builder.append("}");
                  _builder.newLine();
                }
              }
            }
            if (_hasElements) {
              _builder.append("");
            }
          }
          _xblockexpression_1 = _builder.toString();
        }
        _xifexpression = _xblockexpression_1;
      } else {
        StringConcatenation _builder = new StringConcatenation();
        _xifexpression = _builder.toString();
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  /**
   * This method is same as getInlineCode in SDFCombTemplateSrc class
   */
  private String getInlineCode() {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _hasElements = false;
      for(final Vertex impl : this.implActorSet) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate("", "");
        }
        _builder.append("//in combFunction ");
        String _identifier = impl.getIdentifier();
        _builder.append(_identifier);
        _builder.newLineIfNotEmpty();
        String _inlineCode = Query.getInlineCode(impl);
        _builder.append(_inlineCode);
        _builder.newLineIfNotEmpty();
      }
      if (_hasElements) {
        _builder.append("");
      }
    }
    return _builder.toString();
  }
}
