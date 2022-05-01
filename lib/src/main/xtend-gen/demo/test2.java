package demo;

import forsyde.io.java.core.EdgeInfo;
import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import utils.Load;
import utils.Query;

@SuppressWarnings("all")
public class test2 {
  public static void main(final String[] args) {
    final String path1 = "forsyde-io\\complete-mapped-sobel-model.forsyde.xmi";
    final String path2 = "forsyde-io\\sobel-application.fiodl";
    final String root = "generateCode\\c\\single";
    ForSyDeSystemGraph model1 = Load.load(path1);
    ForSyDeSystemGraph model2 = Load.load(path2);
    model2.mergeInPlace(model1);
    AllDirectedPaths<Vertex, EdgeInfo> dj = new AllDirectedPaths<Vertex, EdgeInfo>(model2);
    test2.test(model2);
  }
  
  public static void test(final ForSyDeSystemGraph model) {
    final Predicate<Vertex> _function = new Predicate<Vertex>() {
      public boolean test(final Vertex v) {
        return (SDFComb.conforms(v)).booleanValue();
      }
    };
    Set<Vertex> actors = model.vertexSet().stream().filter(_function).collect(Collectors.<Vertex>toSet());
    for (final Vertex actor : actors) {
      {
        String _identifier = actor.getIdentifier();
        String _plus = ("in sdfcomb " + _identifier);
        String _plus_1 = (_plus + "========================================");
        InputOutput.<String>println(_plus_1);
        Set<String> impls = Query.findCombFuntionVertex(model, actor);
        for (final String impl : impls) {
          {
            final Predicate<Vertex> _function_1 = new Predicate<Vertex>() {
              public boolean test(final Vertex v) {
                return v.getIdentifier().equals(impl);
              }
            };
            Vertex actorimpl = model.vertexSet().stream().filter(_function_1).findAny().orElse(null);
            Set<String> inSet = Query.findImplInputPortSet(actorimpl);
            for (final String inport : inSet) {
              {
                String datatype = Query.findImplPortDataType(model, actorimpl, inport);
                String actorPort = Query.findActorPortConnectedToImplInputPort(model, actor, actorimpl, inport);
                Vertex sdf = Query.findSDFChannel(model, actor, actorPort);
                String _identifier_1 = actor.getIdentifier();
                String _plus_2 = (("sdfchannel " + "--->") + _identifier_1);
                String _plus_3 = (_plus_2 + " port ");
                String _plus_4 = (_plus_3 + actorPort);
                String _plus_5 = (_plus_4 + " ---> impl port ");
                String _plus_6 = (_plus_5 + inport);
                String _plus_7 = (_plus_6 + "--->type ");
                String _plus_8 = (_plus_7 + datatype);
                InputOutput.<String>println(_plus_8);
              }
            }
            Set<String> outSet = Query.findImplOutputPortSet(actorimpl);
            for (final String p : outSet) {
              {
                String datatype = Query.findImplPortDataType(model, actorimpl, p);
                String actorPort = Query.findActorPortConnectedToImplOutputPort(model, actor, actorimpl, p);
                Vertex sdf = Query.findSDFChannel(model, actor, actorPort);
                String _identifier_1 = actor.getIdentifier();
                String _plus_2 = (("sdfchannel " + "<---") + _identifier_1);
                String _plus_3 = (_plus_2 + " port ");
                String _plus_4 = (_plus_3 + actorPort);
                String _plus_5 = (_plus_4 + " <---- impl port ");
                String _plus_6 = (_plus_5 + p);
                String _plus_7 = (_plus_6 + "--->type ");
                String _plus_8 = (_plus_7 + datatype);
                InputOutput.<String>println(_plus_8);
              }
            }
          }
        }
      }
    }
  }
}
