package template.baremetal_multi;

import com.google.common.base.Objects;
import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.core.Trait;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.core.VertexAcessor;
import forsyde.io.java.core.VertexTrait;
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel;
import forsyde.io.java.typed.viewers.values.IntegerValue;
import generator.Generator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.InputOutput;
import template.templateInterface.SubsystemTemplate;
import utils.MyPair;
import utils.Name;
import utils.Query;

@SuppressWarnings("all")
public class SubsystemTemplateSrcMulti implements SubsystemTemplate {
  private Vertex tile;
  
  private Vertex order;
  
  @Override
  public String create(final Vertex tile) {
    String _xblockexpression = null;
    {
      List<Vertex> slots = new ArrayList<Vertex>();
      this.tile = tile;
      ForSyDeSystemGraph model = Generator.model;
      this.order = this.findOrder(model, tile);
      Set<String> _ports = this.order.getPorts();
      TreeSet<String> ports = new TreeSet<String>(_ports);
      ports.remove("contained");
      for (final String portname : ports) {
        {
          Vertex actor = VertexAcessor.getNamedPort(
            Generator.model, 
            this.order, portname, 
            VertexTrait.MOC_SDF_SDFACTOR).orElse(null);
          slots.add(actor);
        }
      }
      InputOutput.<String>println(tile.getIdentifier());
      InputOutput.<String>println(this.order.getIdentifier());
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("#include \"subsystem_");
      String _identifier = tile.getIdentifier();
      _builder.append(_identifier);
      _builder.append(".h\"");
      _builder.newLineIfNotEmpty();
      _builder.append("#include \"../datatype/datatype_definition.h\"");
      _builder.newLine();
      _builder.append("#include \"../circular_fifo_lib/circular_fifo_lib.h\"");
      _builder.newLine();
      _builder.append("#include <cheap_s.h>");
      _builder.newLine();
      _builder.newLine();
      _builder.newLine();
      _builder.append("void subsystem_");
      String _identifier_1 = tile.getIdentifier();
      _builder.append(_identifier_1);
      _builder.append("(){");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("while(1){");
      _builder.newLine();
      {
        boolean _hasElements = false;
        for(final Vertex actor : slots) {
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
              _builder.append("xil_printf(\"fire actor ");
              String _identifier_2 = actor.getIdentifier();
              _builder.append(_identifier_2, "\t");
              _builder.append("\\n\");");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("actor_");
              String _name = Name.name(actor);
              _builder.append(_name, "\t");
              _builder.append("();");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("xil_printf(\"actor ");
              String _identifier_3 = actor.getIdentifier();
              _builder.append(_identifier_3, "\t");
              _builder.append(" ends\\n\");");
              _builder.newLineIfNotEmpty();
            }
          }
        }
        if (_hasElements) {
          _builder.append("");
        }
      }
      _builder.append("\t");
      _builder.append("}");
      _builder.newLine();
      _builder.append("}\t");
      _builder.newLine();
      _builder.newLine();
      _builder.append("int init_");
      String _identifier_4 = tile.getIdentifier();
      _builder.append(_identifier_4);
      _builder.append("(){");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("xil_printf(\"tile initialization starts\\n\");");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("/* extern */");
      _builder.newLine();
      _builder.append("\t");
      String _extern = this.extern(model, tile);
      _builder.append(_extern, "\t");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("/* Create the channels*/");
      _builder.newLine();
      _builder.append("\t");
      String _createChannel = this.createChannel(model, tile);
      _builder.append(_createChannel, "\t");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("/* SDF Delays */");
      _builder.newLine();
      _builder.append("\t");
      String _sdfdelay = this.sdfdelay(model, tile);
      _builder.append(_sdfdelay, "\t");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("/*wait util all other fifos are created*/");
      _builder.newLine();
      _builder.append("\t");
      String _wait = this.wait(model, tile);
      _builder.append(_wait, "\t");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
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
  
