package template.templateInterface;

import forsyde.io.java.core.Vertex;

@SuppressWarnings("all")
public interface SubsystemTemplate {
  String create(final Vertex tile);
  
  String savePath();
}
