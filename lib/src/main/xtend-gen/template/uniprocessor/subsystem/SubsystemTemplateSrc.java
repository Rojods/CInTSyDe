package template.uniprocessor.subsystem;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.typed.viewers.moc.sdf.SDFActor;
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel;
import forsyde.io.java.typed.viewers.values.IntegerValue;
import generator.Generator;
import generator.Schedule;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.templateInterface.SubsystemTemplate;
import utils.Query;

@FileTypeAnno(type = FileType.C_SOURCE)
@SuppressWarnings("all")
public class SubsystemTemplateSrc implements SubsystemTemplate {
  @Override
  public String create(final Schedule s) {
    String _xblockexpression = null;
    {
      ForSyDeSystemGraph model = Generator.model;
      final Predicate<Vertex> _function = (Vertex v) -> {
        return (SDFActor.conforms(v)).booleanValue();
      };
      Set<Vertex> sdfcomb = model.vertexSet().stream().filter(_function).collect(Collectors.<Vertex>toSet());
      final Predicate<Vertex> _function_1 = (Vertex v) -> {
        return (IntegerValue.conforms(v)).booleanValue();
      };
      final Function<Vertex, IntegerValue> _function_2 = (Vertex v) -> {
        return IntegerValue.safeCast(v).get();
      };
      Set<IntegerValue> integerValues = model.vertexSet().stream().filter(_function_1).<IntegerValue>map(_function_2).collect(Collectors.<IntegerValue>toSet());
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
      _builder.append("#include \"../inc/datatype_definition.h\"");
      _builder.newLine();
      _builder.append("#include \"../inc/circular_fifo_lib.h\"");
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
      _builder.append("int subsystem(){");
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
          _builder.append("//printf(\"%s\\n\",\"enter ");
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
      _builder.newLine();
      _builder.append("\t");
      _builder.append("/*");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("*********************************************************");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("Initialize All the Channels");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("Should be called before subsystem_single_uniprocessor()");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("*********************************************************");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("*/");
      _builder.newLine();
      _builder.append("int init_subsystem(){");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("/* Extern Variables */");
      _builder.newLine();
      {
        for(final IntegerValue value : integerValues) {
          _builder.append("\t");
          _builder.append("extern int ");
          String _identifier_3 = value.getIdentifier();
          _builder.append(_identifier_3, "\t");
          _builder.append(";");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      String _externChannel = this.externChannel();
      _builder.append(_externChannel, "\t");
      _builder.append("\t\t");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("/* initialize the channels*/");
      _builder.newLine();
      {
        for(final Vertex channel : Generator.sdfchannelSet) {
          _builder.append("\t\t");
          String sdfname = channel.getIdentifier();
          _builder.newLineIfNotEmpty();
          {
            if ((Generator.fifoType == 1)) {
              _builder.append("\t\t");
              _builder.append("init_channel_");
              String _findSDFChannelDataType = Query.findSDFChannelDataType(Generator.model, channel);
              _builder.append(_findSDFChannelDataType, "\t\t");
              _builder.append("(&fifo_");
              _builder.append(sdfname, "\t\t");
              _builder.append(",buffer_");
              _builder.append(sdfname, "\t\t");
              _builder.append(",buffer_");
              _builder.append(sdfname, "\t\t");
              _builder.append("_size);");
              _builder.newLineIfNotEmpty();
            }
          }
          {
            if ((Generator.fifoType == 2)) {
              _builder.append("\t\t");
              _builder.append("init(&fifo_");
              _builder.append(sdfname, "\t\t");
              _builder.append(",buffer_");
              _builder.append(sdfname, "\t\t");
              _builder.append(",buffer_");
              _builder.append(sdfname, "\t\t");
              _builder.append("_size);");
              _builder.newLineIfNotEmpty();
            }
          }
        }
      }
      _builder.append("\t\t");
      _builder.newLine();
      {
        for(final Vertex channel_1 : Generator.sdfchannelSet) {
          SDFChannel sdfchannel = SDFChannel.safeCast(channel_1).get();
          _builder.newLineIfNotEmpty();
          {
            if (((sdfchannel.getNumOfInitialTokens() != null) && ((sdfchannel.getNumOfInitialTokens()).intValue() > 0))) {
              Object _unwrap = sdfchannel.getProperties().get("__initialTokenValues_ordering__").unwrap();
              HashMap<String, Integer> b = ((HashMap<String, Integer>) _unwrap);
              _builder.newLineIfNotEmpty();
              {
                Set<String> _keySet = b.keySet();
                for(final String k : _keySet) {
                  {
                    if ((Generator.fifoType == 2)) {
                      _builder.append("write_non_blocking(&fifo_");
                      String _identifier_4 = sdfchannel.getIdentifier();
                      _builder.append(_identifier_4);
                      _builder.append(",(void*)&");
                      _builder.append(k);
                      _builder.append(");");
                      _builder.newLineIfNotEmpty();
                    }
                  }
                  {
                    if ((Generator.fifoType == 1)) {
                      _builder.append("write_non_blocking_");
                      String _findSDFChannelDataType_1 = Query.findSDFChannelDataType(Generator.model, channel_1);
                      _builder.append(_findSDFChannelDataType_1);
                      _builder.append("(&fifo_");
                      String _identifier_5 = sdfchannel.getIdentifier();
                      _builder.append(_identifier_5);
                      _builder.append(",");
                      _builder.append(k);
                      _builder.append(");");
                      _builder.newLineIfNotEmpty();
                    }
                  }
                }
              }
            }
          }
        }
      }
      _builder.append("\t\t");
      _builder.append("return 0;");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("}\t\t");
      _builder.newLine();
      _builder.newLine();
      _builder.newLine();
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
  
  public String externChannel() {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final Vertex channel : Generator.sdfchannelSet) {
        String sdfname = channel.getIdentifier();
        _builder.newLineIfNotEmpty();
        String type = Query.findSDFChannelDataType(Generator.model, channel);
        _builder.newLineIfNotEmpty();
        _builder.append("/* extern sdfchannel ");
        _builder.append(sdfname);
        _builder.append("*/");
        _builder.newLineIfNotEmpty();
        {
          if ((Generator.fifoType == 1)) {
            _builder.append("extern ");
            _builder.append(type);
            _builder.append(" buffer_");
            _builder.append(sdfname);
            _builder.append("[];");
            _builder.newLineIfNotEmpty();
            _builder.append("extern int buffer_");
            _builder.append(sdfname);
            _builder.append("_size;");
            _builder.newLineIfNotEmpty();
            _builder.append("extern circular_fifo_");
            _builder.append(type);
            _builder.append(" fifo_");
            _builder.append(sdfname);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
        {
          if ((Generator.fifoType == 2)) {
            _builder.append("extern void* buffer_");
            _builder.append(sdfname);
            _builder.append("[];");
            _builder.newLineIfNotEmpty();
            _builder.append("extern size_t buffer_");
            _builder.append(sdfname);
            _builder.append("_size;");
            _builder.newLineIfNotEmpty();
            _builder.append("extern circular_fifo fifo_");
            _builder.append(sdfname);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
        {
          if ((Generator.fifoType == 3)) {
            _builder.append("extern void* buffer_");
            _builder.append(sdfname);
            _builder.append("[];");
            _builder.newLineIfNotEmpty();
            _builder.append("extern size_t buffer_");
            _builder.append(sdfname);
            _builder.append("_size;");
            _builder.newLineIfNotEmpty();
            _builder.append("extern circular_fifo fifo_");
            _builder.append(sdfname);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    return _builder.toString();
  }
  
  @Override
  public String getFileName() {
    return "subsystem";
  }
}
