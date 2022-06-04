package template.baremetal_multi;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.templateInterface.InitTemplate;

@FileTypeAnno(type = FileType.C_INCLUDE)
@SuppressWarnings("all")
public class SpinLockTemplateInc implements InitTemplate {
  @Override
  public String create() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#ifndef SPINLOCK_H_");
    _builder.newLine();
    _builder.append("#define SPINLOCK_H_");
    _builder.newLine();
    _builder.append("#include \"config.h\"");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("#define ARM");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("typedef struct{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("volatile\tint flag;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}spinlock;");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("void spinlock_get(spinlock* lock);");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("void spinlock_release(spinlock* lock);");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.append("#endif");
    _builder.newLine();
    return _builder.toString();
  }
  
  @Override
  public String getFileName() {
    return "spinlock";
  }
}
