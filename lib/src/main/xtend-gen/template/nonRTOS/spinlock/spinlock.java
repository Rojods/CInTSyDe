package template.nonRTOS.spinlock;

import generator.generator;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.Template;
import utils.Save;

@SuppressWarnings("all")
public class spinlock implements Template {
  public void create() {
    Save.save((generator.root + "/inc/spinlock.h"), this.inc());
    Save.save((generator.root + "/src/spinlock.c"), this.src());
  }
  
  public String inc() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#ifndef SPINLOCK_H_");
    _builder.newLine();
    _builder.append("#define SPINLOCK_H_");
    _builder.newLine();
    _builder.append("typedef struct{");
    _builder.newLine();
    _builder.append("volatile\tint flag;");
    _builder.newLine();
    _builder.append("}spinlock;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("void spinlock_get(spinlock* lock);");
    _builder.newLine();
    _builder.append("void spinlock_release(spinlock* lock);");
    _builder.newLine();
    _builder.append("#endif");
    _builder.newLine();
    return _builder.toString();
  }
  
  public String src() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#include \"../inc/spinlock.h\"");
    _builder.newLine();
    _builder.append("void spinlock_get(spinlock* lock){");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("while(__sync_lock_test_and_set(&lock->flag,1)==1){");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.append("void spinlock_release(spinlock* lock){");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("__sync_lock_test_and_set(&lock->flag,0);");
    _builder.newLine();
    _builder.append("}\t");
    _builder.newLine();
    return _builder.toString();
  }
}
