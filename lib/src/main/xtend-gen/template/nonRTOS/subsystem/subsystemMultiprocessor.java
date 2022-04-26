package template.nonRTOS.subsystem;

import com.google.common.base.Objects;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.core.VertexAcessor;
import forsyde.io.java.core.VertexTrait;
import forsyde.io.java.typed.viewers.platform.GenericProcessingModule;
import generator.generator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.Template;
import utils.Global;
import utils.Name;
import utils.Save;

@SuppressWarnings("all")
public class subsystemMultiprocessor implements Template {
  public subsystemMultiprocessor() {
  }
  
  public void create() {
    final Predicate<Vertex> _function = new Predicate<Vertex>() {
      public boolean test(final Vertex v) {
        return (GenericProcessingModule.conforms(v)).booleanValue();
      }
    };
    Set<Vertex> tiles = Global.model.vertexSet().stream().filter(_function).collect(Collectors.<Vertex>toSet());
    final Consumer<Vertex> _function_1 = new Consumer<Vertex>() {
      public void accept(final Vertex t) {
        subsystemMultiprocessor.this.help(subsystemMultiprocessor.this.getScheduler(t), t);
      }
    };
    tiles.stream().forEach(_function_1);
  }
  
  public boolean help(final Vertex scheduler, final Vertex tile) {
    boolean _xblockexpression = false;
    {
      Set<String> _ports = scheduler.getPorts();
      TreeSet<String> ports = new TreeSet<String>(_ports);
      ports.remove("contained");
      int _size = ports.size();
      ArrayList<Vertex> firingSlots = new ArrayList<Vertex>(_size);
      Iterator<String> iter = ports.iterator();
      while (iter.hasNext()) {
        {
          String slotPortName = iter.next();
          Optional<Vertex> optional_actor = VertexAcessor.getNamedPort(Global.model, scheduler, slotPortName, VertexTrait.MOC_SDF_SDFCOMB, VertexAcessor.VertexPortDirection.OUTGOING);
          boolean _isPresent = optional_actor.isPresent();
          if (_isPresent) {
            firingSlots.add(optional_actor.get());
          }
        }
      }
      Set<Vertex> channels = new HashSet<Vertex>();
      for (final Vertex actor : firingSlots) {
        Set<String> _ports_1 = actor.getPorts();
        for (final String port : _ports_1) {
          if (((!Objects.equal(port, "combinator")) && (!Objects.equal(port, "combFunction")))) {
            Optional<Vertex> channel = VertexAcessor.getNamedPort(Global.model, actor, port, VertexTrait.MOC_SDF_SDFCHANNEL, VertexAcessor.VertexPortDirection.BIDIRECTIONAL);
            boolean _isPresent = channel.isPresent();
            if (_isPresent) {
              channels.add(channel.get());
            }
          }
        }
      }
      String _identifier = tile.getIdentifier();
      String _plus = ((generator.root + "/src/subsystem_") + _identifier);
      String _plus_1 = (_plus + ".c");
      Save.save(_plus_1, this.src(tile, firingSlots, channels));
      String _identifier_1 = tile.getIdentifier();
      String _plus_2 = ((generator.root + "/inc/subsystem_") + _identifier_1);
      String _plus_3 = (_plus_2 + ".h");
      _xblockexpression = Save.save(_plus_3, this.inc(tile, firingSlots, channels));
    }
    return _xblockexpression;
  }
  
