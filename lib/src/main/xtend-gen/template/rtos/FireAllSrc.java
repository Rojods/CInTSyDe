package template.rtos;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.templateInterface.InitTemplate;

@FileTypeAnno(type = FileType.C_SOURCE)
@SuppressWarnings("all")
public class FireAllSrc implements InitTemplate {
  @Override
  public String create() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#include \"../inc/fire_all.h\"");
    _builder.newLine();
    _builder.append("#include \"../inc/config.h\"");
    _builder.newLine();
    _builder.append("#include \"FreeRTOS.h\"");
    _builder.newLine();
    _builder.append("#include \"semphr.h\"");
    _builder.newLine();
    _builder.append("#include \"timers.h\"\t");
    _builder.newLine();
    _builder.append("#include \"queue.h\"\t\t");
    _builder.newLine();
    _builder.append("void fire_all(){");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("#if FREERTOS==1");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("vTaskStartScheduler();");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("#endif");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder.toString();
  }
  
  @Override
  public String savePath() {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
}
