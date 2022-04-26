package template.nonRTOS.subsystem;

import forsyde.io.java.core.Vertex;
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel;
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb;
import generator.generator;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.Template;
import utils.Global;
import utils.Name;
import utils.Save;

@SuppressWarnings("all")
public class subsystemIncUniprocessor implements Template {
  public subsystemIncUniprocessor() {
  }
  
  public void create() {
    String str = this.createHeader();
    Save.save((generator.root + "/inc/subsystem.h"), str);
  }
  
  public String createHeader() {
    String _xblockexpression = null;
    {
      final Predicate<Vertex> _function = new Predicate<Vertex>() {
        public boolean test(final Vertex v) {
          return (SDFChannel.conforms(v)).booleanValue();
        }
      };
      Set<Vertex> sdfChannelSet = Global.model.vertexSet().stream().filter(_function).collect(Collectors.<Vertex>toSet());
      final Predicate<Vertex> _function_1 = new Predicate<Vertex>() {
        public boolean test(final Vertex v) {
          return (SDFComb.conforms(v)).booleanValue();
        }
      };
      Set<Vertex> sdfCombSet = Global.model.vertexSet().stream().filter(_function_1).collect(Collectors.<Vertex>toSet());
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("#ifndef SYSTEM_H_");
      _builder.newLine();
      _builder.append("#define SYSTEM_H_");
      _builder.newLine();
      _builder.newLine();
      {
        boolean _hasElements = false;
        for(final Vertex channel : sdfChannelSet) {
          if (!_hasElements) {
            _hasElements = true;
          } else {
            _builder.appendImmediate("", "");
          }
          _builder.append("#include \"../inc/sdfchannel_");
          String _name = Name.name(channel);
          _builder.append(_name);
          _builder.append(".h\"");
          _builder.newLineIfNotEmpty();
        }
        if (_hasElements) {
          _builder.append("\n");
        }
      }
      _builder.newLine();
      {
        boolean _hasElements_1 = false;
        for(final Vertex sdf : sdfCombSet) {
          if (!_hasElements_1) {
            _hasElements_1 = true;
          } else {
            _builder.appendImmediate("", "");
          }
          _builder.append("#include \"../inc/sdfcomb_");
          String _name_1 = Name.name(sdf);
          _builder.append(_name_1);
          _builder.append(".h\"");
          _builder.newLineIfNotEmpty();
        }
        if (_hasElements_1) {
          _builder.append("\n");
        }
      }
      _builder.newLine();
      _builder.append("//system input and system output");
      _builder.newLine();
      _builder.append("void subsystem();");
      _builder.newLine();
      _builder.newLine();
      _builder.append("#endif");
      _builder.newLine();
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
}
