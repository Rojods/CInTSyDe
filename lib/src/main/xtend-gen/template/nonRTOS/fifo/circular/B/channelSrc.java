package template.nonRTOS.fifo.circular.B;

import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel;
import java.util.function.Consumer;
import java.util.function.Predicate;
import template.Template;
import utils.Save;

@SuppressWarnings("all")
public class channelSrc implements Template {
  private ForSyDeSystemGraph model;
  
  private String root;
  
  public channelSrc(final ForSyDeSystemGraph model) {
    this.model = model;
  }
  
  public void create() {
    final Predicate<Vertex> _function = new Predicate<Vertex>() {
      public boolean test(final Vertex v) {
        return (SDFChannel.conforms(v)).booleanValue();
      }
    };
    final Consumer<Vertex> _function_1 = new Consumer<Vertex>() {
      public void accept(final Vertex v) {
        Save.save(channelSrc.this.path(v), channelSrc.this.src(v));
      }
    };
    this.model.vertexSet().stream().filter(_function).forEach(_function_1);
  }
  
  public String src(final Vertex v) {
    throw new Error("Unresolved compilation problems:"
      + "\n< cannot be resolved."
      + "\nThe method or field types is undefined"
      + "\nThe method or field types is undefined"
      + "\n> cannot be resolved");
  }
  
  private String path(final Vertex vertex) {
    return (this.root + "/source/channels_types.c");
  }
}
