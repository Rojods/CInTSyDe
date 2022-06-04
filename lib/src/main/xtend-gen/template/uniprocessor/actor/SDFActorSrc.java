package template.uniprocessor.actor;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.typed.viewers.impl.Executable;
import forsyde.io.java.typed.viewers.moc.sdf.SDFActor;
import forsyde.io.java.typed.viewers.typing.TypedDataBlockViewer;
import forsyde.io.java.typed.viewers.typing.TypedOperation;
import forsyde.io.java.typed.viewers.typing.datatypes.DataType;
import generator.Generator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;
import template.templateInterface.ActorTemplate;
import utils.Query;

@FileTypeAnno(type = FileType.C_SOURCE)
@SuppressWarnings("all")
public class SDFActorSrc implements ActorTemplate {
  private Set<Vertex> implActorSet;
  
  private Set<Vertex> inputSDFChannelSet;
  
  private Set<Vertex> outputSDFChannelSet;
  
  @Override
  public String create(final Vertex actor) {
    String _xblockexpression = null;
    {
      final ForSyDeSystemGraph model = Generator.model;
      final Function<Executable, Vertex> _function = (Executable v) -> {
        return v.getViewedVertex();
      };
      this.implActorSet = SDFActor.safeCast(actor).get().getCombFunctionsPort(model).stream().<Vertex>map(_function).collect(Collectors.<Vertex>toSet());
      this.inputSDFChannelSet = Query.findInputSDFChannels(model, actor);
      this.outputSDFChannelSet = Query.findOutputSDFChannels(model, actor);
      Set<Vertex> datablock = null;
      datablock = Query.findAllExternalDataBlocks(model, SDFActor.safeCast(actor).get());
      StringBuilder ret1 = new StringBuilder();
      StringBuilder ret2 = new StringBuilder();
      this.initMemory(model, actor, ret1, ret2);
      StringConcatenation _builder = new StringConcatenation();
      String name = actor.getIdentifier();
      _builder.newLineIfNotEmpty();
      _builder.append("/* Includes */");
      _builder.newLine();
      _builder.append("#include \"../inc/config.h\"");
      _builder.newLine();
      _builder.append("#include \"../inc/datatype_definition.h\"");
      _builder.newLine();
      _builder.append("#include \"../inc/circular_fifo_lib.h\"");
      _builder.newLine();
      _builder.append("#include \"../inc/sdfcomb_");
      _builder.append(name);
      _builder.append(".h\"");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("/*");
      _builder.newLine();
      _builder.append("========================================");
      _builder.newLine();
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
      _builder.append("Declare Extern Global Variables");
      _builder.newLine();
      _builder.append("========================================");
      _builder.newLine();
      _builder.append("*/\t\t\t");
      _builder.newLine();
      {
        for(final Vertex d : datablock) {
          _builder.append("extern ");
          String _findType = this.findType(model, d);
          _builder.append(_findType);
          _builder.append(" ");
          String _identifier = d.getIdentifier();
          _builder.append(_identifier);
          _builder.append(";");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.append("\t");
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
      _builder.append("*/\t");
      _builder.newLine();
      _builder.append("/*  initialize memory*/");
      _builder.newLine();
      _builder.append(ret1);
      _builder.append("\t");
      _builder.newLineIfNotEmpty();
      _builder.append("void actor_");
      _builder.append(name);
      _builder.append("(){");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("\t");
      _builder.append(ret2, "\t");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("/* Read From Input Port  */");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("int ret=0;");
      _builder.newLine();
      _builder.append("\t");
      String _read = this.read(model, actor);
      _builder.append(_read, "\t");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("/* Inline Code           */");
      _builder.newLine();
      _builder.append("\t");
      String _inlineCode = this.getInlineCode();
      _builder.append(_inlineCode, "\t");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("/* Write To Output Ports */");
      _builder.newLine();
      _builder.append("\t");
      String _write = this.write(actor);
      _builder.append(_write, "\t");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
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
      _builder.append("/* Input FIFO */");
      _builder.newLine();
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
              {
                if ((Generator.fifoType == 1)) {
                  _builder.append("extern circular_fifo_");
                  String _findSDFChannelDataType = Query.findSDFChannelDataType(Generator.model, sdf);
                  _builder.append(_findSDFChannelDataType);
                  _builder.append(" fifo_");
                  String _identifier = sdf.getIdentifier();
                  _builder.append(_identifier);
                  _builder.append(";");
                  _builder.newLineIfNotEmpty();
                  _builder.append("extern spinlock spinlock_");
                  String _identifier_1 = sdf.getIdentifier();
                  _builder.append(_identifier_1);
                  _builder.append(";\t");
                  _builder.newLineIfNotEmpty();
                }
              }
              {
                if ((Generator.fifoType == 2)) {
                  _builder.append("extern circular_fifo fifo_");
                  String _identifier_2 = sdf.getIdentifier();
                  _builder.append(_identifier_2);
                  _builder.append(";");
                  _builder.newLineIfNotEmpty();
                  _builder.append("extern spinlock spinlock_");
                  String _identifier_3 = sdf.getIdentifier();
                  _builder.append(_identifier_3);
                  _builder.append(";\t");
                  _builder.newLineIfNotEmpty();
                }
              }
              boolean tmp = record.add(sdf);
              _builder.newLineIfNotEmpty();
              _builder.newLine();
            }
          }
        }
        if (_hasElements) {
          _builder.append("");
        }
      }
      _builder.append("/* Output FIFO */");
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
              {
                if ((Generator.fifoType == 1)) {
                  _builder.append("extern circular_fifo_");
                  String _findSDFChannelDataType_1 = Query.findSDFChannelDataType(Generator.model, sdf_1);
                  _builder.append(_findSDFChannelDataType_1);
                  _builder.append(" fifo_");
                  String _identifier_4 = sdf_1.getIdentifier();
                  _builder.append(_identifier_4);
                  _builder.append(";");
                  _builder.newLineIfNotEmpty();
                  _builder.append("extern spinlock spinlock_");
                  String _identifier_5 = sdf_1.getIdentifier();
                  _builder.append(_identifier_5);
                  _builder.append(";");
                  _builder.newLineIfNotEmpty();
                }
              }
              {
                if ((Generator.fifoType == 2)) {
                  _builder.append("extern circular_fifo fifo_");
                  String _identifier_6 = sdf_1.getIdentifier();
                  _builder.append(_identifier_6);
                  _builder.append(";");
                  _builder.newLineIfNotEmpty();
                  _builder.append("extern spinlock spinlock_");
                  String _identifier_7 = sdf_1.getIdentifier();
                  _builder.append(_identifier_7);
                  _builder.append(";");
                  _builder.newLineIfNotEmpty();
                }
              }
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
  
