package demo;

import com.google.common.base.Objects;
import forsyde.io.java.core.EdgeTrait;
import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.core.VertexProperty;
import forsyde.io.java.core.VertexTrait;
import forsyde.io.java.drivers.ForSyDeModelHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * load fiodl file
 */
@SuppressWarnings("all")
public class test {
  public static void main(final String[] args) {
    try {
      ForSyDeSystemGraph model = new ForSyDeSystemGraph();
      final EdgeTrait t = EdgeTrait.MOC_SDF_SDFDATAEDGE;
      test.addVertex(model);
      test.addchannel(model);
      test.connectChannel(model, "p1", "s1", "a", "producer", t);
      test.connectChannel(model, "s1", "p2", "consumer", "b", t);
      new ForSyDeModelHandler().writeModel(model, "test2.fiodl");
      InputOutput.<String>println("end!");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public static boolean connectChannel(final ForSyDeSystemGraph model, final String srcname, final String dstname, final String srcport, final String dstport, final EdgeTrait t) {
    return model.connect(model.queryVertex(srcname).get(), model.queryVertex(dstname).get(), srcport, dstport, t);
  }
  
  public static void addchannel(final ForSyDeSystemGraph model) {
    List<String> list = Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("s1", "s_in"));
    for (final String name : list) {
      {
        Set<String> ports = Collections.<String>unmodifiableSet(CollectionLiterals.<String>newHashSet("producer", "consumer"));
        int maxtoken = 0;
        boolean _equals = Objects.equal(name, "s1");
        if (_equals) {
          maxtoken = 1;
        }
        boolean _equals_1 = Objects.equal(name, "s2");
        if (_equals_1) {
          maxtoken = 1;
        }
        boolean _equals_2 = Objects.equal(name, "s3");
        if (_equals_2) {
          maxtoken = 2;
        }
        boolean _equals_3 = Objects.equal(name, "s4");
        if (_equals_3) {
          maxtoken = 1;
        }
        boolean _equals_4 = Objects.equal(name, "s5");
        if (_equals_4) {
          maxtoken = 2;
        }
        boolean _equals_5 = Objects.equal(name, "s6");
        if (_equals_5) {
          maxtoken = 2;
        }
        boolean _equals_6 = Objects.equal(name, "s_in");
        if (_equals_6) {
          maxtoken = 10;
        }
        boolean _equals_7 = Objects.equal(name, "s_out");
        if (_equals_7) {
          maxtoken = 10;
        }
        VertexProperty _create = VertexProperty.create(maxtoken);
        Pair<String, VertexProperty> _mappedTo = Pair.<String, VertexProperty>of("maximumTokens", _create);
        VertexProperty _create_1 = VertexProperty.create(32);
        Pair<String, VertexProperty> _mappedTo_1 = Pair.<String, VertexProperty>of("maxSizeInBits", _create_1);
        VertexProperty _create_2 = VertexProperty.create(32);
        Pair<String, VertexProperty> _mappedTo_2 = Pair.<String, VertexProperty>of("tokenSizeInBits", _create_2);
        Map<String, VertexProperty> properties = Collections.<String, VertexProperty>unmodifiableMap(CollectionLiterals.<String, VertexProperty>newHashMap(_mappedTo, _mappedTo_1, _mappedTo_2));
        Vertex a = new Vertex(name, ports, properties);
        a.addTraits(VertexTrait.MOC_SDF_SDFCHANNEL, VertexTrait.DECISION_SDF_BOUNDEDSDFCHANNEL);
        a.addTraits(VertexTrait.IMPL_TOKENIZABLEDATABLOCK);
        model.addVertex(a);
      }
    }
  }
  
  public static boolean addVertex(final ForSyDeSystemGraph model) {
    boolean _xblockexpression = false;
    {
      Set<String> ports = Collections.<String>unmodifiableSet(CollectionLiterals.<String>newHashSet("a", "combFunctions"));
      VertexProperty _create = VertexProperty.create(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0))));
      Pair<String, VertexProperty> _mappedTo = Pair.<String, VertexProperty>of("firingSlots", _create);
      Pair<String, Integer> _mappedTo_1 = Pair.<String, Integer>of("a", Integer.valueOf(1));
      VertexProperty _create_1 = VertexProperty.create(Collections.<String, Integer>unmodifiableMap(CollectionLiterals.<String, Integer>newHashMap(_mappedTo_1)));
      Pair<String, VertexProperty> _mappedTo_2 = Pair.<String, VertexProperty>of("production", _create_1);
      Map<String, VertexProperty> properties = Collections.<String, VertexProperty>unmodifiableMap(CollectionLiterals.<String, VertexProperty>newHashMap(_mappedTo, _mappedTo_2));
      Vertex a = new Vertex("p1", ports, properties);
      a.addTraits(VertexTrait.MOC_SDF_SDFACTOR, VertexTrait.DECISION_SDF_PASSEDSDFACTOR);
      model.addVertex(a);
      ports = Collections.<String>unmodifiableSet(CollectionLiterals.<String>newHashSet("b", "combFunctions"));
      VertexProperty _create_2 = VertexProperty.create(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1))));
      Pair<String, VertexProperty> _mappedTo_3 = Pair.<String, VertexProperty>of("firingSlots", _create_2);
      Pair<String, Integer> _mappedTo_4 = Pair.<String, Integer>of("b", Integer.valueOf(1));
      VertexProperty _create_3 = VertexProperty.create(Collections.<String, Integer>unmodifiableMap(CollectionLiterals.<String, Integer>newHashMap(_mappedTo_4)));
      Pair<String, VertexProperty> _mappedTo_5 = Pair.<String, VertexProperty>of("consumption", _create_3);
      properties = Collections.<String, VertexProperty>unmodifiableMap(CollectionLiterals.<String, VertexProperty>newHashMap(_mappedTo_3, _mappedTo_5));
      Vertex _vertex = new Vertex("p2", ports, properties);
      a = _vertex;
      a.addTraits(VertexTrait.MOC_SDF_SDFACTOR, VertexTrait.DECISION_SDF_PASSEDSDFACTOR);
      model.addVertex(a);
      Vertex type = new Vertex("uint32");
      type.addTraits(VertexTrait.TYPING_DATATYPES_INTEGER);
      VertexProperty _create_4 = VertexProperty.create(32);
      Pair<String, VertexProperty> _mappedTo_6 = Pair.<String, VertexProperty>of("numberOfBits", _create_4);
      properties = Collections.<String, VertexProperty>unmodifiableMap(CollectionLiterals.<String, VertexProperty>newHashMap(_mappedTo_6));
      type.properties = properties;
      model.addVertex(type);
      List<EdgeTrait> l = new ArrayList<EdgeTrait>();
      ports = Collections.<String>unmodifiableSet(CollectionLiterals.<String>newHashSet("portTypes", "a"));
      VertexProperty _create_5 = VertexProperty.create("a=5;");
      Pair<String, VertexProperty> _mappedTo_7 = Pair.<String, VertexProperty>of("inlinedCode", _create_5);
      VertexProperty _create_6 = VertexProperty.create(Collections.<Object>unmodifiableList(CollectionLiterals.<Object>newArrayList()));
      Pair<String, VertexProperty> _mappedTo_8 = Pair.<String, VertexProperty>of("inputPorts", _create_6);
      VertexProperty _create_7 = VertexProperty.create(Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("a")));
      Pair<String, VertexProperty> _mappedTo_9 = Pair.<String, VertexProperty>of("outputPorts", _create_7);
      Map<String, VertexProperty> b = Collections.<String, VertexProperty>unmodifiableMap(CollectionLiterals.<String, VertexProperty>newHashMap(_mappedTo_7, _mappedTo_8, _mappedTo_9));
      Vertex impl = new Vertex("p1impl", ports, b);
      impl.addTraits(VertexTrait.IMPL_ANSICBLACKBOXEXECUTABLE, VertexTrait.TYPING_TYPEDOPERATION, 
        VertexTrait.IMPL_INSTRUMENTEDEXECUTABLE);
      model.addVertex(impl);
      model.connect(model.queryVertex("p1").get(), impl, "combFunctions");
      final List<EdgeTrait> _converted_l = (List<EdgeTrait>)l;
      model.connect(impl, model.queryVertex("p1").get(), "a", "a", ((EdgeTrait[])Conversions.unwrapArray(_converted_l, EdgeTrait.class)));
      model.connect(model.queryVertex("p1impl").get(), model.queryVertex("uint32").get(), "a", EdgeTrait.TYPING_DATATYPES_DATADEFINITION);
      ports = Collections.<String>unmodifiableSet(CollectionLiterals.<String>newHashSet("portTypes", "b"));
      VertexProperty _create_8 = VertexProperty.create("int c = 2*b;");
      Pair<String, VertexProperty> _mappedTo_10 = Pair.<String, VertexProperty>of("inlinedCode", _create_8);
      VertexProperty _create_9 = VertexProperty.create(Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("b")));
      Pair<String, VertexProperty> _mappedTo_11 = Pair.<String, VertexProperty>of("inputPorts", _create_9);
      VertexProperty _create_10 = VertexProperty.create(Collections.<Object>unmodifiableList(CollectionLiterals.<Object>newArrayList()));
      Pair<String, VertexProperty> _mappedTo_12 = Pair.<String, VertexProperty>of("outputPorts", _create_10);
      b = Collections.<String, VertexProperty>unmodifiableMap(CollectionLiterals.<String, VertexProperty>newHashMap(_mappedTo_10, _mappedTo_11, _mappedTo_12));
      Vertex _vertex_1 = new Vertex("p2impl", ports, b);
      impl = _vertex_1;
      impl.addTraits(VertexTrait.IMPL_ANSICBLACKBOXEXECUTABLE, VertexTrait.TYPING_TYPEDOPERATION, 
        VertexTrait.IMPL_INSTRUMENTEDEXECUTABLE);
      model.addVertex(impl);
      model.connect(model.queryVertex("p2impl").get(), model.queryVertex("uint32").get(), "b", EdgeTrait.TYPING_DATATYPES_DATADEFINITION);
      model.connect(model.queryVertex("p2").get(), impl, "combFunctions");
      final List<EdgeTrait> _converted_l_1 = (List<EdgeTrait>)l;
      _xblockexpression = model.connect(model.queryVertex("p2").get(), impl, "b", "b", ((EdgeTrait[])Conversions.unwrapArray(_converted_l_1, EdgeTrait.class)));
    }
    return _xblockexpression;
  }
}
