package template.uniprocessor.others;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel;
import generator.Generator;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.templateInterface.InitTemplate;

@FileTypeAnno(type = FileType.C_INCLUDE)
@SuppressWarnings("all")
public class Config implements InitTemplate {
  @Override
  public String create() {
    String _xblockexpression = null;
    {
      ForSyDeSystemGraph model = Generator.model;
      final Predicate<Vertex> _function = (Vertex v) -> {
        return (SDFChannel.conforms(v)).booleanValue();
      };
      Set<Vertex> channels = model.vertexSet().stream().filter(_function).collect(Collectors.<Vertex>toSet());
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("#ifndef CONFIG_H_");
      _builder.newLine();
      _builder.append("#define CONFIG_H_");
      _builder.newLine();
      _builder.newLine();
      _builder.newLine();
      _builder.append("/*");
      _builder.newLine();
      _builder.append("*************************************************************");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("Config Channel Block or Non Block Read Write");
      _builder.newLine();
      _builder.append("*************************************************************");
      _builder.newLine();
      _builder.append("*/");
      _builder.newLine();
      {
        for(final Vertex c : channels) {
          _builder.append("#define ");
          String _upperCase = c.getIdentifier().toUpperCase();
          _builder.append(_upperCase);
          _builder.append("_BLOCKING 0");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.append("#endif");
      _builder.newLine();
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
  
  @Override
  public String getFileName() {
    return "config";
  }
}
