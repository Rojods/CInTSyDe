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
public class SDFChannelHeader implements Template {
  private ForSyDeSystemGraph model;
  
  private String root;
  
  public SDFChannelHeader(final ForSyDeSystemGraph model) {
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
        Save.save(SDFChannelHeader.this.path(v), SDFChannelHeader.this.inc(v));
      }
    };
    this.model.vertexSet().stream().filter(_function).forEach(_function_1);
  }
  
  /**
   * @param vertex	this vertex must have moc::sdf::sdfchannel trait
   */
  public String inc(final Vertex vertex) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field type is undefined");
  }
  
  private String path(final Vertex vertex) {
    String _name = Name.name(vertex);
    String _plus = ((this.root + "/include/sdfchannel_") + _name);
    return (_plus + ".h");
  }
}
