package template.nonRTOS.fifo.circular.B;

import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel;
import java.util.function.Consumer;
import java.util.function.Predicate;
import template.Template;
import utils.Name;
import utils.Save;

@SuppressWarnings("all")
public class SDFChannelSource implements Template {
  private ForSyDeSystemGraph model;
  
  private String root;
  
  public SDFChannelSource(final ForSyDeSystemGraph model) {
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
        Save.save(SDFChannelSource.this.path(v), SDFChannelSource.this.createSource(v));
      }
    };
    this.model.vertexSet().stream().filter(_function).forEach(_function_1);
  }
  
  public String createSource(final Vertex vertex) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field type is undefined"
      + "\nThe method or field size is undefined");
  }
  
  private String path(final Vertex vertex) {
    String _name = Name.name(vertex);
    String _plus = ((this.root + "/source/sdfchannel_") + _name);
    return (_plus + ".c");
  }
}
