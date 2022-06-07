package template.templateInterface;

import forsyde.io.java.core.Vertex;

@SuppressWarnings("all")
public interface ActorTemplate {
  String create(final Vertex vertex);
  
  String savePath();
}
