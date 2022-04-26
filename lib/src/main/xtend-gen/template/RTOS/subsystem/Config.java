package template.RTOS.subsystem;

import generator.generator;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.Template;
import utils.Save;

@SuppressWarnings("all")
public class Config implements Template {
  public void create() {
    Save.save(this.path(), this.config());
  }
  
  public String config() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#ifndef CONFIG_H_");
    _builder.newLine();
    _builder.append("#define CONFIG_H_");
    _builder.newLine();
    _builder.append("//#include \"\"");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#define TASK_STACKSIZE 2048");
    _builder.newLine();
    _builder.append("#endif");
    _builder.newLine();
    return _builder.toString();
  }
  
  private String path() {
    return (generator.root + "/include/freertos_config.h");
  }
}
