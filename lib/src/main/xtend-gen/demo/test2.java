package demo;

import forsyde.io.java.core.EdgeTrait;
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

/**
 * create fiodl file
 */
@SuppressWarnings("all")
public class test2 {
  public static void main(final String[] args) {
    try {
      ForSyDeSystemGraph model = new ForSyDeSystemGraph();
      final Set<String> ports = Collections.<String>unmodifiableSet(CollectionLiterals.<String>newHashSet("aa", "combFunctions"));
      VertexProperty _create = VertexProperty.create(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0))));
      Pair<String, VertexProperty> _mappedTo = Pair.<String, VertexProperty>of("firingSlots", _create);
      Pair<String, Integer> _mappedTo_1 = Pair.<String, Integer>of("aa", Integer.valueOf(1));
      VertexProperty _create_1 = VertexProperty.create(Collections.<String, Integer>unmodifiableMap(CollectionLiterals.<String, Integer>newHashMap(_mappedTo_1)));
      Pair<String, VertexProperty> _mappedTo_2 = Pair.<String, VertexProperty>of("production", _create_1);
      final Map<String, VertexProperty> properties = Collections.<String, VertexProperty>unmodifiableMap(CollectionLiterals.<String, VertexProperty>newHashMap(_mappedTo, _mappedTo_2));
      Vertex a = new Vertex("vertex_a", ports, properties);
      a.addTraits(VertexTrait.MOC_SDF_SDFCOMB, VertexTrait.DECISION_SDF_PASSEDSDFCOMB);
      model.addVertex(a);
      final Set<String> ports2 = Collections.<String>unmodifiableSet(CollectionLiterals.<String>newHashSet("bb", "combFunctions"));
      VertexProperty _create_2 = VertexProperty.create(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1))));
      Pair<String, VertexProperty> _mappedTo_3 = Pair.<String, VertexProperty>of("firingSlots", _create_2);
      Pair<String, Integer> _mappedTo_4 = Pair.<String, Integer>of("bb", Integer.valueOf(1));
      VertexProperty _create_3 = VertexProperty.create(Collections.<String, Integer>unmodifiableMap(CollectionLiterals.<String, Integer>newHashMap(_mappedTo_4)));
      Pair<String, VertexProperty> _mappedTo_5 = Pair.<String, VertexProperty>of("consumption", _create_3);
      final Map<String, VertexProperty> properties2 = Collections.<String, VertexProperty>unmodifiableMap(CollectionLiterals.<String, VertexProperty>newHashMap(_mappedTo_3, _mappedTo_5));
      Vertex a2 = new Vertex("vertex_b", ports2, properties2);
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
      Vertex type = new Vertex("DoubleType");
      type.addTraits(VertexTrait.TYPING_DATATYPES_DOUBLE);
      model.addVertex(type);
      final Set<String> impl_port1 = Collections.<String>unmodifiableSet(CollectionLiterals.<String>newHashSet("portTypes", "aa"));
      VertexProperty _create_5 = VertexProperty.create("aa=100;");
      Pair<String, VertexProperty> _mappedTo_7 = Pair.<String, VertexProperty>of("inlinedCode", _create_5);
      VertexProperty _create_6 = VertexProperty.create(Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("aa")));
      Pair<String, VertexProperty> _mappedTo_8 = Pair.<String, VertexProperty>of("outputPorts", _create_6);
      final Map<String, VertexProperty> impl_pro1 = Collections.<String, VertexProperty>unmodifiableMap(CollectionLiterals.<String, VertexProperty>newHashMap(_mappedTo_7, _mappedTo_8));
      Vertex impl = new Vertex("vertex_a_impl", impl_port1, impl_pro1);
      impl.addTraits(VertexTrait.IMPL_ANSICBLACKBOXEXECUTABLE, VertexTrait.TYPING_TYPEDOPERATION, 
        VertexTrait.IMPL_INSTRUMENTEDEXECUTABLE);
      model.addVertex(impl);
      model.connect(a, a3, "aa", "producer", EdgeTrait.MOC_SDF_SDFDATAEDGE);
      model.connect(a3, a2, "consumer", "bb", EdgeTrait.MOC_SDF_SDFDATAEDGE);
      model.connect(impl, type, "aa", EdgeTrait.TYPING_DATATYPES_DATADEFINITION);
      model.connect(impl, a, "aa", "aa");
      model.connect(a, impl, "combFunctions");
      new ForSyDeModelHandler().writeModel(model, "a.forsyde.xmi");
      InputOutput.<ForSyDeSystemGraph>println(model);
      InputOutput.<String>println("end!");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
