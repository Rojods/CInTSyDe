package template.baremetal_multi;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.typed.viewers.impl.Executable;
import forsyde.io.java.typed.viewers.moc.sdf.SDFActorViewer;
import generator.Generator;
import java.util.Set;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.templateInterface.ActorTemplate;

@FileTypeAnno(type = FileType.C_INCLUDE)
@SuppressWarnings("all")
public class SDFActorInc implements ActorTemplate {
  private Set<Executable> a;
  
  private Vertex actor;
  
  @Override
  public String create(final Vertex actor) {
    String _xblockexpression = null;
    {
      this.actor = actor;
      this.a = new SDFActorViewer(actor).getCombFunctionsPort(Generator.model);
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
      _builder.append("\t\t\t");
      _builder.newLineIfNotEmpty();
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
  
  @Override
  public String savePath() {
    String _identifier = this.actor.getIdentifier();
    String _plus = ("/sdfactor/sdfactor_" + _identifier);
    return (_plus + ".h");
  }
}
