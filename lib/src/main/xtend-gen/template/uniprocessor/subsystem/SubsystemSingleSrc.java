package template.uniprocessor.subsystem;

import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.core.VertexProperty;
import forsyde.io.java.typed.viewers.moc.sdf.SDFActor;
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel;
import forsyde.io.java.typed.viewers.values.IntegerValue;
import generator.Generator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.templateInterface.SubsystemTemplate;
import utils.Query;

@SuppressWarnings("all")
public class SubsystemSingleSrc implements SubsystemTemplate {
  private Set<Vertex> sdfactorSet;
  
  private TreeMap<Integer, Vertex> uniprocessorSchedule;
  
  @Override
  public String savePath() {
    return "/tile/subsystem.c";
  }
  
  @Override
  public String create(final Vertex tile) {
    String _xblockexpression = null;
    {
      ForSyDeSystemGraph model = Generator.model;
      final Predicate<Vertex> _function = (Vertex v) -> {
        return (SDFActor.conforms(v)).booleanValue();
      };
      this.sdfactorSet = model.vertexSet().stream().filter(_function).collect(Collectors.<Vertex>toSet());
      final Predicate<Vertex> _function_1 = (Vertex v) -> {
        return (IntegerValue.conforms(v)).booleanValue();
      };
      final Function<Vertex, IntegerValue> _function_2 = (Vertex v) -> {
        return IntegerValue.safeCast(v).get();
      };
      Set<IntegerValue> integerValues = model.vertexSet().stream().filter(_function_1).<IntegerValue>map(_function_2).collect(Collectors.<IntegerValue>toSet());
      this.createUniprocessorSchedule();
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("#include \"subsystem.h\"");
      _builder.newLine();
      _builder.append("#include <stdio.h>");
      _builder.newLine();
      {
        for(final Vertex v : this.sdfactorSet) {
          _builder.append("#include \"./sdfactor/sdfactor_");
          String _identifier = v.getIdentifier();
          _builder.append(_identifier);
          _builder.append(".h\"");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.append("#include \"../datatype/datatype_definition.h\"");
      _builder.newLine();
      _builder.append("#include \"../circular_fifo_lib/circular_fifo_lib.h\"");
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
      _builder.append("\t");
      _builder.append("while(1){");
      _builder.newLine();
      {
        Set<Map.Entry<Integer, Vertex>> _entrySet = this.uniprocessorSchedule.entrySet();
        boolean _hasElements = false;
        for(final Map.Entry<Integer, Vertex> set : _entrySet) {
          if (!_hasElements) {
            _hasElements = true;
          } else {
            _builder.appendImmediate("", "\t\t");
          }
          _builder.append("\t\t");
          _builder.append("actor_");
          String _identifier_1 = set.getValue().getIdentifier();
          _builder.append(_identifier_1, "\t\t");
          _builder.append("();");
          _builder.newLineIfNotEmpty();
        }
        if (_hasElements) {
          _builder.append("", "\t\t");
        }
      }
      _builder.append("\t");
      _builder.append("}");
      _builder.newLine();
      _builder.newLine();
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
          String _identifier_2 = value.getIdentifier();
          _builder.append(_identifier_2, "\t");
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
              _builder.append("init_fifo_");
              String _findSDFChannelDataType = Query.findSDFChannelDataType(Generator.model, channel);
              _builder.append(_findSDFChannelDataType);
              _builder.append("(&fifo_");
              _builder.append(sdfname);
              _builder.append(",buffer_");
              _builder.append(sdfname);
              _builder.append(",buffer_");
              _builder.append(sdfname);
              _builder.append("_size);");
              _builder.newLineIfNotEmpty();
            }
          }
          {
            if ((Generator.fifoType == 2)) {
              _builder.append("\t\t");
              _builder.append("init_fifo(&fifo_");
              _builder.append(sdfname, "\t\t");
              _builder.append(",buffer_");
              _builder.append(sdfname, "\t\t");
              _builder.append(",buffer_");
              _builder.append(sdfname, "\t\t");
              _builder.append("_size, sizeof(");
              String _findSDFChannelDataType_1 = Query.findSDFChannelDataType(Generator.model, channel);
              _builder.append(_findSDFChannelDataType_1, "\t\t");
              _builder.append("));");
              _builder.newLineIfNotEmpty();
            }
          }
        }
      }
      _builder.append("\t\t");
      _builder.newLine();
      {
        for(final Vertex channel_1 : Generator.sdfchannelSet) {
          _builder.append("\t\t");
          SDFChannel sdfchannel = SDFChannel.safeCast(channel_1).get();
          _builder.newLineIfNotEmpty();
          {
            if (((sdfchannel.getNumOfInitialTokens() != null) && ((sdfchannel.getNumOfInitialTokens()).intValue() > 0))) {
              _builder.append("\t\t");
              ArrayList<String> delayValueList = this.inithelp(sdfchannel);
              _builder.newLineIfNotEmpty();
              {
                for(final String delay : delayValueList) {
                  {
                    if ((Generator.fifoType == 1)) {
                      _builder.append("\t\t");
                      _builder.append("write_fifo_");
                      String _findSDFChannelDataType_2 = Query.findSDFChannelDataType(Generator.model, channel_1);
                      _builder.append(_findSDFChannelDataType_2, "\t\t");
                      _builder.append("(&fifo_");
                      String _identifier_3 = sdfchannel.getIdentifier();
                      _builder.append(_identifier_3, "\t\t");
                      _builder.append(",&");
                      _builder.append(delay, "\t\t");
                      _builder.append(",1);");
                      _builder.newLineIfNotEmpty();
                    }
                  }
                  {
                    if ((Generator.fifoType == 2)) {
                      _builder.append("\t\t");
                      _builder.append("write_fifo(&fifo_");
                      String _identifier_4 = sdfchannel.getIdentifier();
                      _builder.append(_identifier_4, "\t\t");
                      _builder.append(",(void*)&");
                      _builder.append(delay, "\t\t");
                      _builder.append(",1);");
                      _builder.newLineIfNotEmpty();
                    }
                  }
                }
              }
            }
          }
          _builder.append("\t\t\t");
          _builder.newLine();
          _builder.append("\t\t");
          _builder.append("\t");
          _builder.newLine();
        }
      }
      _builder.append("return 0;");
      _builder.newLine();
      _builder.append("}\t\t");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.newLine();
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
  
