package template.rtos;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.Vertex;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.templateInterface.ActorTemplate;

@FileTypeAnno(type = FileType.C_INCLUDE)
@SuppressWarnings("all")
public class SDFCombInc implements ActorTemplate {
  @Override
  public String create(final Vertex vertex) {
    String _xblockexpression = null;
    {
      String name = vertex.getIdentifier();
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("#ifndef ACTOR_");
      String _upperCase = name.toUpperCase();
      _builder.append(_upperCase);
      _builder.append("_H_");
      _builder.newLineIfNotEmpty();
      _builder.append("#define ACTOR_");
      String _upperCase_1 = name.toUpperCase();
      _builder.append(_upperCase_1);
      _builder.append("_H_");
      _builder.newLineIfNotEmpty();
      _builder.append("#include \"../inc/datatype_definition.h\"");
      _builder.newLine();
      _builder.append("#include \"../inc/config.h\"");
      _builder.newLine();
      _builder.append("#include \"FreeRTOS.h\"");
      _builder.newLine();
      _builder.append("#include \"semphr.h\"");
      _builder.newLine();
      _builder.append("#include \"timers.h\"\t");
      _builder.newLine();
      _builder.append("#include \"queue.h\"");
      _builder.newLine();
      _builder.append("#if FREERTOS==1");
      _builder.newLine();
      _builder.append("void task_");
      _builder.append(name);
      _builder.append("(void* pdata);");
      _builder.newLineIfNotEmpty();
      _builder.append("void timer_");
      _builder.append(name);
      _builder.append("_callback(TimerHandle_t xTimer);");
      _builder.newLineIfNotEmpty();
      _builder.append("#endif");
      _builder.newLine();
      _builder.newLine();
      _builder.append("#endif");
      _builder.newLine();
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
}
