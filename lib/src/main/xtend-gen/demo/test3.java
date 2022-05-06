package demo;

import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.core.VertexProperty;
import forsyde.io.java.core.VertexTrait;
import forsyde.io.java.drivers.ForSyDeModelHandler;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.Pair;

@SuppressWarnings("all")
public class test3 {
  public static void main(final String[] args) {
    try {
      final String path = "forsyde-io\\modified2\\complete-mapped-sobel-model.forsyde.xmi";
      final String path2 = "forsyde-io\\modified2\\sobel-application.fiodl";
      ForSyDeSystemGraph model = new ForSyDeSystemGraph();
      final Set<String> ports = Collections.<String>unmodifiableSet(CollectionLiterals.<String>newHashSet("a", "combFunctions"));
      VertexProperty _create = VertexProperty.create(0);
      Pair<String, VertexProperty> _mappedTo = Pair.<String, VertexProperty>of("firingSlots", _create);
      Pair<String, Integer> _mappedTo_1 = Pair.<String, Integer>of("a", Integer.valueOf(1));
      VertexProperty _create_1 = VertexProperty.create(Collections.<String, Integer>unmodifiableMap(CollectionLiterals.<String, Integer>newHashMap(_mappedTo_1)));
      Pair<String, VertexProperty> _mappedTo_2 = Pair.<String, VertexProperty>of("production", _create_1);
      final Map<String, VertexProperty> properties = Collections.<String, VertexProperty>unmodifiableMap(CollectionLiterals.<String, VertexProperty>newHashMap(_mappedTo, _mappedTo_2));
      Vertex a = new Vertex("A", ports, properties);
      a.addTraits(VertexTrait.MOC_SDF_SDFCOMB, VertexTrait.DECISION_SDF_PASSEDSDFCOMB);
      model.addVertex(a);
      final Set<String> ports2 = Collections.<String>unmodifiableSet(CollectionLiterals.<String>newHashSet("b", "combFunctions"));
      VertexProperty _create_2 = VertexProperty.create(1);
      Pair<String, VertexProperty> _mappedTo_3 = Pair.<String, VertexProperty>of("firingSlots", _create_2);
      Pair<String, Integer> _mappedTo_4 = Pair.<String, Integer>of("b", Integer.valueOf(1));
      VertexProperty _create_3 = VertexProperty.create(Collections.<String, Integer>unmodifiableMap(CollectionLiterals.<String, Integer>newHashMap(_mappedTo_4)));
      Pair<String, VertexProperty> _mappedTo_5 = Pair.<String, VertexProperty>of("consumption", _create_3);
      final Map<String, VertexProperty> properties2 = Collections.<String, VertexProperty>unmodifiableMap(CollectionLiterals.<String, VertexProperty>newHashMap(_mappedTo_3, _mappedTo_5));
      Vertex a2 = new Vertex("B", ports, properties);
      a2.addTraits(VertexTrait.MOC_SDF_SDFCOMB, VertexTrait.DECISION_SDF_PASSEDSDFCOMB);
      model.addVertex(a2);
      final Set<String> ports3 = Collections.<String>unmodifiableSet(CollectionLiterals.<String>newHashSet("producer", "consumer"));
      VertexProperty _create_4 = VertexProperty.create(1);
      Pair<String, VertexProperty> _mappedTo_6 = Pair.<String, VertexProperty>of("maximumTokens", _create_4);
      final Map<String, VertexProperty> properties3 = Collections.<String, VertexProperty>unmodifiableMap(CollectionLiterals.<String, VertexProperty>newHashMap(_mappedTo_6));
      Vertex a3 = new Vertex("sig", ports3, properties3);
      a3.addTraits(VertexTrait.MOC_SDF_SDFCHANNEL, VertexTrait.DECISION_SDF_BOUNDEDSDFCHANNEL);
      a3.addTraits(VertexTrait.IMPL_TOKENIZABLEDATABLOCK);
      model.addVertex(a3);
      new ForSyDeModelHandler().writeModel(model, "a.fiodl");
      InputOutput.<String>println("end!");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