  public ArrayList<String> inithelp(final SDFChannel sdfchannel) {
    Integer numOfInitialToken = sdfchannel.getNumOfInitialTokens();
    Object _unwrap = sdfchannel.getProperties().get("__initialTokenValues_ordering__").unwrap();
    HashMap<String, Integer> delays = ((HashMap<String, Integer>) _unwrap);
    ArrayList<String> delayValueList = new ArrayList<String>();
    for (int i = 0; (i < (numOfInitialToken).intValue()); i = (i + 1)) {
      delayValueList.add("");
    }
    Set<String> _keySet = delays.keySet();
    for (final String k : _keySet) {
      delayValueList.set((delays.get(k)).intValue(), k);
    }
    return delayValueList;
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
            _builder.append("extern ");
            _builder.append(type);
            _builder.append(" buffer_");
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
        _builder.newLine();
      }
    }
    return _builder.toString();
  }
  
  public void createUniprocessorSchedule() {
    TreeMap<Integer, Vertex> _treeMap = new TreeMap<Integer, Vertex>();
    this.uniprocessorSchedule = _treeMap;
    for (final Vertex actor : this.sdfactorSet) {
      {
        ArrayList<Integer> tmp = this.getFiringSlot(actor);
        for (int i = 0; (i < tmp.size()); i = (i + 1)) {
          this.uniprocessorSchedule.put(tmp.get(i), actor);
        }
      }
    }
  }
  
  private ArrayList<Integer> getFiringSlot(final Vertex actor) {
    VertexProperty firingSlots = actor.getProperties().get("firingSlots");
    if ((firingSlots != null)) {
      Object _unwrap = firingSlots.unwrap();
      ArrayList<Integer> slot = ((ArrayList<Integer>) _unwrap);
      return slot;
    }
    return null;
  }
}
