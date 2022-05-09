package template.baremetal;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel;
import generator.Generator;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import template.templateInterface.InitTemplate;
import utils.Query;

@FileTypeAnno(type = FileType.C_INCLUDE)
@SuppressWarnings("all")
public class CircularFIFOTemplateInc implements InitTemplate {
  private Set<Vertex> typeVertexSet;
  
  public CircularFIFOTemplateInc() {
    final ForSyDeSystemGraph model = Generator.model;
    final Predicate<Vertex> _function = new Predicate<Vertex>() {
      public boolean test(final Vertex v) {
        return (SDFChannel.conforms(v)).booleanValue();
      }
    };
    final Function<Vertex, String> _function_1 = new Function<Vertex, String>() {
      public String apply(final Vertex v) {
        return Query.findSDFChannelDataType(model, v);
      }
    };
    final Function<String, Vertex> _function_2 = new Function<String, Vertex>() {
      public Vertex apply(final String s) {
        return Query.findVertexByName(model, s);
      }
    };
    this.typeVertexSet = model.vertexSet().stream().filter(_function).<String>map(_function_1).<Vertex>map(_function_2).collect(Collectors.<Vertex>toSet());
  }
  
  public String create() {
    throw new Error("Unresolved compilation problems:"
      + "\nno viable alternative at input \'Â\'"
      + "\nno viable alternative at input \'Â\'"
      + "\nno viable alternative at input \'»\\r\\n\\t\\t\\t#endif\\r\\n\\t\\t\'\'\'\'"
      + "\nThe method or field ENDFORÂ is undefined"
      + "\nThe field CircularFIFOTemplateInc.Â refers to the missing type Â«"
      + "\nThe field CircularFIFOTemplateInc.Â refers to the missing type Â«");
  }
  
  public String getFileName() {
    return "circular_fifo_lib";
  }
  
  public String foo(final Vertex v) {
    throw new Error("Unresolved compilation problems:"
      + "\nno viable alternative at input \'Â\'"
      + "\nmismatched input \'»\\r\\nÂ«\' expecting \'}\'"
      + "\nThis expression is not allowed in this context, since it doesn\'t cause any side effects."
      + "\nThe field CircularFIFOTemplateInc.Â refers to the missing type Â«");
  }
  
  private /* Â« */Object Â;
}