  public String extern(final ForSyDeSystemGraph model, final Vertex tile) {
    String ret = "";
    final Predicate<Vertex> _function = (Vertex v) -> {
      return (SDFChannel.conforms(v)).booleanValue();
    };
    Set<Vertex> channelSet = Generator.model.vertexSet().stream().filter(_function).collect(
      Collectors.<Vertex>toSet());
    final Predicate<Vertex> _function_1 = (Vertex v) -> {
      return (IntegerValue.conforms(v)).booleanValue();
    };
    final Function<Vertex, IntegerValue> _function_2 = (Vertex v) -> {
      return IntegerValue.safeCast(v).get();
    };
    Set<IntegerValue> integerValues = model.vertexSet().stream().filter(_function_1).<IntegerValue>map(_function_2).collect(Collectors.<IntegerValue>toSet());
    for (final IntegerValue value : integerValues) {
      String _ret = ret;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("extern int ");
      String _identifier = value.getIdentifier();
      _builder.append(_identifier);
      _builder.append(";");
      _builder.newLineIfNotEmpty();
      ret = (_ret + _builder);
    }
    for (final Vertex channel : channelSet) {
      {
        String channelname = channel.getIdentifier();
        MyPair pair = Query.findSrcAndSnkTileOfChannel(model, channel);
        String type = Query.findSDFChannelDataType(Generator.model, channel);
        if (((pair.src != null) && (pair.snk != null))) {
          if ((Objects.equal(pair.src, tile) && Objects.equal(pair.snk, tile))) {
            String _ret_1 = ret;
            StringConcatenation _builder_1 = new StringConcatenation();
            {
              if ((Generator.fifoType == 1)) {
                _builder_1.append("extern ");
                _builder_1.append(type);
                _builder_1.append(" buffer_");
                _builder_1.append(channelname);
                _builder_1.append("[];");
                _builder_1.newLineIfNotEmpty();
                _builder_1.append("extern int buffer_");
                _builder_1.append(channelname);
                _builder_1.append("_size;");
                _builder_1.newLineIfNotEmpty();
                _builder_1.append("extern circular_fifo_");
                _builder_1.append(type);
                _builder_1.append(" fifo_");
                _builder_1.append(channelname);
                _builder_1.append(";");
                _builder_1.newLineIfNotEmpty();
              }
            }
            {
              if ((Generator.fifoType == 2)) {
                _builder_1.append("extern ");
                _builder_1.append(type);
                _builder_1.append(" buffer_");
                _builder_1.append(channelname);
                _builder_1.append("[];");
                _builder_1.newLineIfNotEmpty();
                _builder_1.append("extern size_t buffer_");
                _builder_1.append(channelname);
                _builder_1.append("_size;");
                _builder_1.newLineIfNotEmpty();
                _builder_1.append("extern circular_fifo fifo_");
                _builder_1.append(channelname);
                _builder_1.append(";");
                _builder_1.newLineIfNotEmpty();
              }
            }
            ret = (_ret_1 + _builder_1);
          }
          if ((Objects.equal(pair.src, tile) && (pair.snk != tile))) {
            String _ret_2 = ret;
            StringConcatenation _builder_2 = new StringConcatenation();
            _builder_2.append("extern cheap fifo_admin_");
            _builder_2.append(channelname);
            _builder_2.append(";");
            _builder_2.newLineIfNotEmpty();
            _builder_2.append("extern volatile ");
            _builder_2.append(type);
            _builder_2.append(" * const fifo_data_");
            _builder_2.append(channelname);
            _builder_2.append(";");
            _builder_2.newLineIfNotEmpty();
            _builder_2.append("extern size_t  buffer_");
            _builder_2.append(channelname);
            _builder_2.append("_size;");
            _builder_2.newLineIfNotEmpty();
            _builder_2.append("extern size_t token_");
            _builder_2.append(channelname);
            _builder_2.append("_size;");
            _builder_2.newLineIfNotEmpty();
            ret = (_ret_2 + _builder_2);
          }
          if (((pair.src != tile) && Objects.equal(pair.snk, tile))) {
            String _ret_3 = ret;
            StringConcatenation _builder_3 = new StringConcatenation();
            _builder_3.append("extern cheap fifo_admin_");
            _builder_3.append(channelname);
            _builder_3.append(";");
            _builder_3.newLineIfNotEmpty();
            _builder_3.append("extern volatile ");
            _builder_3.append(type);
            _builder_3.append(" * const fifo_data_");
            _builder_3.append(channelname);
            _builder_3.append(";");
            _builder_3.newLineIfNotEmpty();
            _builder_3.append("extern size_t buffer_");
            _builder_3.append(channelname);
            _builder_3.append("_size;");
            _builder_3.newLineIfNotEmpty();
            _builder_3.append("extern size_t token_");
            _builder_3.append(channelname);
            _builder_3.append("_size;");
            _builder_3.newLineIfNotEmpty();
            ret = (_ret_3 + _builder_3);
          }
        } else {
          if ((pair.src == null)) {
            boolean _equals = Objects.equal(pair.snk, tile);
            if (_equals) {
              String _ret_4 = ret;
              StringConcatenation _builder_4 = new StringConcatenation();
              {
                if ((Generator.fifoType == 1)) {
                  _builder_4.append("extern ");
                  _builder_4.append(type);
                  _builder_4.append(" buffer_");
                  _builder_4.append(channelname);
                  _builder_4.append("[];");
                  _builder_4.newLineIfNotEmpty();
                  _builder_4.append("extern int buffer_");
                  _builder_4.append(channelname);
                  _builder_4.append("_size;");
                  _builder_4.newLineIfNotEmpty();
                  _builder_4.append("extern circular_fifo_");
                  _builder_4.append(type);
                  _builder_4.append(" fifo_");
                  _builder_4.append(channelname);
                  _builder_4.append(";");
                  _builder_4.newLineIfNotEmpty();
                }
              }
              {
                if ((Generator.fifoType == 2)) {
                  _builder_4.append("extern ");
                  _builder_4.append(type);
                  _builder_4.append(" buffer_");
                  _builder_4.append(channelname);
                  _builder_4.append("[];");
                  _builder_4.newLineIfNotEmpty();
                  _builder_4.append("extern size_t buffer_");
                  _builder_4.append(channelname);
                  _builder_4.append("_size;");
                  _builder_4.newLineIfNotEmpty();
                  _builder_4.append("extern circular_fifo fifo_");
                  _builder_4.append(channelname);
                  _builder_4.append(";");
                  _builder_4.newLineIfNotEmpty();
                }
              }
              ret = (_ret_4 + _builder_4);
            }
          }
          if ((pair.snk == null)) {
            boolean _equals_1 = Objects.equal(pair.src, tile);
            if (_equals_1) {
              String _ret_5 = ret;
              StringConcatenation _builder_5 = new StringConcatenation();
              {
                if ((Generator.fifoType == 1)) {
                  _builder_5.append("extern ");
                  _builder_5.append(type);
                  _builder_5.append(" buffer_");
                  _builder_5.append(channelname);
                  _builder_5.append("[];");
                  _builder_5.newLineIfNotEmpty();
                  _builder_5.append("extern int buffer_");
                  _builder_5.append(channelname);
                  _builder_5.append("_size;");
                  _builder_5.newLineIfNotEmpty();
                  _builder_5.append("extern circular_fifo_");
                  _builder_5.append(type);
                  _builder_5.append(" fifo_");
                  _builder_5.append(channelname);
                  _builder_5.append(";");
                  _builder_5.newLineIfNotEmpty();
                }
              }
              {
                if ((Generator.fifoType == 2)) {
                  _builder_5.append("extern ");
                  _builder_5.append(type);
                  _builder_5.append(" buffer_");
                  _builder_5.append(channelname);
                  _builder_5.append("[];");
                  _builder_5.newLineIfNotEmpty();
                  _builder_5.append("extern size_t buffer_");
                  _builder_5.append(channelname);
                  _builder_5.append("_size;");
                  _builder_5.newLineIfNotEmpty();
                  _builder_5.append("extern circular_fifo fifo_");
                  _builder_5.append(channelname);
                  _builder_5.append(";");
                  _builder_5.newLineIfNotEmpty();
                }
              }
              ret = (_ret_5 + _builder_5);
            }
          }
        }
      }
    }
    return ret;
  }
  
