package template.baremetal;

import forsyde.io.java.core.Vertex;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.templateInterface.ActorTemplate;
import utils.Name;
import utils.Query;

@SuppressWarnings("all")
public class SDFCombTemplateSrc implements ActorTemplate {
  public String create(final Vertex vertex) {
    StringConcatenation _builder = new StringConcatenation();
    String name = Name.name(vertex);
    _builder.newLineIfNotEmpty();
    _builder.append("inline void actor_");
    _builder.append(name);
    _builder.append("(){");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("//initilize the memory");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("//read from the input port");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("//inline code");
    _builder.newLine();
    _builder.append("\t");
    String _inlineCode = Query.getInlineCode(vertex);
    _builder.append(_inlineCode, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("//write to the output port");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder.toString();
  }
}
