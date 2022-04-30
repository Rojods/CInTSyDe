package template.rtos;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.Vertex;
import generator.Generator;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.templateInterface.InitTemplate;

@FileTypeAnno(type = FileType.C_INCLUDE)
@SuppressWarnings("all")
public class ConfigRTOSInc implements InitTemplate {
  public String create() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#ifndef CONFIG_H_");
    _builder.newLine();
    _builder.append("#define CONFIG_H_");
    _builder.newLine();
    _builder.append("/*");
    _builder.newLine();
    _builder.append("************************************************");
    _builder.newLine();
    _builder.append("This file define the stack size for each task");
    _builder.newLine();
    _builder.append("************************************************");
    _builder.newLine();
    _builder.append("*/");
    _builder.newLine();
    _builder.append("#define STARTTASK_STACKSIZE 2048");
    _builder.newLine();
    {
      boolean _hasElements = false;
      for(final Vertex actor : Generator.sdfcombSet) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate("", "");
        }
        _builder.append("#define ");
        String _upperCase = actor.getIdentifier().toUpperCase();
        _builder.append(_upperCase);
        _builder.append("_STACKSIZE 2048");
        _builder.newLineIfNotEmpty();
      }
      if (_hasElements) {
        _builder.append("");
      }
    }
    _builder.append("#endif\t\t");
    _builder.newLine();
    return _builder.toString();
  }
  
  public String getFileName() {
    return "config";
  }
}
