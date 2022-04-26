package template.nonRTOS.actor;

import forsyde.io.java.core.EdgeInfo;
import forsyde.io.java.core.EdgeTrait;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb;
import generator.generator;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.Template;
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
      Save.save(this.path(v), this.src(v));
    }
  }
  
  private String path(final Vertex vertex) {
    String _name = Name.name(vertex);
    String _plus = ((generator.root + "/src/sdfcomb_") + _name);
    return (_plus + ".c");
  }
  
  public String src(final Vertex vertex) {
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
      _builder.append("#include <stdlib.h>");
      _builder.newLine();
      _builder.append("#include <stdio.h>");
      _builder.newLine();
      _builder.append("#include \"../inc/sdfcomb_");
      _builder.append(name);
      _builder.append(".h\"");
      _builder.newLineIfNotEmpty();
      _builder.append("#include \"../inc/config.h\"");
      _builder.newLine();
      _builder.append("#include \"../inc/spinlock.h\"");
      _builder.newLine();
      {
        boolean _hasElements = false;
        for(final String port : inputPorts) {
          if (!_hasElements) {
            _hasElements = true;
          } else {
            _builder.appendImmediate("", "");
          }
          _builder.append("extern spinlock spinlock_");
          String _channelName = Query.getChannelName(vertex, port, Global.model);
          _builder.append(_channelName);
          _builder.append(";");
          _builder.newLineIfNotEmpty();
        }
        if (_hasElements) {
          _builder.append("");
        }
      }
      {
        boolean _hasElements_1 = false;
        for(final String port_1 : outputPorts) {
          if (!_hasElements_1) {
            _hasElements_1 = true;
          } else {
            _builder.appendImmediate("", "");
          }
          _builder.append("extern spinlock spinlock_");
          String _channelName_1 = Query.getChannelName(vertex, port_1, Global.model);
          _builder.append(_channelName_1);
          _builder.append(";");
          _builder.newLineIfNotEmpty();
        }
        if (_hasElements_1) {
          _builder.append("");
        }
      }
      _builder.newLine();
      {
        boolean _hasElements_2 = false;
        for(final String port_2 : inputPorts) {
          if (!_hasElements_2) {
            _hasElements_2 = true;
          } else {
            _builder.appendImmediate("", "");
          }
          _builder.append("inline static void read_channel_");
          _builder.append(name);
          _builder.append("_");
          _builder.append(port_2);
          _builder.append("(circularFIFO_");
          String _sDFChannelName = Query.getSDFChannelName(vertex, port_2, Global.model);
          _builder.append(_sDFChannelName);
          _builder.append("* src_channel_ptr, const size_t num, token_");
          String _sDFChannelName_1 = Query.getSDFChannelName(vertex, port_2, Global.model);
          _builder.append(_sDFChannelName_1);
          _builder.append("  dst[]){");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("//#if defined SINGLE");
          _builder.newLine();
          _builder.append("\t\t");
          _builder.append("for(size_t i=0 ; i < num ;++i){");
          _builder.newLine();
          _builder.append("\t\t\t");
          _builder.append("#if ");
          String _upperCase = Query.getSDFChannelName(vertex, port_2, Global.model).toUpperCase();
          _builder.append(_upperCase, "\t\t\t");
          _builder.append("_NONBLOCKING==1");
          _builder.newLineIfNotEmpty();
          _builder.append("\t\t\t\t");
          _builder.append("if(read_circularFIFO_non_blocking_");
          String _sDFChannelName_2 = Query.getSDFChannelName(vertex, port_2, Global.model);
          _builder.append(_sDFChannelName_2, "\t\t\t\t");
          _builder.append("(src_channel_ptr,dst+i) ==-1){");
          _builder.newLineIfNotEmpty();
          _builder.append("\t\t\t");
          _builder.append("#else");
          _builder.newLine();
          _builder.append("\t\t\t\t");
          _builder.append("if(read_circularFIFO_blocking_");
          String _sDFChannelName_3 = Query.getSDFChannelName(vertex, port_2, Global.model);
          _builder.append(_sDFChannelName_3, "\t\t\t\t");
          _builder.append("(src_channel_ptr,dst+i,&spinlock_");
          String _sDFChannelName_4 = Query.getSDFChannelName(vertex, port_2, Global.model);
          _builder.append(_sDFChannelName_4, "\t\t\t\t");
          _builder.append(") ==-1){");
          _builder.newLineIfNotEmpty();
          _builder.append("\t\t\t");
          _builder.append("#endif\t\t");
          _builder.newLine();
          _builder.append("\t\t\t\t");
          _builder.append("//error");
          _builder.newLine();
          _builder.append("\t\t\t\t");
          _builder.append("//abort();");
          _builder.newLine();
          _builder.append("\t\t\t");
          _builder.append("}");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("}");
          _builder.newLine();
          _builder.append("}");
          _builder.newLine();
        }
        if (_hasElements_2) {
          _builder.append("\n");
        }
      }
      _builder.newLine();
      {
        boolean _hasElements_3 = false;
        for(final String port_3 : outputPorts) {
          if (!_hasElements_3) {
            _hasElements_3 = true;
          } else {
            _builder.appendImmediate("", "");
          }
          _builder.append("inline static void write_channel_");
          _builder.append(name);
          _builder.append("_");
          _builder.append(port_3);
          _builder.append("(token_");
          String _sDFChannelName_5 = Query.getSDFChannelName(vertex, port_3, Global.model);
          _builder.append(_sDFChannelName_5);
          _builder.append(" src[],const size_t num,circularFIFO_");
          String _sDFChannelName_6 = Query.getSDFChannelName(vertex, port_3, Global.model);
          _builder.append(_sDFChannelName_6);
          _builder.append("* dst_channel_ptr){");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("for(size_t i=0 ; i < num ;++i){");
          _builder.newLine();
          _builder.append("\t\t");
          _builder.append("#if ");
          String _upperCase_1 = Query.getSDFChannelName(vertex, port_3, Global.model).toUpperCase();
          _builder.append(_upperCase_1, "\t\t");
          _builder.append("_NONBLOCKING==1");
          _builder.newLineIfNotEmpty();
          _builder.append("\t\t\t");
          _builder.append("if(write_circularFIFO_non_blocking_");
          String _sDFChannelName_7 = Query.getSDFChannelName(vertex, port_3, Global.model);
          _builder.append(_sDFChannelName_7, "\t\t\t");
          _builder.append("(dst_channel_ptr,src[i]) ==-1){");
          _builder.newLineIfNotEmpty();
          _builder.append("\t\t");
          _builder.append("#else");
          _builder.newLine();
          _builder.append("\t\t\t");
          _builder.append("if(write_circularFIFO_blocking_");
          String _sDFChannelName_8 = Query.getSDFChannelName(vertex, port_3, Global.model);
          _builder.append(_sDFChannelName_8, "\t\t\t");
          _builder.append("(dst_channel_ptr,src[i],&spinlock_");
          String _sDFChannelName_9 = Query.getSDFChannelName(vertex, port_3, Global.model);
          _builder.append(_sDFChannelName_9, "\t\t\t");
          _builder.append(") ==-1){");
          _builder.newLineIfNotEmpty();
          _builder.append("\t\t");
          _builder.append("#endif");
          _builder.newLine();
          _builder.append("\t\t\t\t");
          _builder.append("//error");
          _builder.newLine();
          _builder.append("\t\t\t");
          _builder.append("}");
          _builder.newLine();
          _builder.append("\t\t");
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
      _builder.append("\t");
      _builder.newLine();
      _builder.append("inline void actor_");
      _builder.append(name);
      _builder.append("(");
      String _actorParameterSignature = actorhelp.actorParameterSignature(vertex, inputPorts, outputPorts);
      _builder.append(_actorParameterSignature);
      _builder.append("){");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      {
        boolean _hasElements_4 = false;
        for(final String port_4 : inputPorts) {
          if (!_hasElements_4) {
            _hasElements_4 = true;
          } else {
            _builder.appendImmediate("", "\t");
          }
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
        if (_hasElements_4) {
          _builder.append("", "\t");
        }
      }
      {
        boolean _hasElements_5 = false;
        for(final String port_5 : outputPorts) {
          if (!_hasElements_5) {
            _hasElements_5 = true;
          } else {
            _builder.appendImmediate("", "\t");
          }
          _builder.append("\t");
          _builder.append("token_");
          String _channelName_3 = Query.getChannelName(vertex, port_5, Global.model);
          _builder.append(_channelName_3, "\t");
          _builder.append(" ");
          _builder.append(port_5, "\t");
          _builder.append("[");
          _builder.append(port_5, "\t");
          _builder.append("_rate];");
          _builder.newLineIfNotEmpty();
        }
        if (_hasElements_5) {
          _builder.append("", "\t");
        }
      }
      {
        boolean _hasElements_6 = false;
        for(final String port_6 : inputPorts) {
          if (!_hasElements_6) {
            _hasElements_6 = true;
          } else {
            _builder.appendImmediate(" \n", "\t");
          }
          _builder.append("\t");
          _builder.append("read_channel_");
          _builder.append(name, "\t");
          _builder.append("_");
          _builder.append(port_6, "\t");
          _builder.append("(channel_");
          _builder.append(port_6, "\t");
          _builder.append("_ptr,");
          _builder.append(port_6, "\t");
          _builder.append("_rate,");
          _builder.append(port_6, "\t");
          _builder.append(");");
          _builder.newLineIfNotEmpty();
        }
        if (_hasElements_6) {
          _builder.append("\n", "\t");
        }
      }
      _builder.append("\t\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("combinator(");
      String _funcParameter = actorhelp.funcParameter(vertex, inputPorts, outputPorts);
      _builder.append(_funcParameter, "\t");
      _builder.append(");\t");
      _builder.newLineIfNotEmpty();
      {
        boolean _hasElements_7 = false;
        for(final String port_7 : outputPorts) {
          if (!_hasElements_7) {
            _hasElements_7 = true;
          } else {
            _builder.appendImmediate(" \n", "\t");
          }
          _builder.append("\t");
          _builder.append("write_channel_");
          _builder.append(name, "\t");
          _builder.append("_");
          _builder.append(port_7, "\t");
          _builder.append("(");
          _builder.append(port_7, "\t");
          _builder.append(",");
          _builder.append(port_7, "\t");
          _builder.append("_rate,channel_");
          _builder.append(port_7, "\t");
          _builder.append("_ptr);");
          _builder.newLineIfNotEmpty();
        }
        if (_hasElements_7) {
          _builder.append("\n", "\t");
        }
      }
      _builder.append("}");
      _builder.newLine();
      _builder.newLine();
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.newLine();
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
}
