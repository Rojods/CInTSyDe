package template.templateInterface;

import generator.Schedule;

@SuppressWarnings("all")
public interface SubsystemTemplate {
  String create(final Schedule s);
  
  String savePath();
}
