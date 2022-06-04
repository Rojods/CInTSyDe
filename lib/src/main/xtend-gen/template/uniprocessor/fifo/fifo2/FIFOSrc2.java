package template.uniprocessor.fifo.fifo2;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.templateInterface.InitTemplate;

@FileTypeAnno(type = FileType.C_SOURCE)
@SuppressWarnings("all")
public class FIFOSrc2 implements InitTemplate {
  public FIFOSrc2() {
  }
  
  @Override
  public String create() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    _builder.append("/*");
    _builder.newLine();
    _builder.append("*******************************************************");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("copy by reference");
    _builder.newLine();
    _builder.append("*******************************************************");
    _builder.newLine();
    _builder.append("*/");
    _builder.newLine();
    _builder.append("#include \"../inc/config.h\"");
    _builder.newLine();
    _builder.append("#include \"../inc/datatype_definition.h\"");
    _builder.newLine();
    _builder.append("#include \"../inc/circular_fifo_lib.h\"");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.append("void init(circular_fifo* fifo_ptr, void** buffer, size_t size){");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("fifo_ptr->front=0;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("fifo_ptr->rear=0;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("fifo_ptr->buffer=buffer;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("fifo_ptr->size=size;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.append("void read_non_blocking(circular_fifo* fifo_ptr, void** dst){");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("if(fifo_ptr->front==fifo_ptr->rear){");
    _builder.newLine();
    _builder.append("\t \t");
    _builder.append("//empty");
    _builder.newLine();
    _builder.append("\t \t");
    _builder.append("return;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}else{");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("*dst = fifo_ptr->buffer[fifo_ptr->front];\t\t\t\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("fifo_ptr->front= (fifo_ptr->front+1)%fifo_ptr->size;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.append("void write_non_blocking(circular_fifo* channel, void* src){");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("if((channel->rear+1)%channel->size == channel->front){");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("//full");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return ;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}else{");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("channel->buffer[channel->rear] = src;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("channel->rear= (channel->rear+1)%channel->size;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}\t\t\t\t\t");
    _builder.newLine();
    _builder.append("}\t\t");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.newLine();
    return _builder.toString();
  }
  
  @Override
  public String getFileName() {
    return "circular_fifo_lib";
  }
}