  public String createChannel(final ForSyDeSystemGraph model, final Vertex tile) {
    String ret = "";
    final Predicate<Vertex> _function = (Vertex v) -> {
      return (SDFChannel.conforms(v)).booleanValue();
    };
    Set<Vertex> channelSet = Generator.model.vertexSet().stream().filter(_function).collect(
      Collectors.<Vertex>toSet());
    for (final Vertex channel : channelSet) {
      {
        String channelname = channel.getIdentifier();
        MyPair pair = Query.findSrcAndSnkTileOfChannel(model, channel);
        if (((pair.src != null) && (pair.snk != null))) {
          if ((Objects.equal(pair.src, tile) && Objects.equal(pair.snk, tile))) {
            String _ret = ret;
            StringConcatenation _builder = new StringConcatenation();
            {
              if ((Generator.fifoType == 1)) {
                _builder.append("init_fifo_");
                String _findSDFChannelDataType = Query.findSDFChannelDataType(Generator.model, channel);
                _builder.append(_findSDFChannelDataType);
                _builder.append("(&fifo_");
                _builder.append(channelname);
                _builder.append(",buffer_");
                _builder.append(channelname);
                _builder.append(",buffer_");
                _builder.append(channelname);
                _builder.append("_size);");
                _builder.newLineIfNotEmpty();
              }
            }
            {
              if ((Generator.fifoType == 2)) {
                _builder.append("init_fifo(&fifo_");
                _builder.append(channelname);
                _builder.append(",buffer_");
                _builder.append(channelname);
                _builder.append(",buffer_");
                _builder.append(channelname);
                _builder.append("_size, sizeof(");
                String _findSDFChannelDataType_1 = Query.findSDFChannelDataType(Generator.model, channel);
                _builder.append(_findSDFChannelDataType_1);
                _builder.append("));");
                _builder.newLineIfNotEmpty();
              }
            }
            ret = (_ret + _builder);
          }
          if ((Objects.equal(pair.src, tile) && (pair.snk != tile))) {
            String _ret_1 = ret;
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.append("if (cheap_init_r (fifo_admin_");
            _builder_1.append(channelname);
            _builder_1.append(", (void *) fifo_data_");
            _builder_1.append(channelname);
            _builder_1.append(", buffer_");
            _builder_1.append(channelname);
            _builder_1.append("_size, token_");
            _builder_1.append(channelname);
            _builder_1.append("_size) == NULL) {");
            _builder_1.newLineIfNotEmpty();
            _builder_1.append("\t");
            _builder_1.append("//xil_printf(\"%04u/%010u: cheap_init_r failed\\n\", (uint32_t)(t>>32),(uint32_t)t);");
            _builder_1.newLine();
            _builder_1.append("\t");
            _builder_1.append("return 1;");
            _builder_1.newLine();
            _builder_1.append("}\t\t\t\t");
            _builder_1.newLine();
            ret = (_ret_1 + _builder_1);
          }
        } else {
          if ((pair.src == null)) {
            boolean _equals = Objects.equal(pair.snk, tile);
            if (_equals) {
              String _ret_2 = ret;
              StringConcatenation _builder_2 = new StringConcatenation();
              {
                if ((Generator.fifoType == 1)) {
                  _builder_2.append("init_fifo_");
                  String _findSDFChannelDataType_2 = Query.findSDFChannelDataType(Generator.model, channel);
                  _builder_2.append(_findSDFChannelDataType_2);
                  _builder_2.append("(&fifo_");
                  _builder_2.append(channelname);
                  _builder_2.append(",buffer_");
                  _builder_2.append(channelname);
                  _builder_2.append(",buffer_");
                  _builder_2.append(channelname);
                  _builder_2.append("_size);");
                  _builder_2.newLineIfNotEmpty();
                }
              }
              {
                if ((Generator.fifoType == 2)) {
                  _builder_2.append("init_fifo(&fifo_");
                  _builder_2.append(channelname);
                  _builder_2.append(",buffer_");
                  _builder_2.append(channelname);
                  _builder_2.append(",buffer_");
                  _builder_2.append(channelname);
                  _builder_2.append("_size, sizeof(");
                  String _findSDFChannelDataType_3 = Query.findSDFChannelDataType(Generator.model, channel);
                  _builder_2.append(_findSDFChannelDataType_3);
                  _builder_2.append("));");
                  _builder_2.newLineIfNotEmpty();
                }
              }
              ret = (_ret_2 + _builder_2);
            }
          }
          if ((pair.snk == null)) {
            boolean _equals_1 = Objects.equal(pair.src, tile);
            if (_equals_1) {
              String _ret_3 = ret;
              StringConcatenation _builder_3 = new StringConcatenation();
              {
                if ((Generator.fifoType == 1)) {
                  _builder_3.append("init_fifo_");
                  String _findSDFChannelDataType_4 = Query.findSDFChannelDataType(Generator.model, channel);
                  _builder_3.append(_findSDFChannelDataType_4);
                  _builder_3.append("(&fifo_");
                  _builder_3.append(channelname);
                  _builder_3.append(",buffer_");
                  _builder_3.append(channelname);
                  _builder_3.append(",buffer_");
                  _builder_3.append(channelname);
                  _builder_3.append("_size);");
                  _builder_3.newLineIfNotEmpty();
                }
              }
              {
                if ((Generator.fifoType == 2)) {
                  _builder_3.append("init_fifo(&fifo_");
                  _builder_3.append(channelname);
                  _builder_3.append(",buffer_");
                  _builder_3.append(channelname);
                  _builder_3.append(",buffer_");
                  _builder_3.append(channelname);
                  _builder_3.append("_size, sizeof(");
                  String _findSDFChannelDataType_5 = Query.findSDFChannelDataType(Generator.model, channel);
                  _builder_3.append(_findSDFChannelDataType_5);
                  _builder_3.append("));");
                  _builder_3.newLineIfNotEmpty();
                }
              }
              ret = (_ret_3 + _builder_3);
            }
          }
        }
      }
    }
    return ret;
  }
  
