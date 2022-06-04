package template.baremetal_multi;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel;
import forsyde.io.java.typed.viewers.values.IntegerValue;
import generator.Generator;
import generator.Schedule;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.templateInterface.SubsystemTemplate;
import utils.Name;
import utils.Query;

@FileTypeAnno(type = FileType.C_SOURCE)
@SuppressWarnings("all")
public class SubsystemTemplateSrcMulti implements SubsystemTemplate {
  private Schedule s;
  
  @Override
  public String create(final Schedule schedule) {
    String _xblockexpression = null;
    {
      this.s = schedule;
      Vertex tile = schedule.tile;
      ForSyDeSystemGraph model = Generator.model;
      final Predicate<Vertex> _function = (Vertex v) -> {
        return (IntegerValue.conforms(v)).booleanValue();
      };
      final Function<Vertex, IntegerValue> _function_1 = (Vertex v) -> {
        return IntegerValue.safeCast(v).get();
      };
      Set<IntegerValue> integerValues = model.vertexSet().stream().filter(_function).<IntegerValue>map(_function_1).collect(Collectors.<IntegerValue>toSet());
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("#include \"../inc/subsystem_");
      String _identifier = this.s.tile.getIdentifier();
      _builder.append(_identifier);
      _builder.append(".h\"");
      _builder.newLineIfNotEmpty();
      _builder.append("#include \"../inc/datatype_definition.h\"");
      _builder.newLine();
      _builder.append("#include <cheap_s.h>");
      _builder.newLine();
      _builder.append("void subsystem_");
      String _identifier_1 = tile.getIdentifier();
      _builder.append(_identifier_1);
      _builder.append("(){");
      _builder.newLineIfNotEmpty();
      {
        boolean _hasElements = false;
        for(final Vertex actor : schedule.slots) {
          if (!_hasElements) {
            _hasElements = true;
          } else {
            _builder.appendImmediate("", "");
          }
          int tmp = 1;
          _builder.newLineIfNotEmpty();
          {
            if ((actor != null)) {
              _builder.append("\t");
              _builder.append("actor_");
              String _name = Name.name(actor);
              _builder.append(_name, "\t");
              _builder.append("();");
              _builder.newLineIfNotEmpty();
            }
          }
        }
        if (_hasElements) {
          _builder.append("");
        }
      }
      _builder.append("}\t");
      _builder.newLine();
      _builder.newLine();
      _builder.append("int init_");
      String _identifier_2 = tile.getIdentifier();
      _builder.append(_identifier_2);
      _builder.append("(){");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("xil_printf(\"tile initialization starts\\n\");");
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
      _builder.newLine();
      {
        for(final Vertex channel : schedule.outgoingchannels) {
          String sdfname = channel.getIdentifier();
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          String type = Query.findSDFChannelDataType(Generator.model, channel);
          _builder.newLineIfNotEmpty();
          {
            boolean _isOnOneCoreChannel = Query.isOnOneCoreChannel(model, channel);
            if (_isOnOneCoreChannel) {
              _builder.append("\t");
              _builder.append("/* extern sdfchannel ");
              _builder.append(sdfname, "\t");
              _builder.append("*/");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("extern ");
              _builder.append(type, "\t");
              _builder.append(" buffer_");
              _builder.append(sdfname, "\t");
              _builder.append("[];");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("extern int buffer_");
              _builder.append(sdfname, "\t");
              _builder.append("_size;");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("extern circular_fifo_");
              _builder.append(type, "\t");
              _builder.append(" fifo_");
              _builder.append(sdfname, "\t");
              _builder.append(";");
              _builder.newLineIfNotEmpty();
            } else {
              _builder.append("\t");
              _builder.append("extern cheap fifo_admin_");
              _builder.append(sdfname, "\t");
              _builder.append(";");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("extern volatile ");
              _builder.append(type, "\t");
              _builder.append(" * const fifo_data_");
              _builder.append(sdfname, "\t");
              _builder.append(";");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("extern unsigned int buffer_");
              _builder.append(sdfname, "\t");
              _builder.append("_size;");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("extern unsigned int token_");
              _builder.append(sdfname, "\t");
              _builder.append("_size;");
              _builder.newLineIfNotEmpty();
            }
          }
        }
      }
      {
        for(final Vertex channel_1 : schedule.incomingchannels) {
          String sdfname_1 = channel_1.getIdentifier();
          _builder.newLineIfNotEmpty();
          String type_1 = Query.findSDFChannelDataType(Generator.model, channel_1);
          _builder.newLineIfNotEmpty();
          {
            boolean _isOnOneCoreChannel_1 = Query.isOnOneCoreChannel(model, channel_1);
            if (_isOnOneCoreChannel_1) {
              _builder.append("\t");
              _builder.append("/* extern sdfchannel ");
              _builder.append(sdfname_1, "\t");
              _builder.append("*/");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("extern ");
              _builder.append(type_1, "\t");
              _builder.append(" buffer_");
              _builder.append(sdfname_1, "\t");
              _builder.append("[];");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("extern int buffer_");
              _builder.append(sdfname_1, "\t");
              _builder.append("_size;");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("extern circular_fifo_");
              _builder.append(type_1, "\t");
              _builder.append(" fifo_");
              _builder.append(sdfname_1, "\t");
              _builder.append(";");
              _builder.newLineIfNotEmpty();
            } else {
              _builder.append("\t");
              _builder.append("extern cheap fifo_admin_");
              _builder.append(sdfname_1, "\t");
              _builder.append(";");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("extern volatile ");
              _builder.append(type_1, "\t");
              _builder.append(" * const fifo_data_");
              _builder.append(sdfname_1, "\t");
              _builder.append(";");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("extern unsigned int buffer_");
              _builder.append(sdfname_1, "\t");
              _builder.append("_size;");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("extern unsigned int token_");
              _builder.append(sdfname_1, "\t");
              _builder.append("_size;");
              _builder.newLineIfNotEmpty();
            }
          }
        }
      }
      _builder.append("/* Create the channels*/");
      _builder.newLine();
      {
        for(final Vertex channel_2 : schedule.outgoingchannels) {
          _builder.append("\t");
          String channelname = channel_2.getIdentifier();
          _builder.newLineIfNotEmpty();
          {
            boolean _isOnOneCoreChannel_2 = Query.isOnOneCoreChannel(model, channel_2);
            if (_isOnOneCoreChannel_2) {
              _builder.append("\t");
              _builder.append("init_channel_");
              String _findSDFChannelDataType = Query.findSDFChannelDataType(Generator.model, channel_2);
              _builder.append(_findSDFChannelDataType, "\t");
              _builder.append("(&fifo_");
              _builder.append(channelname, "\t");
              _builder.append(",buffer_");
              _builder.append(channelname, "\t");
              _builder.append(",buffer_");
              _builder.append(channelname, "\t");
              _builder.append("_size);");
              _builder.newLineIfNotEmpty();
            } else {
              _builder.append("\t");
              _builder.newLine();
              _builder.append("\t");
              _builder.append("if (cheap_init_r (fifo_admin_");
              _builder.append(channelname, "\t");
              _builder.append(", (void *) fifo_data_");
              _builder.append(channelname, "\t");
              _builder.append(", buffer_");
              _builder.append(channelname, "\t");
              _builder.append("_size, token_");
              _builder.append(channelname, "\t");
              _builder.append("_size) == NULL) {");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("\t");
              _builder.append("//xil_printf(\"%04u/%010u: cheap_init_r failed\\n\", (uint32_t)(t>>32),(uint32_t)t);");
              _builder.newLine();
              _builder.append("\t");
              _builder.append("\t");
              _builder.append("return 1;");
              _builder.newLine();
              _builder.append("\t");
              _builder.append("}\t\t\t\t");
              _builder.newLine();
            }
          }
        }
      }
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("/*Initialize the channel */");
      _builder.newLine();
      {
        for(final Vertex channel_3 : schedule.outgoingchannels) {
          SDFChannel sdfchannel = SDFChannel.safeCast(channel_3).get();
          _builder.newLineIfNotEmpty();
          String sdfchannelName = channel_3.getIdentifier();
          _builder.newLineIfNotEmpty();
          String datatype = Query.findSDFChannelDataType(model, channel_3);
          _builder.newLineIfNotEmpty();
          {
            if (((sdfchannel.getNumOfInitialTokens() != null) && ((sdfchannel.getNumOfInitialTokens()).intValue() > 0))) {
              Object _unwrap = sdfchannel.getProperties().get("__initialTokenValues_ordering__").unwrap();
              HashMap<String, Integer> ordering = ((HashMap<String, Integer>) _unwrap);
              _builder.newLineIfNotEmpty();
              _builder.newLine();
              {
                int _size = ordering.size();
                boolean _greaterThan = (_size > 0);
                if (_greaterThan) {
                  ArrayList<String> initList = this.help(ordering);
                  _builder.append("\t\t");
                  _builder.newLineIfNotEmpty();
                  {
                    boolean _isOnOneCoreChannel_3 = Query.isOnOneCoreChannel(model, channel_3);
                    if (_isOnOneCoreChannel_3) {
                      {
                        for(final String valueName : initList) {
                          _builder.append("\t");
                          _builder.append("write_non_blocking_");
                          String _findSDFChannelDataType_1 = Query.findSDFChannelDataType(Generator.model, channel_3);
                          _builder.append(_findSDFChannelDataType_1, "\t");
                          _builder.append("(&fifo_");
                          String _identifier_4 = sdfchannel.getIdentifier();
                          _builder.append(_identifier_4, "\t");
                          _builder.append(",");
                          _builder.append(valueName, "\t");
                          _builder.append(");");
                          _builder.newLineIfNotEmpty();
                        }
                      }
                    } else {
                      _builder.append("\t");
                      _builder.append("{");
                      _builder.newLine();
                      _builder.append("\t");
                      _builder.newLine();
                      _builder.append("\t");
                      _builder.append("\t");
                      _builder.append("volatile ");
                      _builder.append(datatype, "\t\t");
                      _builder.append(" *tmp_ptrs[");
                      int _size_1 = initList.size();
                      _builder.append(_size_1, "\t\t");
                      _builder.append("];");
                      _builder.newLineIfNotEmpty();
                      _builder.append("\t");
                      _builder.append("\t");
                      _builder.append("while ((cheap_claim_spaces (fifo_admin_");
                      _builder.append(sdfchannelName, "\t\t");
                      _builder.append(", (volatile void **) &tmp_ptrs[0], ");
                      int _size_2 = initList.size();
                      _builder.append(_size_2, "\t\t");
                      _builder.append(")) < ");
                      int _size_3 = initList.size();
                      _builder.append(_size_3, "\t\t");
                      _builder.append(")");
                      _builder.newLineIfNotEmpty();
                      _builder.append("\t");
                      _builder.append("\t");
                      _builder.append("cheap_release_all_claimed_spaces (fifo_admin_");
                      _builder.append(sdfchannelName, "\t\t");
                      _builder.append(");");
                      _builder.newLineIfNotEmpty();
                      _builder.append("\t");
                      _builder.append("\t");
                      _builder.newLine();
                      _builder.append("\t");
                      _builder.append("\t");
                      int i = 0;
                      _builder.newLineIfNotEmpty();
                      {
                        for(final String value_1 : initList) {
                          _builder.append("\t");
                          _builder.append("\t");
                          _builder.append("*tmp_ptrs[");
                          _builder.append(i, "\t\t");
                          _builder.append("]=");
                          _builder.append(value_1, "\t\t");
                          _builder.append(";");
                          _builder.newLineIfNotEmpty();
                          _builder.append("\t");
                          _builder.append("\t");
                          _builder.append(i = (i + 1), "\t\t");
                          _builder.newLineIfNotEmpty();
                        }
                      }
                      _builder.append("\t");
                      _builder.append("\t");
                      _builder.newLine();
                      _builder.append("\t");
                      _builder.append("\t");
                      _builder.append("cheap_release_tokens (fifo_admin_");
                      _builder.append(sdfchannelName, "\t\t");
                      _builder.append(", ");
                      int _size_4 = initList.size();
                      _builder.append(_size_4, "\t\t");
                      _builder.append(");");
                      _builder.newLineIfNotEmpty();
                      _builder.append("\t");
                      _builder.append("}\t\t");
                      _builder.newLine();
                      _builder.append("\t");
                      _builder.newLine();
                      _builder.append("\t");
                      _builder.newLine();
                    }
                  }
                }
              }
            }
          }
        }
      }
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("/* wait util other channels are created*/");
      _builder.newLine();
      {
        for(final Vertex channel_4 : schedule.incomingchannels) {
          _builder.append("\t");
          _builder.append("while (cheap_get_buffer_capacity (fifo_admin_");
          String _identifier_5 = channel_4.getIdentifier();
          _builder.append(_identifier_5, "\t");
          _builder.append(") == 0); ");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.append("\t");
      _builder.append("xil_printf(\"tile initialization ends\\n\");\t\t\t\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("return 0;\t");
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
  
  @Override
  public String getFileName() {
    String _identifier = this.s.tile.getIdentifier();
    return ("subsystem_tile_" + _identifier);
  }
  
  public ArrayList<String> help(final HashMap<String, Integer> ordering) {
    int _size = ordering.size();
    ArrayList<String> a = new ArrayList<String>(_size);
    Set<String> _keySet = ordering.keySet();
    for (final String k : _keySet) {
      a.add((ordering.get(k)).intValue(), k);
    }
    return a;
  }
}
