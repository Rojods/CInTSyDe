package template.baremetal_multi;

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
import utils.Query;

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
      _builder.append("#include <cheap_s.h>");
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
          {
            boolean _isOnOneCoreChannel = Query.isOnOneCoreChannel(model, c);
            if (_isOnOneCoreChannel) {
              _builder.append("#define ");
              String _upperCase = c.getIdentifier().toUpperCase();
              _builder.append(_upperCase);
              _builder.append("_BLOCKING 0");
              _builder.newLineIfNotEmpty();
            }
          }
        }
      }
      _builder.newLine();
      {
        for(final Vertex c_1 : channels) {
          {
            boolean _isOnOneCoreChannel_1 = Query.isOnOneCoreChannel(model, c_1);
            boolean _not = (!_isOnOneCoreChannel_1);
            if (_not) {
              _builder.append("#define ");
              String _upperCase_1 = c_1.getIdentifier().toUpperCase();
              _builder.append(_upperCase_1);
              _builder.append("_ADDR 0x80020000");
              _builder.newLineIfNotEmpty();
            }
          }
        }
      }
      _builder.append("#endif\t\t");
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
