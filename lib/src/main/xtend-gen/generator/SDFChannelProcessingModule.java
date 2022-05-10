package generator;

import java.util.HashSet;
import java.util.Set;
import template.templateInterface.ChannelTemplate;

@SuppressWarnings("all")
public class SDFChannelProcessingModule implements ModuleInterface {
  private Set<ChannelTemplate> templates;
  
  public SDFChannelProcessingModule() {
    HashSet<ChannelTemplate> _hashSet = new HashSet<ChannelTemplate>();
    this.templates = _hashSet;
  }
  
  public void create() {
    throw new Error("Unresolved compilation problems:"
      + "\nSDFChannel cannot be resolved to a type."
      + "\nThe field Generator.model refers to the missing type ForSyDeSystemGraph"
      + "\nThe method process(Vertex) from the type SDFChannelProcessingModule refers to the missing type Vertex"
      + "\nvertexSet cannot be resolved"
      + "\nstream cannot be resolved"
      + "\nfilter cannot be resolved"
      + "\nconforms cannot be resolved"
      + "\nforEach cannot be resolved");
  }
  
  public void process(final /* Vertex */Object v) {
    throw new Error("Unresolved compilation problems:"
      + "\n== cannot be resolved."
      + "\n+ cannot be resolved."
      + "\n== cannot be resolved."
      + "\n+ cannot be resolved."
      + "\nThe method name(Vertex) from the type Name refers to the missing type Vertex"
      + "\nThe method create(Vertex) from the type ChannelTemplate refers to the missing type Vertex"
      + "\nThe method name(Vertex) from the type Name refers to the missing type Vertex"
      + "\nThe method create(Vertex) from the type ChannelTemplate refers to the missing type Vertex"
      + "\n+ cannot be resolved"
      + "\n+ cannot be resolved"
      + "\n+ cannot be resolved"
      + "\n+ cannot be resolved");
  }
  
  public void add(final ChannelTemplate t) {
    this.templates.add(t);
  }
}
