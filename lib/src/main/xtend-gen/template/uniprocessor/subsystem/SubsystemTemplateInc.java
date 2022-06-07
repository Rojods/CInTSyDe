package template.uniprocessor.subsystem;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import generator.Schedule;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.templateInterface.SubsystemTemplate;

@FileTypeAnno(type = FileType.C_INCLUDE)
@SuppressWarnings("all")
public class SubsystemTemplateInc implements SubsystemTemplate {
  @Override
  public String create(final Schedule s) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#ifndef SUBSYSTEM_");
    int _hashCode = this.hashCode();
    _builder.append(_hashCode);
    _builder.append("_H_");
    _builder.newLineIfNotEmpty();
    _builder.append("#define SUBSYSTEM_");
    int _hashCode_1 = this.hashCode();
    _builder.append(_hashCode_1);
    _builder.append("_H_");
    _builder.newLineIfNotEmpty();
    _builder.append("/* Includes--------------------*/");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/* Function Prototype----------*/");
    _builder.newLine();
    _builder.append("int init_subsystem();");
    _builder.newLine();
    _builder.append("int subsystem();");
    _builder.newLine();
    _builder.append("#endif");
    _builder.newLine();
    return _builder.toString();
  }
  
  @Override
  public String savePath() {
    return "/tile/subsystem.h";
  }
}
