package template.baremetal.uniprocessor;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel;
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb;
import generator.Generator;
import generator.Schedule;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.templateInterface.SubsystemTemplate;
import utils.Query;

@FileTypeAnno(type = FileType.C_SOURCE)
@SuppressWarnings("all")
public class SubsystemTemplateSrc implements SubsystemTemplate {
  public String create(final Schedule s) {
    String _xblockexpression = null;
    {
      ForSyDeSystemGraph model = Generator.model;
      final Predicate<Vertex> _function = new Predicate<Vertex>() {
        public boolean test(final Vertex v) {
          return (SDFComb.conforms(v)).booleanValue();
        }
      };
      Set<Vertex> sdfcomb = model.vertexSet().stream().filter(_function).collect(Collectors.<Vertex>toSet());
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("#include \"../inc/subsystem.h\"");
      _builder.newLine();
      _builder.append("#include <stdio.h>");
      _builder.newLine();
      {
        for(final Vertex v : sdfcomb) {
          _builder.append("#include \"../inc/sdfcomb_");
          String _identifier = v.getIdentifier();
          _builder.append(_identifier);
          _builder.append(".h\"");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.append("/*");
      _builder.newLine();
      _builder.append("==============================================");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("Extern Variables are decalred in the ");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("subsystem_include_help.h");
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
      _builder.append("Subsystem Function");
      _builder.newLine();
      _builder.append("==============================================");
      _builder.newLine();
      _builder.append("*/\t");
      _builder.newLine();
      _builder.append("int fire_subsystem_single_uniprocessor(){");
      _builder.newLine();
      {
        Set<Map.Entry<Integer, Vertex>> _entrySet = Generator.uniprocessorSchedule.entrySet();
        boolean _hasElements = false;
        for(final Map.Entry<Integer, Vertex> set : _entrySet) {
          if (!_hasElements) {
            _hasElements = true;
          } else {
            _builder.appendImmediate("", "\t\t");
          }
          _builder.append("\t\t");
          _builder.append("printf(\"%s\\n\",\"enter ");
          String _identifier_1 = set.getValue().getIdentifier();
          _builder.append(_identifier_1, "\t\t");
          _builder.append("\");");
          _builder.newLineIfNotEmpty();
          {
            if (((Generator.TESTING == 1) && (Generator.PC == 1))) {
              _builder.append("\t\t");
              _builder.append("\t");
              _builder.append("actor_");
              String _identifier_2 = set.getValue().getIdentifier();
              _builder.append(_identifier_2, "\t\t\t");
              _builder.append("();");
              _builder.newLineIfNotEmpty();
            }
          }
        }
        if (_hasElements) {
          _builder.append("", "\t\t");
        }
      }
      _builder.append("}");
      _builder.newLine();
      _builder.newLine();
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
  
  public String initChannels() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("/*");
    _builder.newLine();
    _builder.append("*********************************************************");
    _builder.newLine();
    _builder.append("Initialize All the Channels");
    _builder.newLine();
    _builder.append("Should be called before subsystem_single_uniprocessor()");
    _builder.newLine();
    _builder.append("*********************************************************");
    _builder.newLine();
    _builder.append("*/");
    _builder.newLine();
    {
      for(final Vertex channel : Generator.sdfchannelSet) {
        _builder.append("\t");
        String sdfname = channel.getIdentifier();
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("init_channel_");
        String _findSDFChannelDataType = Query.findSDFChannelDataType(Generator.model, channel);
        _builder.append(_findSDFChannelDataType, "\t");
        _builder.append("(&fifo_");
        _builder.append(sdfname, "\t");
        _builder.append(",buffer_");
        _builder.append(sdfname, "\t");
        _builder.append(",buffer_");
        _builder.append(sdfname, "\t");
        _builder.append("_size);");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    {
      for(final Vertex channel_1 : Generator.sdfchannelSet) {
        _builder.append("\t");
        SDFChannel sdfchannel = SDFChannel.safeCast(channel_1).get();
        _builder.newLineIfNotEmpty();
        {
          if (((sdfchannel.getNumOfInitialTokens() != null) && ((sdfchannel.getNumOfInitialTokens()).intValue() > 0))) {
            _builder.append("\t");
            Object _unwrap = sdfchannel.getProperties().get("__initialTokenValues_ordering__").unwrap();
            HashMap<String, Integer> b = ((HashMap<String, Integer>) _unwrap);
            _builder.newLineIfNotEmpty();
            {
              Set<String> _keySet = b.keySet();
              for(final String k : _keySet) {
                _builder.append("\t");
                _builder.append("write_non_blocking_");
                String _findSDFChannelDataType_1 = Query.findSDFChannelDataType(Generator.model, channel_1);
                _builder.append(_findSDFChannelDataType_1, "\t");
                _builder.append("(&fifo_");
                String _identifier = sdfchannel.getIdentifier();
                _builder.append(_identifier, "\t");
                _builder.append(",");
                _builder.append(k, "\t");
                _builder.append(");");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
      }
    }
    return _builder.toString();
  }
  
  public String getFileName() {
    return "subsystem";
  }
}
