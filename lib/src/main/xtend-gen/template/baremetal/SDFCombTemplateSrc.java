package template.baremetal;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.core.VertexAcessor;
import forsyde.io.java.core.VertexProperty;
import forsyde.io.java.core.VertexTrait;
import generator.Generator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import org.eclipse.xtend2.lib.StringConcatenation;
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
      this.implActorSet = VertexAcessor.getMultipleNamedPort(Generator.model, actor, "combFunctions", 
        VertexTrait.IMPL_ANSICBLACKBOXEXECUTABLE, VertexAcessor.VertexPortDirection.OUTGOING);
      this.inputSDFChannelSet = Query.findInputSDFChannels(actor);
      this.outputSDFChannelSet = Query.findOutputSDFChannels(actor);
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
      _builder.newLine();
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
  
  public boolean isExtern(final String string) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
  
  public String read(final Vertex vertex) {
    String _xblockexpression = null;
    {
      VertexProperty consumption = vertex.getProperties().get("consumption");
      String _xifexpression = null;
      if ((consumption != null)) {
        String _xblockexpression_1 = null;
        {
          Object _unwrap = consumption.unwrap();
          HashMap<String, Integer> inputPortsHashMap = ((HashMap<String, Integer>) _unwrap);
          StringConcatenation _builder = new StringConcatenation();
          {
            Set<String> _keySet = inputPortsHashMap.keySet();
            boolean _hasElements = false;
            for(final String port : _keySet) {
              if (!_hasElements) {
                _hasElements = true;
              } else {
                _builder.appendImmediate("", "");
              }
              {
                Integer _get = inputPortsHashMap.get(port);
                boolean _equals = ((_get).intValue() == 1);
                if (_equals) {
                  _builder.append("read_non_blocking(&channel,&");
                  _builder.append(port);
                  _builder.append(");");
                  _builder.newLineIfNotEmpty();
                } else {
                  _builder.append("for(int i=0;i<");
                  Integer _get_1 = inputPortsHashMap.get(port);
                  _builder.append(_get_1);
                  _builder.append(";++i){");
                  _builder.newLineIfNotEmpty();
                  _builder.append("\t");
                  _builder.append("read_non_blocking(&channel,");
                  _builder.append(port, "\t");
                  _builder.append("+i);");
                  _builder.newLineIfNotEmpty();
                  _builder.append("}");
                  _builder.newLine();
                }
              }
            }
            if (_hasElements) {
              _builder.append("");
            }
          }
          _xblockexpression_1 = _builder.toString();
        }
        _xifexpression = _xblockexpression_1;
      } else {
        StringConcatenation _builder = new StringConcatenation();
        _xifexpression = _builder.toString();
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  public String write(final Vertex vertex) {
    String _xblockexpression = null;
    {
      VertexProperty production = vertex.getProperties().get("production");
      String _xifexpression = null;
      if ((production != null)) {
        String _xblockexpression_1 = null;
        {
          Object _unwrap = production.unwrap();
          HashMap<String, Integer> inputPortsHashMap = ((HashMap<String, Integer>) _unwrap);
          StringConcatenation _builder = new StringConcatenation();
          {
            Set<String> _keySet = inputPortsHashMap.keySet();
            boolean _hasElements = false;
            for(final String port : _keySet) {
              if (!_hasElements) {
                _hasElements = true;
              } else {
                _builder.appendImmediate("", "");
              }
              {
                Integer _get = inputPortsHashMap.get(port);
                boolean _equals = ((_get).intValue() == 1);
                if (_equals) {
                  _builder.append("write(");
                  _builder.append(port);
                  _builder.append("_channel);");
                  _builder.newLineIfNotEmpty();
                } else {
                  _builder.append("for(int i=0;i<");
                  Integer _get_1 = inputPortsHashMap.get(port);
                  _builder.append(_get_1);
                  _builder.append(";++i){");
                  _builder.newLineIfNotEmpty();
                  _builder.append("\t");
                  _builder.append("write(");
                  _builder.append(port, "\t");
                  _builder.append("_channel);");
                  _builder.newLineIfNotEmpty();
                  _builder.append("}");
                  _builder.newLine();
                }
              }
            }
            if (_hasElements) {
              _builder.append("");
            }
          }
          _xblockexpression_1 = _builder.toString();
        }
        _xifexpression = _xblockexpression_1;
      } else {
        StringConcatenation _builder = new StringConcatenation();
        _xifexpression = _builder.toString();
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
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
      Set<Vertex> SDFChannelSet = Query.findInputSDFChannels(actor);
      SDFChannelSet.addAll(Query.findOutputSDFChannels(actor));
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