  public String initMemory(final ForSyDeSystemGraph model, final Vertex actor, final StringBuilder ret1, final StringBuilder ret2) {
    Set<String> impls = Query.findCombFuntionVertex(model, actor);
    Set<String> variableNameRecord = new HashSet<String>();
    String ret = "";
    for (final String impl : impls) {
      {
        InputOutput.<String>println(("-->" + impl));
        Vertex actorimpl = model.queryVertex(impl).get();
        Set<String> ports = new HashSet<String>();
        List<String> _findImplInputPorts = Query.findImplInputPorts(actorimpl);
        boolean _tripleNotEquals = (_findImplInputPorts != null);
        if (_tripleNotEquals) {
          ports.addAll(Query.findImplInputPorts(actorimpl));
        }
        List<String> _findImplOutputPorts = Query.findImplOutputPorts(actorimpl);
        boolean _tripleNotEquals_1 = (_findImplOutputPorts != null);
        if (_tripleNotEquals_1) {
          ports.addAll(Query.findImplOutputPorts(actorimpl));
        }
        boolean _isEmpty = ports.isEmpty();
        if (_isEmpty) {
          String _ret = ret;
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("The inputPorts or outputPorts Property is not specified in ");
          _builder.append(impl);
          _builder.newLineIfNotEmpty();
          ret = (_ret + _builder);
        } else {
          for (final String port : ports) {
            {
              String datatype = Query.findImplPortDataType(model, actorimpl, port);
              boolean _contains = variableNameRecord.contains(port);
              boolean _not = (!_contains);
              if (_not) {
                String _isSystemChannel = Query.isSystemChannel(model, actorimpl, port);
                boolean _tripleEquals = (_isSystemChannel == null);
                if (_tripleEquals) {
                  StringConcatenation _builder_1 = new StringConcatenation();
                  _builder_1.append("static\t");
                  _builder_1.append(datatype);
                  _builder_1.append(" ");
                  _builder_1.append(port);
                  _builder_1.append("; ");
                  _builder_1.newLineIfNotEmpty();
                  ret1.append(_builder_1);
                } else {
                  StringConcatenation _builder_2 = new StringConcatenation();
                  _builder_2.append(datatype);
                  _builder_2.append(" ");
                  _builder_2.append(port);
                  _builder_2.append(" = ");
                  String _isSystemChannel_1 = Query.isSystemChannel(model, actorimpl, port);
                  _builder_2.append(_isSystemChannel_1);
                  _builder_2.append("; ");
                  _builder_2.newLineIfNotEmpty();
                  ret2.append(_builder_2);
                }
                variableNameRecord.add(port);
              }
            }
          }
        }
      }
    }
    return ret;
  }
  
