package template.nonRTOS.actor;

import forsyde.io.java.core.Vertex;
import java.util.Set;
import org.eclipse.xtend2.lib.StringConcatenation;
import utils.Global;
import utils.Name;
import utils.Query;

@SuppressWarnings("all")
public class actorhelp {
  public static String actorParameterSignature(final Vertex vertex, final Set<String> inputPorts, final Set<String> outputPorts) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _hasElements = false;
      for(final String port : inputPorts) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(",", "");
        }
        _builder.append("circularFIFO_");
        String _sDFChannelName = Query.getSDFChannelName(vertex, port, Global.model);
        _builder.append(_sDFChannelName);
        _builder.append("* channel_");
        _builder.append(port);
        _builder.append("_ptr, const size_t ");
        _builder.append(port);
        _builder.append("_rate");
        _builder.newLineIfNotEmpty();
      }
      if (_hasElements) {
        _builder.append(" ");
      }
    }
    {
      int _size = inputPorts.size();
      boolean _equals = (_size == 0);
      if (_equals) {
        {
          boolean _hasElements_1 = false;
          for(final String port_1 : outputPorts) {
            if (!_hasElements_1) {
              _hasElements_1 = true;
            } else {
              _builder.appendImmediate(",", "");
            }
            _builder.append("circularFIFO_");
            String _sDFChannelName_1 = Query.getSDFChannelName(vertex, port_1, Global.model);
            _builder.append(_sDFChannelName_1);
            _builder.append("* channel_");
            _builder.append(port_1);
            _builder.append("_ptr,const size_t ");
            _builder.append(port_1);
            _builder.append("_rate");
            _builder.newLineIfNotEmpty();
          }
          if (_hasElements_1) {
            _builder.append(" ");
          }
        }
      } else {
        {
          boolean _hasElements_2 = false;
          for(final String port_2 : outputPorts) {
            if (!_hasElements_2) {
              _hasElements_2 = true;
            } else {
              _builder.appendImmediate(" ", "");
            }
            _builder.append(",circularFIFO_");
            String _sDFChannelName_2 = Query.getSDFChannelName(vertex, port_2, Global.model);
            _builder.append(_sDFChannelName_2);
            _builder.append("* channel_");
            _builder.append(port_2);
            _builder.append("_ptr,const size_t ");
            _builder.append(port_2);
            _builder.append("_rate");
            _builder.newLineIfNotEmpty();
          }
          if (_hasElements_2) {
            _builder.append(" ");
          }
        }
      }
    }
    return _builder.toString();
  }
  
  public static String func_pointer(final Vertex vertex, final Set<String> inputPorts, final Set<String> outputPorts) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("void (*func_");
    String _name = Name.name(vertex);
    _builder.append(_name);
    _builder.append("_combinator)(");
    _builder.newLineIfNotEmpty();
    {
      boolean _hasElements = false;
      for(final String port : inputPorts) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(",", "\t");
        }
        _builder.append("\t");
        _builder.append("token_");
        String _sDFChannelName = Query.getSDFChannelName(vertex, port, Global.model);
        _builder.append(_sDFChannelName, "\t");
        _builder.append(" *, size_t ");
        _builder.newLineIfNotEmpty();
      }
      if (_hasElements) {
        _builder.append(" ", "\t");
      }
    }
    {
      int _size = inputPorts.size();
      boolean _equals = (_size == 0);
      if (_equals) {
        {
          boolean _hasElements_1 = false;
          for(final String port_1 : outputPorts) {
            if (!_hasElements_1) {
              _hasElements_1 = true;
            } else {
              _builder.appendImmediate(",", "\t");
            }
            _builder.append("\t");
            _builder.append("token_");
            String _sDFChannelName_1 = Query.getSDFChannelName(vertex, port_1, Global.model);
            _builder.append(_sDFChannelName_1, "\t");
            _builder.append(" *, size_t ");
            _builder.newLineIfNotEmpty();
          }
          if (_hasElements_1) {
            _builder.append(" ", "\t");
          }
        }
      } else {
        {
          boolean _hasElements_2 = false;
          for(final String port_2 : outputPorts) {
            if (!_hasElements_2) {
              _hasElements_2 = true;
            } else {
              _builder.appendImmediate(" ", "\t");
            }
            _builder.append("\t");
            _builder.append(",token_");
            String _sDFChannelName_2 = Query.getSDFChannelName(vertex, port_2, Global.model);
            _builder.append(_sDFChannelName_2, "\t");
            _builder.append(" *, size_t ");
            _builder.newLineIfNotEmpty();
          }
          if (_hasElements_2) {
            _builder.append(" ", "\t");
          }
        }
      }
    }
    _builder.append(")\t\t\t");
    _builder.newLine();
    return _builder.toString();
  }
  
  public static String funcParameter(final Vertex vertex, final Set<String> inputPorts, final Set<String> outputPorts) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _hasElements = false;
      for(final String port : inputPorts) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(",", "");
        }
        _builder.append(port);
        _builder.append(",");
        _builder.append(port);
        _builder.append("_rate");
      }
      if (_hasElements) {
        _builder.append(" ");
      }
    }
    {
      int _size = inputPorts.size();
      boolean _equals = (_size == 0);
      if (_equals) {
        {
          boolean _hasElements_1 = false;
          for(final String port_1 : outputPorts) {
            if (!_hasElements_1) {
              _hasElements_1 = true;
            } else {
              _builder.appendImmediate(",", "");
            }
            _builder.append(port_1);
            _builder.append(",");
            _builder.append(port_1);
            _builder.append("_rate");
          }
          if (_hasElements_1) {
            _builder.append(" ");
          }
        }
      } else {
        {
          boolean _hasElements_2 = false;
          for(final String port_2 : outputPorts) {
            if (!_hasElements_2) {
              _hasElements_2 = true;
            } else {
              _builder.appendImmediate(" ", "");
            }
            _builder.append(", ");
            _builder.append(port_2);
            _builder.append(",");
            _builder.append(port_2);
            _builder.append("_rate");
          }
          if (_hasElements_2) {
            _builder.append(" ");
          }
        }
      }
    }
    return _builder.toString();
  }
  
  public static CharSequence funcParameterSignature(final Vertex vertex, final Set<String> inputPorts, final Set<String> outputPorts) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\t");
    _builder.newLine();
    {
      boolean _hasElements = false;
      for(final String port : inputPorts) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(",", "");
        }
        _builder.append("token_");
        String _sDFChannelName = Query.getSDFChannelName(vertex, port, Global.model);
        _builder.append(_sDFChannelName);
        _builder.append(" ");
        _builder.append(port);
        _builder.append("[] , const size_t ");
        _builder.append(port);
        _builder.append("_rate");
        _builder.newLineIfNotEmpty();
      }
      if (_hasElements) {
        _builder.append(" ");
      }
    }
    {
      int _size = inputPorts.size();
      boolean _equals = (_size == 0);
      if (_equals) {
        {
          boolean _hasElements_1 = false;
          for(final String port_1 : outputPorts) {
            if (!_hasElements_1) {
              _hasElements_1 = true;
            } else {
              _builder.appendImmediate(",", "");
            }
            _builder.append("token_");
            String _sDFChannelName_1 = Query.getSDFChannelName(vertex, port_1, Global.model);
            _builder.append(_sDFChannelName_1);
            _builder.append(" ");
            _builder.append(port_1);
            _builder.append("[],const size_t ");
            _builder.append(port_1);
            _builder.append("_rate");
            _builder.newLineIfNotEmpty();
          }
          if (_hasElements_1) {
            _builder.append(" ");
          }
        }
      } else {
        {
          boolean _hasElements_2 = false;
          for(final String port_2 : outputPorts) {
            if (!_hasElements_2) {
              _hasElements_2 = true;
            } else {
              _builder.appendImmediate(" ", "");
            }
            _builder.append(",token_");
            String _sDFChannelName_2 = Query.getSDFChannelName(vertex, port_2, Global.model);
            _builder.append(_sDFChannelName_2);
            _builder.append("  ");
            _builder.append(port_2);
            _builder.append("[],const size_t ");
            _builder.append(port_2);
            _builder.append("_rate");
            _builder.newLineIfNotEmpty();
          }
          if (_hasElements_2) {
            _builder.append(" ");
          }
        }
      }
    }
    return _builder;
  }
}
