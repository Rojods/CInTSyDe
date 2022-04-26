package template.nonRTOS.subsystem;

import forsyde.io.java.core.EdgeInfo;
import forsyde.io.java.core.EdgeTrait;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel;
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb;
import forsyde.io.java.typed.viewers.moc.sdf.SDFDelay;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.eclipse.xtend2.lib.StringConcatenation;
import utils.Global;
import utils.Name;
import utils.Query;

@SuppressWarnings("all")
public class subsystemHelp {
  public static String actorParameter(final Vertex vertex) {
    String _xblockexpression = null;
    {
      String name = Name.name(vertex);
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
      StringConcatenation _builder = new StringConcatenation();
      {
        boolean _hasElements = false;
        for(final String port : inputPorts) {
          if (!_hasElements) {
            _hasElements = true;
          } else {
            _builder.appendImmediate(",", "");
          }
          _builder.append("&channel_");
          String _sDFChannelName = Query.getSDFChannelName(vertex, port, Global.model);
          _builder.append(_sDFChannelName);
          _builder.append(",");
          int _portRate = Query.getPortRate(SDFComb.enforce(vertex), port);
          _builder.append(_portRate);
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
              _builder.append("&channel_");
              String _sDFChannelName_1 = Query.getSDFChannelName(vertex, port_1, Global.model);
              _builder.append(_sDFChannelName_1);
              _builder.append(",");
              int _portRate_1 = Query.getPortRate(SDFComb.enforce(vertex), port_1);
              _builder.append(_portRate_1);
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
              _builder.append(",&channel_");
              String _sDFChannelName_2 = Query.getSDFChannelName(vertex, port_2, Global.model);
              _builder.append(_sDFChannelName_2);
              _builder.append(",");
              int _portRate_2 = Query.getPortRate(SDFComb.enforce(vertex), port_2);
              _builder.append(_portRate_2);
              _builder.newLineIfNotEmpty();
            }
            if (_hasElements_2) {
              _builder.append(" ");
            }
          }
        }
      }
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
  
  public static String sdfDelayHelpB(final Set<SDFChannel> temps) {
    String _xblockexpression = null;
    {
      final Predicate<Vertex> _function = new Predicate<Vertex>() {
        public boolean test(final Vertex v) {
          return (SDFDelay.conforms(v)).booleanValue();
        }
      };
      Set<Vertex> delays = Global.model.vertexSet().stream().filter(_function).collect(Collectors.<Vertex>toSet());
      final Function<Vertex, SDFDelay> _function_1 = new Function<Vertex, SDFDelay>() {
        public SDFDelay apply(final Vertex v) {
          return SDFDelay.enforce(v);
        }
      };
      Set<SDFDelay> sdfdelays = delays.stream().<SDFDelay>map(_function_1).collect(Collectors.<SDFDelay>toSet());
      StringConcatenation _builder = new StringConcatenation();
      {
        boolean _hasElements = false;
        for(final SDFDelay delay : sdfdelays) {
          if (!_hasElements) {
            _hasElements = true;
          } else {
            _builder.appendImmediate("", "");
          }
          _builder.newLine();
          {
            boolean _isPresent = delay.getDelayedChannelPort(Global.model).isPresent();
            if (_isPresent) {
              _builder.append("\t");
              SDFChannel sdfchannel = delay.getDelayedChannelPort(Global.model).get();
              _builder.newLineIfNotEmpty();
              {
                boolean _contains = temps.contains(sdfchannel);
                if (_contains) {
                  Object _unwrap = delay.getProperties().get("delayedTokens").unwrap();
                  ArrayList<Integer> tokens = ((ArrayList<Integer>) _unwrap);
                  _builder.newLineIfNotEmpty();
                  String channelName = Name.name(sdfchannel);
                  _builder.newLineIfNotEmpty();
                  {
                    boolean _hasElements_1 = false;
                    for(final Integer token : tokens) {
                      if (!_hasElements_1) {
                        _hasElements_1 = true;
                      } else {
                        _builder.appendImmediate("\n", "");
                      }
                      _builder.append("write_circularFIFO_non_blocking_");
                      _builder.append(channelName);
                      _builder.append("(&channel_");
                      _builder.append(channelName);
                      _builder.append(",");
                      _builder.append(token);
                      _builder.append(");");
                      _builder.newLineIfNotEmpty();
                    }
                    if (_hasElements_1) {
                      _builder.append("\n");
                    }
                  }
                }
              }
            }
          }
        }
        if (_hasElements) {
          _builder.append("");
        }
      }
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
  
  public static String sdfDelayHelpA(final Set<Vertex> channels) {
    String _xblockexpression = null;
    {
      final Function<Vertex, SDFChannel> _function = new Function<Vertex, SDFChannel>() {
        public SDFChannel apply(final Vertex cha) {
          return SDFChannel.enforce(cha);
        }
      };
      Set<SDFChannel> temps = channels.stream().<SDFChannel>map(_function).collect(Collectors.<SDFChannel>toSet());
      _xblockexpression = subsystemHelp.sdfDelayHelpB(temps);
    }
    return _xblockexpression;
  }
}