  public String src(final Vertex tile, final ArrayList<Vertex> firingSlots, final Set<Vertex> channels) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#include \"../inc/subsystem_");
    String _name = Name.name(tile);
    _builder.append(_name);
    _builder.append(".h\"");
    _builder.newLineIfNotEmpty();
    {
      boolean _hasElements = false;
      for(final Vertex channel : channels) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate("\n", "");
        }
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
        _builder.append("");
      }
    }
    _builder.newLine();
    _builder.append("void subsystem_");
    String _identifier = tile.getIdentifier();
    _builder.append(_identifier);
    _builder.append("(){");
    _builder.newLineIfNotEmpty();
    {
      boolean _hasElements_1 = false;
      for(final Vertex channel_1 : channels) {
        if (!_hasElements_1) {
          _hasElements_1 = true;
        } else {
          _builder.appendImmediate("", "\t");
        }
        _builder.append("\t");
        String channelName_1 = Name.name(channel_1);
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
      if (_hasElements_1) {
        _builder.append("", "\t");
      }
    }
    _builder.append("\t");
    String _sdfDelayHelpA = subsystemHelp.sdfDelayHelpA(channels);
    _builder.append(_sdfDelayHelpA, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("while(1){");
    _builder.newLine();
    {
      boolean _hasElements_2 = false;
      for(final Vertex actor : firingSlots) {
        if (!_hasElements_2) {
          _hasElements_2 = true;
        } else {
          _builder.appendImmediate("", "\t\t");
        }
        _builder.append("\t\t");
        _builder.append("actor_");
        String _name_1 = Name.name(actor);
        _builder.append(_name_1, "\t\t");
        _builder.append("(");
        String _actorParameter = subsystemHelp.actorParameter(actor);
        _builder.append(_actorParameter, "\t\t");
        _builder.append(");");
        _builder.newLineIfNotEmpty();
      }
      if (_hasElements_2) {
        _builder.append("\n", "\t\t");
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder.toString();
  }
  
  public String inc(final Vertex tile, final ArrayList<Vertex> firingSlots, final Set<Vertex> channels) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#ifndef SUBSYSTEM_");
    String _upperCase = Name.name(tile).toUpperCase();
    _builder.append(_upperCase);
    _builder.append("_H_");
    _builder.newLineIfNotEmpty();
    _builder.append("#define SUBSYSTEM_");
    String _upperCase_1 = Name.name(tile).toUpperCase();
    _builder.append(_upperCase_1);
    _builder.append("_H_");
    _builder.newLineIfNotEmpty();
    _builder.append("#include<stdlib.h>");
    _builder.newLine();
    _builder.append("#include <stdio.h>\t");
    _builder.newLine();
    {
      boolean _hasElements = false;
      for(final Vertex actor : firingSlots) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(" ", "");
        }
        _builder.append("#include \"../inc/sdfcomb_");
        String _name = Name.name(actor);
        _builder.append(_name);
        _builder.append(".h\"");
        _builder.newLineIfNotEmpty();
      }
      if (_hasElements) {
        _builder.append("");
      }
    }
    {
      boolean _hasElements_1 = false;
      for(final Vertex channel : channels) {
        if (!_hasElements_1) {
          _hasElements_1 = true;
        } else {
          _builder.appendImmediate(" ", "");
        }
        _builder.append("#include \"../inc/sdfchannel_");
        String _name_1 = Name.name(channel);
        _builder.append(_name_1);
        _builder.append(".h\"");
        _builder.newLineIfNotEmpty();
      }
      if (_hasElements_1) {
        _builder.append("");
      }
    }
    _builder.newLine();
    _builder.append("void subsystem_");
    String _name_2 = Name.name(tile);
    _builder.append(_name_2);
    _builder.append("();");
    _builder.newLineIfNotEmpty();
    _builder.append("#endif\t\t\t");
    _builder.newLine();
    return _builder.toString();
  }
  
  public Vertex getScheduler(final Vertex tile) {
    Optional<Vertex> order = VertexAcessor.getNamedPort(Global.model, tile, "contained", VertexTrait.PLATFORM_RUNTIME_TIMETRIGGEREDSCHEDULER, VertexAcessor.VertexPortDirection.OUTGOING);
    Vertex scheduler = null;
    boolean _isPresent = order.isPresent();
    if (_isPresent) {
      scheduler = order.get();
      return scheduler;
    } else {
      return null;
    }
  }
}
