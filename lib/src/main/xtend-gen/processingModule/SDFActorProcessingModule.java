package processingModule;

import java.util.HashSet;
import java.util.Set;
import template.templateInterface.ActorTemplate;

@SuppressWarnings("all")
public class SDFActorProcessingModule implements ModuleInterface {
  private Set<ActorTemplate> templates;
  
  public SDFActorProcessingModule() {
    HashSet<ActorTemplate> _hashSet = new HashSet<ActorTemplate>();
    this.templates = _hashSet;
  }
  
  public void create() {
    throw new Error("Unresolved compilation problems:"
      + "\nSDFActor cannot be resolved to a type."
      + "\nThe field Generator.model refers to the missing type ForSyDeSystemGraph"
      + "\nThe method process(Vertex) from the type SDFActorProcessingModule refers to the missing type Vertex"
      + "\nvertexSet cannot be resolved"
      + "\nstream cannot be resolved"
      + "\nfilter cannot be resolved"
      + "\nconforms cannot be resolved"
      + "\nforEach cannot be resolved");
  }
  
  public void process(final /* Vertex */Object v) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method save(ForSyDeSystemGraph, Vertex, ActorTemplate) from the type SDFActorProcessingModule refers to the missing type ForSyDeSystemGraph"
      + "\nThe field Generator.model refers to the missing type ForSyDeSystemGraph");
  }
  
  public void add(final ActorTemplate t) {
    this.templates.add(t);
  }
  
  public boolean save(final /* ForSyDeSystemGraph */Object model, final /* Vertex */Object actor, final ActorTemplate t) {
    throw new Error("Unresolved compilation problems:"
      + "\nVertex cannot be resolved to a type."
      + "\n== cannot be resolved."
      + "\n+ cannot be resolved."
      + "\n== cannot be resolved."
      + "\nThe method findTile(ForSyDeSystemGraph, Vertex) is undefined for the type Class<Query>"
      + "\n+ cannot be resolved."
      + "\n== cannot be resolved."
      + "\n+ cannot be resolved."
      + "\nThe method create(Vertex) from the type ActorTemplate refers to the missing type Vertex"
      + "\nThe field Generator.model refers to the missing type ForSyDeSystemGraph"
      + "\nThe method create(Vertex) from the type ActorTemplate refers to the missing type Vertex"
      + "\nThe method create(Vertex) from the type ActorTemplate refers to the missing type Vertex"
      + "\n+ cannot be resolved"
      + "\n!== cannot be resolved"
      + "\n+ cannot be resolved"
      + "\ngetIdentifier cannot be resolved"
      + "\n+ cannot be resolved"
      + "\n+ cannot be resolved");
  }
}
