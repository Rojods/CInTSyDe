package generator;

import forsyde.io.java.core.Vertex;
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import template.templateInterface.ChannelTemplate;

@SuppressWarnings("all")
public class SDFChannelProcessingModule implements ModuleInterface {
  private Set<ChannelTemplate> templates;
  
  public SDFChannelProcessingModule() {
    HashSet<ChannelTemplate> _hashSet = new HashSet<ChannelTemplate>();
    this.templates = _hashSet;
  }
  
  public void create() {
    final Predicate<Vertex> _function = new Predicate<Vertex>() {
      public boolean test(final Vertex v) {
        return (SDFChannel.conforms(v)).booleanValue();
      }
    };
    final Consumer<Vertex> _function_1 = new Consumer<Vertex>() {
      public void accept(final Vertex v) {
        SDFChannelProcessingModule.this.process(v);
      }
    };
    Generator.model.vertexSet().stream().filter(_function).forEach(_function_1);
  }
  
  public void process(final Vertex v) {
    final Consumer<ChannelTemplate> _function = new Consumer<ChannelTemplate>() {
      public void accept(final ChannelTemplate t) {
        t.create(v);
      }
    };
    this.templates.stream().forEach(_function);
  }
  
  public void add(final ChannelTemplate t) {
    this.templates.add(t);
  }
}