  public String sdfdelay(final ForSyDeSystemGraph model, final Vertex tile) {
    String ret = "";
    final Predicate<Vertex> _function = (Vertex v) -> {
      return (SDFChannel.conforms(v)).booleanValue();
    };
    Set<Vertex> channelSet = Generator.model.vertexSet().stream().filter(_function).collect(
      Collectors.<Vertex>toSet());
    for (final Vertex channel : channelSet) {
      {
        String sdfchannelName = channel.getIdentifier();
        MyPair pair = Query.findSrcAndSnkTileOfChannel(model, channel);
        SDFChannel sdfchannel = SDFChannel.safeCast(channel).get();
        String datatype = Query.findSDFChannelDataType(model, channel);
        if (((pair.src != null) && (pair.snk != null))) {
          if ((Objects.equal(pair.src, tile) && Objects.equal(pair.snk, tile))) {
            if (((sdfchannel.getNumOfInitialTokens() != null) && ((sdfchannel.getNumOfInitialTokens()).intValue() > 0))) {
              Object _unwrap = sdfchannel.getProperties().get("__initialTokenValues_ordering__").unwrap();
              HashMap<String, Integer> ordering = ((HashMap<String, Integer>) _unwrap);
              int _size = ordering.size();
              boolean _greaterThan = (_size > 0);
              if (_greaterThan) {
                ArrayList<String> initList = this.help(ordering);
                for (final String valueName : initList) {
                  String _ret = ret;
                  StringConcatenation _builder = new StringConcatenation();
                  {
                    if ((Generator.fifoType == 1)) {
                      _builder.append("write_fifo_");
                      String _findSDFChannelDataType = Query.findSDFChannelDataType(Generator.model, channel);
                      _builder.append(_findSDFChannelDataType);
                      _builder.append("(&fifo_");
                      String _identifier = sdfchannel.getIdentifier();
                      _builder.append(_identifier);
                      _builder.append(",&");
                      _builder.append(valueName);
                      _builder.append(",1);");
                      _builder.newLineIfNotEmpty();
                    }
                  }
                  {
                    if ((Generator.fifoType == 2)) {
                      _builder.append("write_fifo(&fifo_");
                      String _identifier_1 = sdfchannel.getIdentifier();
                      _builder.append(_identifier_1);
                      _builder.append(",&");
                      _builder.append(valueName);
                      _builder.append(",1);");
                      _builder.newLineIfNotEmpty();
                    }
                  }
                  ret = (_ret + _builder);
                }
              }
            }
          }
          if ((Objects.equal(pair.src, tile) && (pair.snk != tile))) {
            if (((sdfchannel.getNumOfInitialTokens() != null) && ((sdfchannel.getNumOfInitialTokens()).intValue() > 0))) {
              Object _unwrap_1 = sdfchannel.getProperties().get("__initialTokenValues_ordering__").unwrap();
              HashMap<String, Integer> ordering_1 = ((HashMap<String, Integer>) _unwrap_1);
              int _size_1 = ordering_1.size();
              boolean _greaterThan_1 = (_size_1 > 0);
              if (_greaterThan_1) {
                ArrayList<String> initList_1 = this.help(ordering_1);
                String _ret_1 = ret;
                StringConcatenation _builder_1 = new StringConcatenation();
                _builder_1.append("volatile ");
                _builder_1.append(datatype);
                _builder_1.append(" *tmp_ptrs[");
                int _size_2 = initList_1.size();
                _builder_1.append(_size_2);
                _builder_1.append("];");
                _builder_1.newLineIfNotEmpty();
                _builder_1.append("while ((cheap_claim_spaces (fifo_admin_");
                _builder_1.append(sdfchannelName);
                _builder_1.append(", (volatile void **) &tmp_ptrs[0], ");
                int _size_3 = initList_1.size();
                _builder_1.append(_size_3);
                _builder_1.append(")) < ");
                int _size_4 = initList_1.size();
                _builder_1.append(_size_4);
                _builder_1.append(")");
                _builder_1.newLineIfNotEmpty();
                _builder_1.append("\t");
                _builder_1.append("cheap_release_all_claimed_spaces (fifo_admin_");
                _builder_1.append(sdfchannelName, "\t");
                _builder_1.append(");\t\t\t\t\t\t\t");
                _builder_1.newLineIfNotEmpty();
                ret = (_ret_1 + _builder_1);
                int i = 0;
                for (final String valueName_1 : initList_1) {
                  {
                    String _ret_2 = ret;
                    StringConcatenation _builder_2 = new StringConcatenation();
                    _builder_2.append("*tmp_ptrs[");
                    _builder_2.append(i);
                    _builder_2.append("]=");
                    _builder_2.append(valueName_1);
                    _builder_2.append(";");
                    _builder_2.newLineIfNotEmpty();
                    ret = (_ret_2 + _builder_2);
                    i = (i + 1);
                  }
                }
                String _ret_2 = ret;
                StringConcatenation _builder_2 = new StringConcatenation();
                _builder_2.append("cheap_release_tokens (fifo_admin_");
                _builder_2.append(sdfchannelName);
                _builder_2.append(", ");
                int _size_5 = initList_1.size();
                _builder_2.append(_size_5);
                _builder_2.append(");");
                _builder_2.newLineIfNotEmpty();
                ret = (_ret_2 + _builder_2);
              }
            }
          }
        }
      }
    }
    return ret;
  }
  
