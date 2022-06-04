package template.baremetal_multi;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel;
import forsyde.io.java.typed.viewers.values.IntegerValue;
import generator.Generator;
import java.util.HashMap;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.templateInterface.InitTemplate;
import utils.Query;

@FileTypeAnno(type = FileType.C_SOURCE)
@SuppressWarnings("all")
public class SubsystemInitSrc implements InitTemplate {
  @Override
  public String create() {
    String _xblockexpression = null;
    {
      ForSyDeSystemGraph model = Generator.model;
      final Predicate<Vertex> _function = (Vertex v) -> {
        return (IntegerValue.conforms(v)).booleanValue();
      };
      final Function<Vertex, IntegerValue> _function_1 = (Vertex v) -> {
        return IntegerValue.safeCast(v).get();
      };
      Set<IntegerValue> integerValues = model.vertexSet().stream().filter(_function).<IntegerValue>map(_function_1).collect(Collectors.<IntegerValue>toSet());
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("#include \"../inc/config.h\"");
      _builder.newLine();
      _builder.append("#include \"../inc/subsystem_init.h\"");
      _builder.newLine();
      _builder.append("#include \"../inc/circular_fifo_lib.h\"");
      _builder.newLine();
      _builder.append("#include \"../inc/datatype_definition.h\"");
      _builder.newLine();
      _builder.append("/*");
      _builder.newLine();
      _builder.append("==============================================");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("Extern Variables ");
      _builder.newLine();
      _builder.append("==============================================");
      _builder.newLine();
      _builder.append("*/\t");
      _builder.newLine();
      {
        for(final IntegerValue value : integerValues) {
          _builder.append("\t");
          _builder.append("extern int ");
          String _identifier = value.getIdentifier();
          _builder.append(_identifier, "\t");
          _builder.append(";");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("#if MULTICORE==1");
      _builder.newLine();
      {
        for(final Vertex channel : Generator.sdfchannelSet) {
          _builder.append("\t");
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
              _builder.append("extern unsigned int buffer_");
              _builder.append(sdfname, "\t");
              _builder.append("_size;");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("extern unsigned int token_");
              _builder.append(sdfname, "\t");
              _builder.append("_size;");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("extern volatile ");
              _builder.append(type, "\t");
              _builder.append(" buffer_");
              _builder.append(sdfname, "\t");
              _builder.append("[];");
              _builder.newLineIfNotEmpty();
            }
          }
        }
      }
      _builder.append("\t");
      _builder.append("#endif\t\t\t");
      _builder.newLine();
      _builder.append("int init_subsystem(){");
      _builder.newLine();
      {
        for(final Vertex channel_1 : Generator.sdfchannelSet) {
          _builder.append("\t");
          String sdfname_1 = channel_1.getIdentifier();
          _builder.newLineIfNotEmpty();
          {
            boolean _isOnOneCoreChannel_1 = Query.isOnOneCoreChannel(model, channel_1);
            if (_isOnOneCoreChannel_1) {
              _builder.append("\t");
              _builder.append("init_channel_");
              String _findSDFChannelDataType = Query.findSDFChannelDataType(Generator.model, channel_1);
              _builder.append(_findSDFChannelDataType, "\t");
              _builder.append("(&fifo_");
              _builder.append(sdfname_1, "\t");
              _builder.append(",buffer_");
              _builder.append(sdfname_1, "\t");
              _builder.append(",buffer_");
              _builder.append(sdfname_1, "\t");
              _builder.append("_size);");
              _builder.newLineIfNotEmpty();
            } else {
              _builder.append("\t");
              _builder.append("if (cheap_init_r (fifo_admin_");
              _builder.append(sdfname_1, "\t");
              _builder.append(", (void *) buffer_");
              _builder.append(sdfname_1, "\t");
              _builder.append(", buffer_");
              _builder.append(sdfname_1, "\t");
              _builder.append("_size, token_");
              _builder.append(sdfname_1, "\t");
              _builder.append("_size) == NULL) {");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("  ");
              _builder.append("//xil_printf(\"%04u/%010u: cheap_init_r failed\\n\", (uint32_t)(t>>32),(uint32_t)t);");
              _builder.newLine();
              _builder.append("\t");
              _builder.append("  ");
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
      {
        for(final Vertex channel_2 : Generator.sdfchannelSet) {
          _builder.append("\t");
          SDFChannel sdfchannel = SDFChannel.safeCast(channel_2).get();
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
                  {
                    boolean _isOnOneCoreChannel_2 = Query.isOnOneCoreChannel(model, channel_2);
                    if (_isOnOneCoreChannel_2) {
                      _builder.append("\t");
                      _builder.append("write_non_blocking_");
                      String _findSDFChannelDataType_1 = Query.findSDFChannelDataType(Generator.model, channel_2);
                      _builder.append(_findSDFChannelDataType_1, "\t");
                      _builder.append("(&fifo_");
                      String _identifier_1 = sdfchannel.getIdentifier();
                      _builder.append(_identifier_1, "\t");
                      _builder.append(",");
                      _builder.append(k, "\t");
                      _builder.append(");");
                      _builder.newLineIfNotEmpty();
                    } else {
                      _builder.append("\t");
                      _builder.append("//while(  )");
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
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("return 0;");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("}");
      _builder.newLine();
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
  
  @Override
  public String getFileName() {
    return "subsystem_init";
  }
}
