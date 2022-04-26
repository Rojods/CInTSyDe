package template.nonRTOS.subsystem;

import forsyde.io.java.core.Vertex;
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel;
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb;
import generator.generator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
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
public class subsystemSrcUniprocessor implements Template {
  public subsystemSrcUniprocessor() {
  }
  
  public void create() {
    String str = this.createSource();
    Save.save((generator.root + "/src/subsystem.c"), str);
  }
  
  public String createSource() {
    String _xblockexpression = null;
    {
      final Predicate<Vertex> _function = new Predicate<Vertex>() {
        public boolean test(final Vertex v) {
          return (SDFChannel.conforms(v)).booleanValue();
        }
      };
      final Function<Vertex, SDFChannel> _function_1 = new Function<Vertex, SDFChannel>() {
        public SDFChannel apply(final Vertex v) {
          return SDFChannel.enforce(v);
        }
      };
      Set<SDFChannel> sdfChannelSet = Global.model.vertexSet().stream().filter(_function).<SDFChannel>map(_function_1).collect(Collectors.<SDFChannel>toSet());
      final Predicate<Vertex> _function_2 = new Predicate<Vertex>() {
        public boolean test(final Vertex v) {
          return (SDFComb.conforms(v)).booleanValue();
        }
      };
      Set<Vertex> sdfCombSet = Global.model.vertexSet().stream().filter(_function_2).collect(Collectors.<Vertex>toSet());
      final Predicate<Vertex> _function_3 = new Predicate<Vertex>() {
        public boolean test(final Vertex v) {
          return (v.hasTrait("impl::TokenizableDataBlock")).booleanValue();
        }
      };
      final Predicate<Vertex> _function_4 = new Predicate<Vertex>() {
        public boolean test(final Vertex v) {
          Boolean _hasTrait = v.hasTrait("moc::sdf::SDFChannel");
          return (!(_hasTrait).booleanValue());
        }
      };
      Set<Vertex> system_in_out = Global.model.vertexSet().stream().filter(_function_3).filter(_function_4).collect(Collectors.<Vertex>toSet());
      TreeMap<Integer, Vertex> firingSet = new TreeMap<Integer, Vertex>();
      for (final Vertex v : sdfCombSet) {
        firingSet.put(Query.getFiringSlot(SDFComb.enforce(v)), v);
      }
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("#include \"../inc/subsystem.h\"");
      _builder.newLine();
      {
        boolean _hasElements = false;
        for(final SDFChannel channel : sdfChannelSet) {
          if (!_hasElements) {
            _hasElements = true;
          } else {
            _builder.appendImmediate("\n", "\t");
          }
          _builder.append("\t");
          String channelName = Name.name(channel);
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("extern circularFIFO_");
          _builder.append(channelName, "\t");
          _builder.append(" channel_");
          _builder.append(channelName, "\t");
          _builder.append(";");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("extern token_");
          _builder.append(channelName, "\t");
          _builder.append(" arr_");
          _builder.append(channelName, "\t");
          _builder.append("[];");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("extern int buffersize_");
          _builder.append(channelName, "\t");
          _builder.append(";");
          _builder.newLineIfNotEmpty();
        }
        if (_hasElements) {
          _builder.append("", "\t");
        }
      }
      {
        boolean _hasElements_1 = false;
        for(final Vertex channel_1 : system_in_out) {
          if (!_hasElements_1) {
            _hasElements_1 = true;
          } else {
            _builder.appendImmediate("", "\t");
          }
          _builder.append("\t");
          _builder.append("extern circularFIFO_");
          String _name = Name.name(channel_1);
          _builder.append(_name, "\t");
          _builder.append(" channel_");
          String _name_1 = Name.name(channel_1);
          _builder.append(_name_1, "\t");
          _builder.append(";");
          _builder.newLineIfNotEmpty();
        }
        if (_hasElements_1) {
          _builder.append("", "\t");
        }
      }
      _builder.append("void subsystem(){");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("//create internal channels");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("/*");
      _builder.newLine();
      _builder.append("\t ");
      _builder.append("create sdf channels ");
      _builder.newLine();
      _builder.append("\t ");
      _builder.append("the identifier of sdf channel is the name of created channel");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("*/");
      _builder.newLine();
      {
        boolean _hasElements_2 = false;
        for(final SDFChannel channel_2 : sdfChannelSet) {
          if (!_hasElements_2) {
            _hasElements_2 = true;
          } else {
            _builder.appendImmediate("", "\t");
          }
          _builder.append("\t");
          String channelName_1 = Name.name(channel_2);
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("init_circularFIFO_");
          _builder.append(channelName_1, "\t");
          _builder.append("(&channel_");
          _builder.append(channelName_1, "\t");
          _builder.append(",arr_");
          _builder.append(channelName_1, "\t");
          _builder.append(",buffersize_");
          _builder.append(channelName_1, "\t");
          _builder.append(");");
          _builder.newLineIfNotEmpty();
        }
        if (_hasElements_2) {
          _builder.append("\n", "\t");
        }
      }
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("//SDFDelay");
      _builder.newLine();
      _builder.append("\t");
      String _sdfDelayHelpB = subsystemHelp.sdfDelayHelpB(sdfChannelSet);
      _builder.append(_sdfDelayHelpB, "\t");
      _builder.newLineIfNotEmpty();
      _builder.append("\t\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("while(1){");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("/*round robin*/");
      _builder.newLine();
      {
        Set<Map.Entry<Integer, Vertex>> _entrySet = firingSet.entrySet();
        boolean _hasElements_3 = false;
        for(final Map.Entry<Integer, Vertex> set : _entrySet) {
          if (!_hasElements_3) {
            _hasElements_3 = true;
          } else {
            _builder.appendImmediate("\n", "\t\t");
          }
          _builder.append("\t\t");
          _builder.append("actor_");
          String _name_2 = Name.name(set.getValue());
          _builder.append(_name_2, "\t\t");
          _builder.append("(");
          String _actorParameter = subsystemHelp.actorParameter(set.getValue());
          _builder.append(_actorParameter, "\t\t");
          _builder.append(");");
          _builder.newLineIfNotEmpty();
        }
        if (_hasElements_3) {
          _builder.append("\n", "\t\t");
        }
      }
      _builder.append("\t\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("}\t\t\t\t\t\t\t\t\t");
      _builder.newLine();
      _builder.append("}\t\t");
      _builder.newLine();
      _builder.newLine();
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
}