  public String wait(final ForSyDeSystemGraph model, final Vertex tile) {
    String ret = "";
    final Predicate<Vertex> _function = (Vertex v) -> {
      return (SDFChannel.conforms(v)).booleanValue();
    };
    Set<Vertex> channelSet = Generator.model.vertexSet().stream().filter(_function).collect(
      Collectors.<Vertex>toSet());
    for (final Vertex channel : channelSet) {
      {
        MyPair pair = Query.findSrcAndSnkTileOfChannel(model, channel);
        if (((Objects.equal(pair.snk, tile) && (pair.src != null)) && (pair.src != tile))) {
          String _ret = ret;
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("while (cheap_get_buffer_capacity (fifo_admin_");
          String _identifier = channel.getIdentifier();
          _builder.append(_identifier);
          _builder.append(") == 0); ");
          _builder.newLineIfNotEmpty();
          ret = (_ret + _builder);
        }
      }
    }
    return ret;
  }
  
  @Override
  public String savePath() {
    String _identifier = this.tile.getIdentifier();
    String _plus = ("/" + _identifier);
    String _plus_1 = (_plus + "/subsystem_");
    String _identifier_1 = this.tile.getIdentifier();
    String _plus_2 = (_plus_1 + _identifier_1);
    return (_plus_2 + ".c");
  }
  
