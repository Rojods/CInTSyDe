package strategy.c.rtos.freertos;

import forsyde.io.java.core.ForSyDeSystemGraph;
import org.eclipse.xtend2.lib.StringConcatenation;
import strategy.Strategy;
import util.Save;

@SuppressWarnings("all")
public class fooInc implements Strategy {
  private String root;
  
  private ForSyDeSystemGraph model;
  
  public fooInc(final ForSyDeSystemGraph model) {
    this.model = model;
  }
  
  public void create() {
    Save.save(this.path(), this.main());
  }
  
  public void setSaveRoot(final String root) {
    this.root = root;
  }
  
  public String main() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("#ifndef FOO_H_");
    _builder.newLine();
    _builder.append("#define FOO_H_");
    _builder.newLine();
    _builder.append("#include \"../include/freertos_StartTask.h\"");
    _builder.newLine();
    _builder.append("#include \"../include/freertos_config.h\"");
    _builder.newLine();
    _builder.append("#include \"FreeRTOS.h\"");
    _builder.newLine();
    _builder.append("#include \"task.h\"");
    _builder.newLine();
    _builder.append("#include \"semphr.h\"");
    _builder.newLine();
    _builder.append("#include \"timers.h\"");
    _builder.newLine();
    _builder.append("int system();");
    _builder.newLine();
    _builder.append("#endif\t\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    return _builder.toString();
  }
  
  private String path() {
    return (this.root + "/include/foo.h");
  }
}
