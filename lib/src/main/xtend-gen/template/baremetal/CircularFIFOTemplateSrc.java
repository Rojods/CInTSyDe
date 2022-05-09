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

@FileTypeAnno(type = FileType.C_SOURCE)
@SuppressWarnings("all")
public class CircularFIFOTemplateSrc implements InitTemplate {
  private Set<Vertex> typeVertexSet;
  
  public CircularFIFOTemplateSrc() {
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
      + "\nno viable alternative at input \'Â\'"
      + "\nno viable alternative at input \'Â\'"
      + "\nno viable alternative at input \'»\\r\\n\\t\\t\\r\\n\\t\\t\'\'\'\'"
      + "\nno viable alternative at input \'}\'"
      + "\nno viable alternative at input \'produce\'"
      + "\nextraneous input \'typeVertex\' expecting \')\'"
      + "\nno viable alternative at input \'{\'"
      + "\nno viable alternative at input \'Â\'"
      + "\nmissing \'}\' at \'»\\r\\n\\t\\t\\t\\tÂ«\'"
      + "\nno viable alternative at input \'Â\'"
      + "\nno viable alternative at input \'Â\'"
      + "\nno viable alternative at input \'Â\'"
      + "\nno viable alternative at input \'Â\'"
      + "\nno viable alternative at input \'Â\'"
      + "\nno viable alternative at input \'Â\'"
      + "\nno viable alternative at input \'Â\'"
      + "\nno viable alternative at input \'»\\r\\n\\t\\r\\n\\t\\t\'\'\'\'"
      + "\nno viable alternative at input \'}\'"
      + "\nno viable alternative at input \'getFileName\'"
      + "\nno viable alternative at input \'{\'"
      + "\nno viable alternative at input \'def\'"
      + "\nno viable alternative at input \'isOneDimension\'"
      + "\nextraneous input \'v\' expecting \')\'"
      + "\nno viable alternative at input \'{\'"
      + "\nno viable alternative at input \'}\'"
      + "\nThe method or field Â is undefined"
      + "\nThe method or field Â is undefined"
      + "\nThe method or field ENDFORÂ is undefined"
      + "\nThe method or field Â is undefined"
      + "\nThe method produce(Vertex) is undefined"
      + "\nThe method or field Â is undefined"
      + "\nThe method or field ENDFORÂ is undefined"
      + "\nThe method or field def is undefined"
      + "\nThe method produce(Class<Vertex>) is undefined"
      + "\nThe method or field Â is undefined"
      + "\nThe method or field Â is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method isOneDimension(Vertex) is undefined"
      + "\nThe method or field Â is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field Â is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field Â is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field Â is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field Â is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method isOneDimension(Vertex) is undefined"
      + "\nThe method or field Â is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field typeÂ is undefined"
      + "\nThe method or field ENDIFÂ is undefined"
      + "\nThe method or field override is undefined"
      + "\nThe method or field def is undefined"
      + "\nThe method isOneDimension(Class<Vertex>) is undefined"
      + "\nThe method or field v is undefined"
      + "\nDuplicate local variable typeVertex"
      + "\nType mismatch: cannot convert from boolean to String"
      + "\nType mismatch: cannot convert from boolean to String"
      + "\nUnreachable expression."
      + "\nThis expression is not allowed in this context, since it doesn\'t cause any side effects."
      + "\n&& cannot be resolved"
      + "\n&& cannot be resolved");
  }
}
