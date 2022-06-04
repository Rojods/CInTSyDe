package template.rtos;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.templateInterface.InitTemplate;

@FileTypeAnno(type = FileType.C_INCLUDE)
@SuppressWarnings("all")
public class FireAllInc implements InitTemplate {
  @Override
  public String create() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#ifndef FIRE_ALL_H_");
    _builder.newLine();
    _builder.append("#define FIRE_ALL_H_");
    _builder.newLine();
    _builder.append("void fire_all();");
    _builder.newLine();
    _builder.append("#endif");
    _builder.newLine();
    _builder.newLine();
    return _builder.toString();
  }
  
  @Override
  public String getFileName() {
    return "fire_all";
  }
}
