package template.rtos;

import org.eclipse.xtend2.lib.StringConcatenation;
import template.templateInterface.InitTemplate;

@SuppressWarnings("all")
public class StartTaskInc implements InitTemplate {
  @Override
  public String create() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#ifndef  SUBSYSTEM_H_");
    _builder.newLine();
    _builder.append("#define  SUBSYSTEM_H_");
    _builder.newLine();
    _builder.append("void init_subsystem();");
    _builder.newLine();
    _builder.append("#endif");
    _builder.newLine();
    return _builder.toString();
  }
  
  @Override
  public String savePath() {
    return "/start_task.h";
  }
}
