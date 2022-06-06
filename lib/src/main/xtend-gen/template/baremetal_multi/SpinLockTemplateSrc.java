package template.baremetal_multi;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.templateInterface.InitTemplate;

@FileTypeAnno(type = FileType.C_SOURCE)
@SuppressWarnings("all")
public class SpinLockTemplateSrc implements InitTemplate {
  @Override
  public String create() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#include \"spinlock.h\"");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("#if defined(WINDOWS)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("#define ATOMIC_TEST_AND_SET   _InterlockedExchange");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("#endif");
    _builder.newLine();
    _builder.append("\t ");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("#if defined(LINUX)|| defined(ARM)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("#define ATOMIC_TEST_AND_SET  __sync_lock_test_and_set");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("#endif");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("void spinlock_get(spinlock* lock){");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("//\twhile(ATOMIC_TEST_AND_SET(&lock->flag,1)==1){");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("//\t}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("void spinlock_release(spinlock* lock){");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("//\tATOMIC_TEST_AND_SET(&lock->flag,0);");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}\t");
    _builder.newLine();
    _builder.newLine();
    return _builder.toString();
  }
  
  @Override
  public String savePath() {
    return "/circular_fifo_lib/spinlock.c";
  }
}
