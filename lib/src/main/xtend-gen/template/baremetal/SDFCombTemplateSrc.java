package template.baremetal;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.core.VertexAcessor;
import forsyde.io.java.core.VertexTrait;
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb;
import generator.Generator;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;
import template.templateInterface.ActorTemplate;
import utils.Query;

@FileTypeAnno(type = FileType.C_SOURCE)
@SuppressWarnings("all")
public class SDFCombTemplateSrc implements ActorTemplate {
  private Set<Vertex> implActorSet;
  
  private Set<Vertex> inputSDFChannelSet;
  
  private Set<Vertex> outputSDFChannelSet;
  
  public String create(final Vertex actor) {
    String _xblockexpression = null;
    {
      ForSyDeSystemGraph model = Generator.model;
      this.implActorSet = VertexAcessor.getMultipleNamedPort(Generator.model, actor, "combFunctions", 
        VertexTrait.IMPL_ANSICBLACKBOXEXECUTABLE, VertexAcessor.VertexPortDirection.OUTGOING);
      this.inputSDFChannelSet = Query.findInputSDFChannels(model, actor);
      this.outputSDFChannelSet = Query.findOutputSDFChannels(model, actor);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("/* Includes-------------------------- */");
      _builder.newLine();
      _builder.append("#include \"../inc/datatype_definition.h\"");
      _builder.newLine();
      String name = actor.getIdentifier();
      _builder.newLineIfNotEmpty();
      _builder.append("/*");
      _builder.newLine();
      _builder.append("========================================");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("Declare Extern Channal Variables");
      _builder.newLine();
      _builder.append("========================================");
      _builder.newLine();
      _builder.append("*/");
      _builder.newLine();
      String _extern = this.extern();
      _builder.append(_extern);
      _builder.newLineIfNotEmpty();
      _builder.append("/*");
      _builder.newLine();
      _builder.append("========================================");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("Actor Function");
      _builder.newLine();
      _builder.append("========================================");
      _builder.newLine();
      _builder.append("*/\t\t\t");
      _builder.newLine();
      _builder.append("inline void actor_");
      _builder.append(name);
      _builder.append("(){");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("/* Initilize Memory      */");
      _builder.newLine();
      _builder.append("\t");
      String _initMemory = this.initMemory(actor);
      _builder.append(_initMemory, "\t");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("/* Read From Input Port  */");
      _builder.newLine();
      _builder.append("\t");
      String _read = this.read(actor);
      _builder.append(_read, "\t");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("/* Inline Code           */");
      _builder.newLine();
      _builder.append("\t");
      String _inlineCode = this.getInlineCode();
      _builder.append(_inlineCode, "\t");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("/* Write To Output Ports */");
      _builder.newLine();
      _builder.append("\t");
      String _write = this.write(actor);
      _builder.append(_write, "\t");
      _builder.newLineIfNotEmpty();
      _builder.append("}");
      _builder.newLine();
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
  
  public String extern() {
    String _xblockexpression = null;
    {
      Set<Vertex> record = new HashSet<Vertex>();
      StringConcatenation _builder = new StringConcatenation();
      {
        boolean _hasElements = false;
        for(final Vertex sdf : this.inputSDFChannelSet) {
          if (!_hasElements) {
            _hasElements = true;
          } else {
            _builder.appendImmediate("", "");
          }
          {
            boolean _contains = record.contains(sdf);
            boolean _not = (!_contains);
            if (_not) {
              _builder.append("extern fifo_");
              String _identifier = sdf.getIdentifier();
              _builder.append(_identifier);
              _builder.append(";");
              _builder.newLineIfNotEmpty();
              boolean tmp = record.add(sdf);
              _builder.newLineIfNotEmpty();
            }
          }
        }
        if (_hasElements) {
          _builder.append("");
        }
      }
      _builder.newLine();
      {
        boolean _hasElements_1 = false;
        for(final Vertex sdf_1 : this.outputSDFChannelSet) {
          if (!_hasElements_1) {
            _hasElements_1 = true;
          } else {
            _builder.appendImmediate("", "");
          }
          {
            boolean _contains_1 = record.contains(sdf_1);
            boolean _not_1 = (!_contains_1);
            if (_not_1) {
              _builder.append("extern fifo_");
              String _identifier_1 = sdf_1.getIdentifier();
              _builder.append(_identifier_1);
              _builder.append(";");
              _builder.newLineIfNotEmpty();
              boolean tmp_1 = record.add(sdf_1);
              _builder.newLineIfNotEmpty();
            }
          }
        }
        if (_hasElements_1) {
          _builder.append("");
        }
      }
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
  
  public String initMemory(final Vertex actor) {
    ForSyDeSystemGraph model = Generator.model;
    Set<String> impls = Query.findCombFuntionVertex(model, actor);
    Set<String> variableNameRecord = new HashSet<String>();
    String ret = "";
    for (final String impl : impls) {
      {
        final Predicate<Vertex> _function = new Predicate<Vertex>() {
          public boolean test(final Vertex v) {
            return v.getIdentifier().equals(impl);
          }
        };
        Vertex actorimpl = model.vertexSet().stream().filter(_function).findAny().orElse(null);
        Set<String> inputPortSet = Query.findImplInputPortSet(actorimpl);
        for (final String inport : inputPortSet) {
          {
            String datatype = Query.findImplPortDataType(model, actorimpl, inport);
            boolean _contains = variableNameRecord.contains(inport);
            boolean _not = (!_contains);
            if (_not) {
              String _ret = ret;
              StringConcatenation _builder = new StringConcatenation();
              _builder.append(datatype);
              _builder.append(" ");
              _builder.append(inport);
              _builder.append("; ");
              _builder.newLineIfNotEmpty();
              ret = (_ret + _builder);
              variableNameRecord.add(inport);
            }
          }
        }
        Set<String> outputPortSet = Query.findImplOutputPortSet(actorimpl);
        for (final String outport : outputPortSet) {
          {
            String datatype = Query.findImplPortDataType(model, actorimpl, outport);
            boolean _contains = variableNameRecord.contains(outport);
            boolean _not = (!_contains);
            if (_not) {
              String _ret = ret;
              StringConcatenation _builder = new StringConcatenation();
              _builder.append(datatype);
              _builder.append(" ");
              _builder.append(outport);
              _builder.append("; ");
              _builder.newLineIfNotEmpty();
              ret = (_ret + _builder);
              variableNameRecord.add(outport);
            }
          }
        }
      }
    }
    return ret;
  }
  
  public String read(final Vertex actor) {
    ForSyDeSystemGraph model = Generator.model;
    Set<String> impls = Query.findCombFuntionVertex(model, actor);
    Set<String> variableNameRecord = new HashSet<String>();
    String ret = "";
    for (final String impl : impls) {
      {
        Vertex actorimpl = Query.findVertexByName(model, impl);
        Set<String> inputPortSet = Query.findImplInputPortSet(actorimpl);
        for (final String inport : inputPortSet) {
          boolean _contains = variableNameRecord.contains(inport);
          boolean _not = (!_contains);
          if (_not) {
            String datatype = Query.findImplPortDataType(model, actorimpl, inport);
            String actorPortName = Query.findActorPortConnectedToImplInputPort(model, actor, actorimpl, inport);
            String sdfchannelName = Query.findInputSDFChannelConnectedToActorPort(model, actor, actorPortName);
            try {
              Integer consumption = SDFComb.enforce(actor).getConsumption().get(actorPortName);
              if (((consumption).intValue() == 1)) {
                String _ret = ret;
                StringConcatenation _builder = new StringConcatenation();
                _builder.append("read_non_blocking(&fifo_");
                _builder.append(sdfchannelName);
                _builder.append(",&");
                _builder.append(inport);
                _builder.append(");");
                _builder.newLineIfNotEmpty();
                ret = (_ret + _builder);
              } else {
                String _ret_1 = ret;
                StringConcatenation _builder_1 = new StringConcatenation();
                _builder_1.append("for(int i=0;i<");
                _builder_1.append(consumption);
                _builder_1.append(";++i){");
                _builder_1.newLineIfNotEmpty();
                _builder_1.append("\t");
                _builder_1.append("read_non_blocking(&fifo_");
                _builder_1.append(sdfchannelName, "\t");
                _builder_1.append(",&");
                _builder_1.append(inport, "\t");
                _builder_1.append("[i]);");
                _builder_1.newLineIfNotEmpty();
                _builder_1.append("}");
                _builder_1.newLine();
                ret = (_ret_1 + _builder_1);
              }
              variableNameRecord.add(inport);
            } catch (final Throwable _t) {
              if (_t instanceof Exception) {
                String _identifier = actor.getIdentifier();
                String _plus = ("In actor " + _identifier);
                String _plus_1 = (_plus + " port ");
                String _plus_2 = (_plus_1 + inport);
                String _plus_3 = (_plus_2 + " no comsumption");
                InputOutput.<String>println(_plus_3);
                return (("error " + inport) + ";");
              } else {
                throw Exceptions.sneakyThrow(_t);
              }
            }
          }
        }
      }
    }
    return ret;
  }
  
  public String write(final Vertex actor) {
    ForSyDeSystemGraph model = Generator.model;
    Set<String> impls = Query.findCombFuntionVertex(model, actor);
    Set<String> variableNameRecord = new HashSet<String>();
    String ret = "";
    for (final String impl : impls) {
      {
        Vertex actorimpl = Query.findVertexByName(model, impl);
        Set<String> outputPortSet = Query.findImplOutputPortSet(actorimpl);
        for (final String outport : outputPortSet) {
          boolean _contains = variableNameRecord.contains(outport);
          boolean _not = (!_contains);
          if (_not) {
            String datatype = Query.findImplPortDataType(model, actorimpl, outport);
            String actorPortName = Query.findActorPortConnectedToImplOutputPort(model, actor, actorimpl, outport);
            String sdfchannelName = Query.findOutputSDFChannelConnectedToActorPort(model, actor, actorPortName);
            try {
              Integer production = SDFComb.enforce(actor).getProduction().get(actorPortName);
              if (((production).intValue() == 1)) {
                String _ret = ret;
                StringConcatenation _builder = new StringConcatenation();
                _builder.append("write_non_blocking(&fifo_");
                _builder.append(sdfchannelName);
                _builder.append(",&");
                _builder.append(outport);
                _builder.append(");");
                _builder.newLineIfNotEmpty();
                ret = (_ret + _builder);
              } else {
                String _ret_1 = ret;
                StringConcatenation _builder_1 = new StringConcatenation();
                _builder_1.append("for(int i=0;i<");
                _builder_1.append(production);
                _builder_1.append(";++i){");
                _builder_1.newLineIfNotEmpty();
                _builder_1.append("\t");
                _builder_1.append("write_non_blocking(&fifo_");
                _builder_1.append(sdfchannelName, "\t");
                _builder_1.append(",&");
                _builder_1.append(outport, "\t");
                _builder_1.append("[i]);");
                _builder_1.newLineIfNotEmpty();
                _builder_1.append("}");
                _builder_1.newLine();
                ret = (_ret_1 + _builder_1);
              }
              variableNameRecord.add(outport);
            } catch (final Throwable _t) {
              if (_t instanceof Exception) {
                String _identifier = actor.getIdentifier();
                String _plus = ("In actor " + _identifier);
                String _plus_1 = (_plus + " port ");
                String _plus_2 = (_plus_1 + outport);
                String _plus_3 = (_plus_2 + " no production");
                InputOutput.<String>println(_plus_3);
                return (("error " + outport) + ";");
              } else {
                throw Exceptions.sneakyThrow(_t);
              }
            }
          }
        }
      }
    }
    return ret;
  }
  
  private String getInlineCode() {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _hasElements = false;
      for(final Vertex impl : this.implActorSet) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate("", "");
        }
        _builder.append("//in combFunction ");
        String _identifier = impl.getIdentifier();
        _builder.append(_identifier);
        _builder.newLineIfNotEmpty();
        String _inlineCode = Query.getInlineCode(impl);
        _builder.append(_inlineCode);
        _builder.newLineIfNotEmpty();
      }
      if (_hasElements) {
        _builder.append("");
      }
    }
    return _builder.toString();
  }
  
  private String getExternSDFChannel(final Vertex actor) {
    String _xblockexpression = null;
    {
      Set<Vertex> SDFChannelSet = Query.findInputSDFChannels(Generator.model, actor);
      SDFChannelSet.addAll(Query.findOutputSDFChannels(Generator.model, actor));
      StringConcatenation _builder = new StringConcatenation();
      {
        boolean _hasElements = false;
        for(final Vertex sdfchannel : SDFChannelSet) {
          if (!_hasElements) {
            _hasElements = true;
          } else {
            _builder.appendImmediate("", "");
          }
          _builder.append("\t");
          _builder.newLine();
        }
        if (_hasElements) {
          _builder.append("");
        }
      }
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
}
