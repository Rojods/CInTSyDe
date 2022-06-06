package template.uniprocessor.fifo.fifo2;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.templateInterface.InitTemplate;

@FileTypeAnno(type = FileType.C_INCLUDE)
@SuppressWarnings("all")
public class FIFOInc2 implements InitTemplate {
  public FIFOInc2() {
  }
  
  @Override
  public String savePath() {
    return "/circular_fifo_lib/circular_fifo_lib.h";
  }
  
  @Override
  public String create() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#ifndef CIRCULAR_FIFO_LIB_H_");
    _builder.newLine();
    _builder.append("#define CIRCULAR_FIFO_LIB_H_");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("#include \"../datatype/datatype_definition.h\"");
    _builder.newLine();
    _builder.append("#include \"spinlock.h\"\t");
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
    _builder.append("\t\t\t");
    _builder.newLine();
    _builder.append("typedef struct{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("void**  buffer;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("size_t front;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("size_t rear;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("size_t size;");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("}circular_fifo;\t\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("void init(circular_fifo* fifo_ptr, void** buffer, size_t size);");
    _builder.newLine();
    _builder.append("void read_non_blocking(circular_fifo* fifo_ptr, void** dst);");
    _builder.newLine();
    _builder.append("void write_non_blocking(circular_fifo* fifo_ptr, void* src);\t\t\t\t\t");
    _builder.newLine();
    _builder.append("#endif");
    _builder.newLine();
    return _builder.toString();
  }
}
