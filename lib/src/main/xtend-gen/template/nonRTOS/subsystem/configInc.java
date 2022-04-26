package template.nonRTOS.subsystem;

import forsyde.io.java.core.Vertex;
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel;
import generator.generator;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.Template;
import utils.Global;
import utils.Name;
import utils.Save;

@SuppressWarnings("all")
public class configInc implements Template {
  public void create() {
    Save.save(this.path(), this.inc());
  }
  
  public String inc() {
    String _xblockexpression = null;
    {
      final Predicate<Vertex> _function = new Predicate<Vertex>() {
        public boolean test(final Vertex v) {
          return (SDFChannel.conforms(v)).booleanValue();
        }
      };
      final Function<Vertex, SDFChannel> _function_1 = new Function<Vertex, SDFChannel>() {
        public SDFChannel apply(final Vertex v) {
          return SDFChannel.enforce(v);
        }
      };
      Set<SDFChannel> sdfchannels = Global.model.vertexSet().stream().filter(_function).<SDFChannel>map(_function_1).collect(Collectors.<SDFChannel>toSet());
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("#ifndef CONFIG_H_");
      _builder.newLine();
      _builder.append("#define CONFIG_H_");
      _builder.newLine();
      {
        boolean _hasElements = false;
        for(final SDFChannel cha : sdfchannels) {
          if (!_hasElements) {
            _hasElements = true;
          } else {
            _builder.appendImmediate("", "");
          }
          _builder.append("#define ");
          String _upperCase = Name.name(cha).toUpperCase();
          _builder.append(_upperCase);
          _builder.append("_NONBLOCKING 0");
          _builder.newLineIfNotEmpty();
        }
        if (_hasElements) {
          _builder.append("\n");
        }
      }
      _builder.newLine();
      _builder.append("#endif");
      _builder.newLine();
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
  
  private String path() {
    return (generator.root + "/inc/config.h");
  }
}
