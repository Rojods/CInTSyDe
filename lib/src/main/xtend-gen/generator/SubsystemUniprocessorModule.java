package generator;

import java.util.HashSet;
import java.util.Set;
import template.templateInterface.SubsystemTemplate;

@SuppressWarnings("all")
public class SubsystemUniprocessorModule implements ModuleInterface {
  private Set<SubsystemTemplate> templates;
  
  public SubsystemUniprocessorModule() {
    HashSet<SubsystemTemplate> _hashSet = new HashSet<SubsystemTemplate>();
    this.templates = _hashSet;
  }
  
  public void create() {
    this.process();
  }
  
  public void process() {
    throw new Error("Unresolved compilation problems:"
      + "\n== cannot be resolved."
      + "\n+ cannot be resolved."
      + "\n== cannot be resolved."
      + "\n+ cannot be resolved."
      + "\n+ cannot be resolved"
      + "\n+ cannot be resolved"
      + "\n+ cannot be resolved"
      + "\n+ cannot be resolved");
  }
  
  public boolean add(final SubsystemTemplate t) {
    return this.templates.add(t);
  }
}
