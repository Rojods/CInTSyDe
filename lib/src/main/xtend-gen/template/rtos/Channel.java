package template.rtos;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.Vertex;
import generator.Generator;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.templateInterface.InitTemplate;
import utils.Query;

@FileTypeAnno(type = FileType.C_SOURCE)
@SuppressWarnings("all")
public class Channel implements InitTemplate {
  public String create() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#include \"semphr.h\"");
    _builder.newLine();
    _builder.append("#include \"timers.h\"\t");
    _builder.newLine();
    _builder.append("#include \"FreeRTOS.h\"");
    _builder.newLine();
    _builder.append("#include \"queue.h\"");
    _builder.newLine();
    {
      boolean _hasElements = false;
      for(final Vertex sdfchannel : Generator.sdfchannelSet) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate("", "");
        }
        String sdfname = sdfchannel.getIdentifier();
        _builder.newLineIfNotEmpty();
        _builder.append("/*");
        _builder.newLine();
        _builder.append("============================================");
        _builder.newLine();
        _builder.append("SDFChannel ");
        _builder.append(sdfname);
        _builder.append(" Message Queue");
        _builder.newLineIfNotEmpty();
        _builder.append("============================================");
        _builder.newLine();
        _builder.append("*/");
        _builder.newLine();
        _builder.append("/* msg queue */");
        _builder.newLine();
        _builder.append("QueueHandle_t msg_queue_");
        _builder.append(sdfname);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.append("/* maximum number of tokens in message queue */");
        _builder.newLine();
        _builder.append("int queue_length_");
        _builder.append(sdfname);
        _builder.append(" = ");
        int _bufferSize = Query.getBufferSize(sdfchannel);
        _builder.append(_bufferSize);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.append("/* size of token */");
        _builder.newLine();
        _builder.append("long item_size_");
        _builder.append(sdfname);
        _builder.append(" = ");
        long _tokenSize = Query.getTokenSize(sdfchannel);
        _builder.append(_tokenSize);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.newLine();
      }
      if (_hasElements) {
        _builder.append("");
      }
    }
    _builder.newLine();
    _builder.newLine();
    return _builder.toString();
  }
  
  public String getFileName() {
    return "sdfchannel";
  }
}