  public ArrayList<String> help(final HashMap<String, Integer> delays) {
    int numOfInitialToken = delays.size();
    ArrayList<String> delayValueList = new ArrayList<String>();
    for (int i = 0; (i < numOfInitialToken); i = (i + 1)) {
      delayValueList.add("");
    }
    Set<String> _keySet = delays.keySet();
    for (final String k : _keySet) {
      delayValueList.set((delays.get(k)).intValue(), k);
    }
    return delayValueList;
  }
  
  public Vertex findOrder(final ForSyDeSystemGraph model, final Vertex tile) {
    Vertex order = null;
    if ((order == null)) {
      order = this.helpfindOrder(VertexTrait.PLATFORM_RUNTIME_FIXEDPRIORITYSCHEDULER);
    }
    if ((order == null)) {
      order = this.helpfindOrder(VertexTrait.PLATFORM_RUNTIME_ROUNDROBINSCHEDULER);
    }
    if ((order == null)) {
      order = this.helpfindOrder(VertexTrait.PLATFORM_RUNTIME_STATICCYCLICSCHEDULER);
    }
    if ((order == null)) {
      order = this.helpfindOrder(VertexTrait.PLATFORM_RUNTIME_STATICCYCLICSCHEDULER);
    }
    if ((order == null)) {
      order = this.helpfindOrder(VertexTrait.PLATFORM_RUNTIME_TIMETRIGGEREDSCHEDULER);
    }
    return order;
  }
  
  public Vertex helpfindOrder(final Trait a) {
    return VertexAcessor.getNamedPort(
      Generator.model, 
      this.tile, 
      "execution", a).orElse(null);
  }
}
