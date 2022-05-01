package template.baremetal;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.core.VertexAcessor;
import forsyde.io.java.core.VertexTrait;
import generator.Generator;
import java.util.Set;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.templateInterface.ActorTemplate;

@FileTypeAnno(type = FileType.C_INCLUDE)
@SuppressWarnings("all")
public class SDFCombTemplateInc implements ActorTemplate {
  private Set<Vertex> implActorSet;
  
  public String create(final Vertex actor) {
    String _xblockexpression = null;
    {
      this.implActorSet = VertexAcessor.getMultipleNamedPort(Generator.model, actor, "combFunctions", VertexTrait.IMPL_ANSICBLACKBOXEXECUTABLE, VertexAcessor.VertexPortDirection.OUTGOING);
      StringConcatenation _builder = new StringConcatenation();
      String name = actor.getIdentifier();
      _builder.newLineIfNotEmpty();
      String _upperCase = name.toUpperCase();
      String tmp = (_upperCase + "_H_");
      _builder.newLineIfNotEmpty();
      _builder.append("#ifndef  ");
      _builder.append(tmp);
      _builder.newLineIfNotEmpty();
      _builder.append("#define ");
      _builder.append(tmp);
      _builder.newLineIfNotEmpty();
      _builder.append("#include \"datatype_definition.h\"");
      _builder.newLine();
      _builder.append("void actor_");
      _builder.append(name);
      _builder.append("();");
      _builder.newLineIfNotEmpty();
      _builder.append("#endif");
      _builder.newLine();
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
}
