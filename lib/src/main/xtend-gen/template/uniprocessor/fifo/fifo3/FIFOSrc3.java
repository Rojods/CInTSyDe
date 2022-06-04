package template.uniprocessor.fifo.fifo3;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.templateInterface.InitTemplate;

@FileTypeAnno(type = FileType.C_SOURCE)
@SuppressWarnings("all")
public class FIFOSrc3 implements InitTemplate {
  public FIFOSrc3() {
  }
  
  @Override
  public String create() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("/*");
    _builder.newLine();
    _builder.append("*******************************************************");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("copy by value");
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
    _builder.append("void init(circular_fifo* fifo_ptr, void* buf, size_t token_number, size_t token_size){");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("fifo_ptr->front=0;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("fifo_ptr->rear=0;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("fifo_ptr->token_number=token_number;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("fifo_ptr->token_size=token_size;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.append("void read_non_blocking(circular_fifo* fifo_ptr,void* dst){");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("if(fifo_ptr->front==fifo_ptr->rear){");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("//empty");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}else{");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("memcpy(dst,(void*)(fifo_ptr->buffer+fifo_ptr->front*fifo_ptr->token_size),fifo_ptr->token_size);");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("fifo_ptr->front= (fifo_ptr->front+1)%fifo_ptr->token_number;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.append("void write_non_blocking(circular_fifo* channel,void* src){");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("if((channel->rear+1)%channel->token_number == channel->front){");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("//full\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}else{");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("memcpy((void*)(channel->rear*channel->token_size+channel->buffer), src , channel->token_size);");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("channel->rear= (channel->rear+1)%channel->token_number;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}\t");
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
