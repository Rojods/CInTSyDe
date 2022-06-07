package template.templateInterface;

import processingModule.Schedule;

@SuppressWarnings("all")
public interface SubsystemTemplate {
  String create(final Schedule s);
  
  String savePath();
}
