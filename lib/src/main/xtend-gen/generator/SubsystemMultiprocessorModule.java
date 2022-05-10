package generator;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import template.templateInterface.SubsystemTemplate;

@SuppressWarnings("all")
public class SubsystemMultiprocessorModule implements ModuleInterface {
  private Set<SubsystemTemplate> templates;
  
  public SubsystemMultiprocessorModule() {
    HashSet<SubsystemTemplate> _hashSet = new HashSet<SubsystemTemplate>();
    this.templates = _hashSet;
  }
  
  public void create() {
    final Consumer<Schedule> _function = new Consumer<Schedule>() {
      public void accept(final Schedule schedule) {
        SubsystemMultiprocessorModule.this.process(schedule);
      }
    };
    Generator.multiProcessorSchedules.stream().forEach(_function);
  }
  
  public void process(final Schedule s) {
    throw new Error("Unresolved compilation problems:"
      + "\n== cannot be resolved."
      + "\n+ cannot be resolved."
      + "\n== cannot be resolved."
      + "\n+ cannot be resolved."
      + "\nThe field Schedule.tile refers to the missing type Vertex"
      + "\nThe field Schedule.tile refers to the missing type Vertex"
      + "\n+ cannot be resolved"
      + "\ngetIdentifier cannot be resolved"
      + "\n+ cannot be resolved"
      + "\n+ cannot be resolved"
      + "\ngetIdentifier cannot be resolved"
      + "\n+ cannot be resolved");
  }
  
  public boolean add(final SubsystemTemplate t) {
    return this.templates.add(t);
  }
}
