package generator;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import template.templateInterface.InitTemplate;

@SuppressWarnings("all")
public class InitProcessingModule implements ModuleInterface {
  private Set<InitTemplate> templateSet;
  
  public InitProcessingModule() {
    HashSet<InitTemplate> _hashSet = new HashSet<InitTemplate>();
    this.templateSet = _hashSet;
  }
  
  public void create() {
    final Consumer<InitTemplate> _function = new Consumer<InitTemplate>() {
      public void accept(final InitTemplate t) {
        InitProcessingModule.this.process(t);
      }
    };
    this.templateSet.stream().forEach(_function);
  }
  
  public void process(final InitTemplate t) {
    throw new Error("Unresolved compilation problems:"
      + "\n== cannot be resolved."
      + "\nThe method println(String) is undefined"
      + "\n+ cannot be resolved."
      + "\n== cannot be resolved."
      + "\n+ cannot be resolved."
      + "\n+ cannot be resolved"
      + "\n+ cannot be resolved"
      + "\n+ cannot be resolved"
      + "\n+ cannot be resolved");
  }
  
  public boolean add(final InitTemplate t) {
    return this.templateSet.add(t);
  }
}