  public String read(final ForSyDeSystemGraph model, final Vertex actor) {
    final Function<Executable, Vertex> _function = (Executable e) -> {
      return e.getViewedVertex();
    };
    Set<Vertex> impls = SDFActor.safeCast(actor).get().getCombFunctionsPort(model).stream().<Vertex>map(_function).collect(Collectors.<Vertex>toSet());
    Set<String> variableNameRecord = new HashSet<String>();
    String ret = "";
    for (final Vertex impl : impls) {
      {
        InputOutput.<Vertex>println(impl);
        List<String> inputPorts = TypedOperation.safeCast(impl).get().getInputPorts();
        if ((inputPorts != null)) {
          for (final String port : inputPorts) {
            if (((!variableNameRecord.contains(port)) && (Query.isSystemChannel(model, impl, port) == null))) {
              String actorPortName = Query.findActorPortConnectedToImplInputPort(model, actor, impl, port);
              String sdfchannelName = Query.findInputSDFChannelConnectedToActorPort(model, actor, actorPortName);
              String datatype = Query.findSDFChannelDataType(model, model.queryVertex(sdfchannelName).get());
              Integer consumption = SDFActor.safeCast(actor).get().getConsumption().get(actorPortName);
              if ((consumption == null)) {
                String _ret = ret;
                StringConcatenation _builder = new StringConcatenation();
                _builder.append("Consumption in ");
                String _identifier = actor.getIdentifier();
                _builder.append(_identifier);
                _builder.append(" Not Specified!");
                _builder.newLineIfNotEmpty();
                ret = (_ret + _builder);
              } else {
                if (((consumption).intValue() == 1)) {
                  String _ret_1 = ret;
                  StringConcatenation _builder_1 = new StringConcatenation();
                  {
                    if ((Generator.fifoType == 1)) {
                      _builder_1.append("#if ");
                      String _upperCase = sdfchannelName.toUpperCase();
                      _builder_1.append(_upperCase);
                      _builder_1.append("_BLOCKING==0");
                      _builder_1.newLineIfNotEmpty();
                      _builder_1.append("ret=read_non_blocking_");
                      _builder_1.append(datatype);
                      _builder_1.append("(&fifo_");
                      _builder_1.append(sdfchannelName);
                      _builder_1.append(",&");
                      _builder_1.append(port);
                      _builder_1.append(");");
                      _builder_1.newLineIfNotEmpty();
                      _builder_1.append("if(ret==-1){");
                      _builder_1.newLine();
                      _builder_1.append("\t");
                      _builder_1.append("//printf(\"fifo_");
                      _builder_1.append(sdfchannelName, "\t");
                      _builder_1.append(" read error\\n\");");
                      _builder_1.newLineIfNotEmpty();
                      _builder_1.append("}");
                      _builder_1.newLine();
                      _builder_1.newLine();
                      _builder_1.append("#else");
                      _builder_1.newLine();
                      _builder_1.append("read_blocking_");
                      _builder_1.append(datatype);
                      _builder_1.append("(&fifo_");
                      _builder_1.append(sdfchannelName);
                      _builder_1.append(",&");
                      _builder_1.append(port);
                      _builder_1.append(",&spinlock_");
                      _builder_1.append(sdfchannelName);
                      _builder_1.append(");");
                      _builder_1.newLineIfNotEmpty();
                      _builder_1.append("#endif");
                      _builder_1.newLine();
                    }
                  }
                  {
                    if ((Generator.fifoType == 2)) {
                      _builder_1.append("{");
                      _builder_1.newLine();
                      _builder_1.append("\t");
                      _builder_1.append("void* tmp_addr;");
                      _builder_1.newLine();
                      _builder_1.append("\t");
                      _builder_1.append("read_non_blocking(&fifo_");
                      _builder_1.append(sdfchannelName, "\t");
                      _builder_1.append(",&tmp_addr);");
                      _builder_1.newLineIfNotEmpty();
                      _builder_1.append("\t");
                      _builder_1.append(port, "\t");
                      _builder_1.append("= *((");
                      _builder_1.append(datatype, "\t");
                      _builder_1.append(" *)tmp_addr);");
                      _builder_1.newLineIfNotEmpty();
                      _builder_1.append("}");
                      _builder_1.newLine();
                    }
                  }
                  _builder_1.newLine();
                  ret = (_ret_1 + _builder_1);
                } else {
                  String _ret_2 = ret;
                  StringConcatenation _builder_2 = new StringConcatenation();
                  _builder_2.append("for(int i=0;i<");
                  _builder_2.append(consumption);
                  _builder_2.append(";++i){");
                  _builder_2.newLineIfNotEmpty();
                  {
                    if ((Generator.fifoType == 1)) {
                      _builder_2.append("\t");
                      _builder_2.append("#if ");
                      String _upperCase_1 = sdfchannelName.toUpperCase();
                      _builder_2.append(_upperCase_1, "\t");
                      _builder_2.append("_BLOCKING==0");
                      _builder_2.newLineIfNotEmpty();
                      _builder_2.append("\t");
                      _builder_2.append("ret=read_non_blocking_");
                      _builder_2.append(datatype, "\t");
                      _builder_2.append("(&fifo_");
                      _builder_2.append(sdfchannelName, "\t");
                      _builder_2.append(",&");
                      _builder_2.append(port, "\t");
                      _builder_2.append("[i]);");
                      _builder_2.newLineIfNotEmpty();
                      _builder_2.append("\t");
                      _builder_2.append("if(ret==-1){");
                      _builder_2.newLine();
                      _builder_2.append("\t");
                      _builder_2.append("\t");
                      _builder_2.append("printf(\"fifo_");
                      _builder_2.append(sdfchannelName, "\t\t");
                      _builder_2.append(" read error\\n\");");
                      _builder_2.newLineIfNotEmpty();
                      _builder_2.append("\t");
                      _builder_2.append("}");
                      _builder_2.newLine();
                      _builder_2.append("\t");
                      _builder_2.append("#else");
                      _builder_2.newLine();
                      _builder_2.append("\t");
                      _builder_2.append("read_blocking_");
                      _builder_2.append(datatype, "\t");
                      _builder_2.append("(&fifo_");
                      _builder_2.append(sdfchannelName, "\t");
                      _builder_2.append(",&");
                      _builder_2.append(port, "\t");
                      _builder_2.append("[i],&spinlock_");
                      _builder_2.append(sdfchannelName, "\t");
                      _builder_2.append(");");
                      _builder_2.newLineIfNotEmpty();
                      _builder_2.append("\t");
                      _builder_2.append("#endif");
                      _builder_2.newLine();
                    }
                  }
                  {
                    if ((Generator.fifoType == 2)) {
                      _builder_2.append("\t");
                      _builder_2.append("void* tmp_addr;");
                      _builder_2.newLine();
                      _builder_2.append("\t");
                      _builder_2.append("read_non_blocking(&fifo_");
                      _builder_2.append(sdfchannelName, "\t");
                      _builder_2.append(",&tmp_addr);");
                      _builder_2.newLineIfNotEmpty();
                      _builder_2.append("\t");
                      _builder_2.append(port, "\t");
                      _builder_2.append("[i]= *((");
                      _builder_2.append(datatype, "\t");
                      _builder_2.append(" *)tmp_addr);");
                      _builder_2.newLineIfNotEmpty();
                    }
                  }
                  _builder_2.append("}");
                  _builder_2.newLine();
                  _builder_2.newLine();
                  ret = (_ret_2 + _builder_2);
                }
              }
              variableNameRecord.add(port);
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
        List<String> outputPortSet = Query.findImplOutputPorts(actorimpl);
        for (final String outport : outputPortSet) {
          boolean _contains = variableNameRecord.contains(outport);
          boolean _not = (!_contains);
          if (_not) {
            String actorPortName = null;
            String sdfchannelName = null;
            String datatype = null;
            actorPortName = Query.findActorPortConnectedToImplOutputPort(model, actor, actorimpl, outport);
            sdfchannelName = Query.findOutputSDFChannelConnectedToActorPort(model, actor, actorPortName);
            try {
              datatype = Query.findSDFChannelDataType(model, model.queryVertex(sdfchannelName).get());
            } catch (final Throwable _t) {
              if (_t instanceof Exception) {
                datatype = (("<" + sdfchannelName) + " DataType Not Found>");
              } else {
                throw Exceptions.sneakyThrow(_t);
              }
            }
            Integer production = SDFActor.enforce(actor).getProduction().get(actorPortName);
            if ((production == null)) {
              String _ret = ret;
              StringConcatenation _builder = new StringConcatenation();
              _builder.append("Production in ");
              String _identifier = actor.getIdentifier();
              _builder.append(_identifier);
              _builder.append(" Is Not Specified!");
              _builder.newLineIfNotEmpty();
              ret = (_ret + _builder);
            } else {
              if (((production).intValue() == 1)) {
                String _ret_1 = ret;
                StringConcatenation _builder_1 = new StringConcatenation();
                {
                  if ((Generator.fifoType == 1)) {
                    _builder_1.append("#if ");
                    String _upperCase = sdfchannelName.toUpperCase();
                    _builder_1.append(_upperCase);
                    _builder_1.append("_BLOCKING==0");
                    _builder_1.newLineIfNotEmpty();
                    _builder_1.append("write_non_blocking_");
                    _builder_1.append(datatype);
                    _builder_1.append("(&fifo_");
                    _builder_1.append(sdfchannelName);
                    _builder_1.append(",");
                    _builder_1.append(outport);
                    _builder_1.append(");");
                    _builder_1.newLineIfNotEmpty();
                    _builder_1.append("#else");
                    _builder_1.newLine();
                    _builder_1.append("write_blocking_");
                    _builder_1.append(datatype);
                    _builder_1.append("(&fifo_");
                    _builder_1.append(sdfchannelName);
                    _builder_1.append(",");
                    _builder_1.append(outport);
                    _builder_1.append(",&spinlock_");
                    _builder_1.append(sdfchannelName);
                    _builder_1.append(");");
                    _builder_1.newLineIfNotEmpty();
                    _builder_1.append("#endif");
                    _builder_1.newLine();
                  }
                }
                {
                  if ((Generator.fifoType == 2)) {
                    _builder_1.append("write_non_blocking(&fifo_");
                    _builder_1.append(sdfchannelName);
                    _builder_1.append(",(void*)&");
                    _builder_1.append(outport);
                    _builder_1.append(");");
                    _builder_1.newLineIfNotEmpty();
                  }
                }
                ret = (_ret_1 + _builder_1);
              } else {
                String _ret_2 = ret;
                StringConcatenation _builder_2 = new StringConcatenation();
                _builder_2.append("for(int i=0;i<");
                _builder_2.append(production);
                _builder_2.append(";++i){");
                _builder_2.newLineIfNotEmpty();
                {
                  if ((Generator.fifoType == 1)) {
                    _builder_2.append("\t");
                    _builder_2.append("#if ");
                    String _upperCase_1 = sdfchannelName.toUpperCase();
                    _builder_2.append(_upperCase_1, "\t");
                    _builder_2.append("_BLOCKING==0");
                    _builder_2.newLineIfNotEmpty();
                    _builder_2.append("\t");
                    _builder_2.append("write_non_blocking_");
                    _builder_2.append(datatype, "\t");
                    _builder_2.append("(&fifo_");
                    _builder_2.append(sdfchannelName, "\t");
                    _builder_2.append(",");
                    _builder_2.append(outport, "\t");
                    _builder_2.append("[i]);");
                    _builder_2.newLineIfNotEmpty();
                    _builder_2.append("\t");
                    _builder_2.append("#else");
                    _builder_2.newLine();
                    _builder_2.append("\t");
                    _builder_2.append("write_blocking_");
                    _builder_2.append(datatype, "\t");
                    _builder_2.append("(&fifo_");
                    _builder_2.append(sdfchannelName, "\t");
                    _builder_2.append(",");
                    _builder_2.append(outport, "\t");
                    _builder_2.append("[i],&spinlock_");
                    _builder_2.append(sdfchannelName, "\t");
                    _builder_2.append(");");
                    _builder_2.newLineIfNotEmpty();
                    _builder_2.append("\t");
                    _builder_2.append("#endif");
                    _builder_2.newLine();
                  }
                }
                {
                  if ((Generator.fifoType == 2)) {
                    _builder_2.append("\t");
                    _builder_2.append("write_non_blocking(&fifo_");
                    _builder_2.append(sdfchannelName, "\t");
                    _builder_2.append(",(void*)&");
                    _builder_2.append(outport, "\t");
                    _builder_2.append("[i]);\t\t");
                    _builder_2.newLineIfNotEmpty();
                  }
                }
                _builder_2.append("}");
                _builder_2.newLine();
                _builder_2.newLine();
                ret = (_ret_2 + _builder_2);
              }
            }
            variableNameRecord.add(outport);
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
        _builder.append("/* in combFunction ");
        String _identifier = impl.getIdentifier();
        _builder.append(_identifier);
        _builder.append(" */");
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
  
  public String actorParameter(final ForSyDeSystemGraph model, final Vertex actor) {
    Set<String> _ports = actor.getPorts();
    Set<String> portSet = new HashSet<String>(_ports);
    portSet.remove("combFunctions");
    portSet.remove("combinator");
    List<String> portList = new ArrayList<String>(portSet);
    Collections.<String>sort(portList);
    String ret = "";
    for (int i = 0; (i < portList.size()); i = (i + 1)) {
      if ((i == 0)) {
        String _ret = ret;
        String _get = portList.get(i);
        String _plus = ("   " + _get);
        String _plus_1 = (_plus + "_port");
        ret = (_ret + _plus_1);
      } else {
        String _ret_1 = ret;
        String _get_1 = portList.get(i);
        String _plus_2 = ("," + _get_1);
        String _plus_3 = (_plus_2 + "_port");
        ret = (_ret_1 + _plus_3);
      }
    }
    return ret;
  }
  
  private String findType(final ForSyDeSystemGraph model, final Vertex datablock) {
    Optional<DataType> a = new TypedDataBlockViewer(datablock).getDataTypePort(model);
    boolean _isPresent = a.isPresent();
    boolean _not = (!_isPresent);
    if (_not) {
      return null;
    }
    return a.get().getIdentifier();
  }
}
