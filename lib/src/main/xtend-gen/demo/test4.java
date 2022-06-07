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
import org.eclipse.xtext.xbase.lib.Pair;

@SuppressWarnings("all")
public class test4 {
  public static void main(final String[] args) {
    try {
      ForSyDeSystemGraph model = new ForSyDeSystemGraph();
      final EdgeTrait t = EdgeTrait.MOC_SDF_SDFDATAEDGE;
      test4.addVertex(model);
      test4.addchannel(model);
      new ForSyDeModelHandler().writeModel(model, "example2.fiodl");
      new ForSyDeModelHandler().writeModel(model, "example2.forsyde.xmi");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public static boolean connectChannel(final ForSyDeSystemGraph model, final String srcname, final String dstname, final String srcport, final String dstport, final EdgeTrait t) {
    return model.connect(model.queryVertex(srcname).get(), model.queryVertex(dstname).get(), srcport, dstport, t);
  }
  
  public static void addchannel(final ForSyDeSystemGraph model) {
    List<String> list = Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("s_in", "s_out", "s1", "s2", "s3", "s4", "s5", "s6"));
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
        Map<String, VertexProperty> properties = Collections.<String, VertexProperty>unmodifiableMap(CollectionLiterals.<String, VertexProperty>newHashMap(_mappedTo));
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
      Set<String> ports = Collections.<String>unmodifiableSet(CollectionLiterals.<String>newHashSet("s_in", "s6", "s1", "combFunctions"));
      VertexProperty _create = VertexProperty.create(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(4))));
      Pair<String, VertexProperty> _mappedTo = Pair.<String, VertexProperty>of("firingSlots", _create);
      Pair<String, Integer> _mappedTo_1 = Pair.<String, Integer>of("s1", Integer.valueOf(1));
      VertexProperty _create_1 = VertexProperty.create(Collections.<String, Integer>unmodifiableMap(CollectionLiterals.<String, Integer>newHashMap(_mappedTo_1)));
      Pair<String, VertexProperty> _mappedTo_2 = Pair.<String, VertexProperty>of("production", _create_1);
      Pair<String, Integer> _mappedTo_3 = Pair.<String, Integer>of("s_in", Integer.valueOf(2));
      Pair<String, Integer> _mappedTo_4 = Pair.<String, Integer>of("s6", Integer.valueOf(1));
      VertexProperty _create_2 = VertexProperty.create(Collections.<String, Integer>unmodifiableMap(CollectionLiterals.<String, Integer>newHashMap(_mappedTo_3, _mappedTo_4)));
      Pair<String, VertexProperty> _mappedTo_5 = Pair.<String, VertexProperty>of("consumption", _create_2);
      Map<String, VertexProperty> properties = Collections.<String, VertexProperty>unmodifiableMap(CollectionLiterals.<String, VertexProperty>newHashMap(_mappedTo, _mappedTo_2, _mappedTo_5));
      Vertex a = new Vertex("p1", ports, properties);
      a.addTraits(VertexTrait.MOC_SDF_SDFACTOR, VertexTrait.DECISION_SDF_PASSEDSDFACTOR);
      model.addVertex(a);
      ports = Collections.<String>unmodifiableSet(CollectionLiterals.<String>newHashSet("s1", "s2", "s3", "combFunctions"));
      VertexProperty _create_3 = VertexProperty.create(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf(5))));
      Pair<String, VertexProperty> _mappedTo_6 = Pair.<String, VertexProperty>of("firingSlots", _create_3);
      Pair<String, Integer> _mappedTo_7 = Pair.<String, Integer>of("s2", Integer.valueOf(1));
      Pair<String, Integer> _mappedTo_8 = Pair.<String, Integer>of("s3", Integer.valueOf(1));
      VertexProperty _create_4 = VertexProperty.create(Collections.<String, Integer>unmodifiableMap(CollectionLiterals.<String, Integer>newHashMap(_mappedTo_7, _mappedTo_8)));
      Pair<String, VertexProperty> _mappedTo_9 = Pair.<String, VertexProperty>of("production", _create_4);
      Pair<String, Integer> _mappedTo_10 = Pair.<String, Integer>of("s1", Integer.valueOf(1));
      VertexProperty _create_5 = VertexProperty.create(Collections.<String, Integer>unmodifiableMap(CollectionLiterals.<String, Integer>newHashMap(_mappedTo_10)));
      Pair<String, VertexProperty> _mappedTo_11 = Pair.<String, VertexProperty>of("consumption", _create_5);
      properties = Collections.<String, VertexProperty>unmodifiableMap(CollectionLiterals.<String, VertexProperty>newHashMap(_mappedTo_6, _mappedTo_9, _mappedTo_11));
      Vertex _vertex = new Vertex("p2", ports, properties);
      a = _vertex;
      a.addTraits(VertexTrait.MOC_SDF_SDFACTOR, VertexTrait.DECISION_SDF_PASSEDSDFACTOR);
      model.addVertex(a);
      ports = Collections.<String>unmodifiableSet(CollectionLiterals.<String>newHashSet("s5", "s6", "s3", "combFunctions"));
      VertexProperty _create_6 = VertexProperty.create(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(8))));
      Pair<String, VertexProperty> _mappedTo_12 = Pair.<String, VertexProperty>of("firingSlots", _create_6);
      Pair<String, Integer> _mappedTo_13 = Pair.<String, Integer>of("s6", Integer.valueOf(2));
      VertexProperty _create_7 = VertexProperty.create(Collections.<String, Integer>unmodifiableMap(CollectionLiterals.<String, Integer>newHashMap(_mappedTo_13)));
      Pair<String, VertexProperty> _mappedTo_14 = Pair.<String, VertexProperty>of("production", _create_7);
      Pair<String, Integer> _mappedTo_15 = Pair.<String, Integer>of("s3", Integer.valueOf(2));
      Pair<String, Integer> _mappedTo_16 = Pair.<String, Integer>of("s5", Integer.valueOf(2));
      VertexProperty _create_8 = VertexProperty.create(Collections.<String, Integer>unmodifiableMap(CollectionLiterals.<String, Integer>newHashMap(_mappedTo_15, _mappedTo_16)));
      Pair<String, VertexProperty> _mappedTo_17 = Pair.<String, VertexProperty>of("consumption", _create_8);
      properties = Collections.<String, VertexProperty>unmodifiableMap(CollectionLiterals.<String, VertexProperty>newHashMap(_mappedTo_12, _mappedTo_14, _mappedTo_17));
      Vertex _vertex_1 = new Vertex("p3", ports, properties);
      a = _vertex_1;
      a.addTraits(VertexTrait.MOC_SDF_SDFACTOR, VertexTrait.DECISION_SDF_PASSEDSDFACTOR);
      model.addVertex(a);
      ports = Collections.<String>unmodifiableSet(CollectionLiterals.<String>newHashSet("s2", "s4", "s_out", "combFunctions"));
      VertexProperty _create_9 = VertexProperty.create(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(2), Integer.valueOf(6))));
      Pair<String, VertexProperty> _mappedTo_18 = Pair.<String, VertexProperty>of("firingSlots", _create_9);
      Pair<String, Integer> _mappedTo_19 = Pair.<String, Integer>of("s2", Integer.valueOf(1));
      VertexProperty _create_10 = VertexProperty.create(Collections.<String, Integer>unmodifiableMap(CollectionLiterals.<String, Integer>newHashMap(_mappedTo_19)));
      Pair<String, VertexProperty> _mappedTo_20 = Pair.<String, VertexProperty>of("consumption", _create_10);
      Pair<String, Integer> _mappedTo_21 = Pair.<String, Integer>of("s4", Integer.valueOf(1));
      Pair<String, Integer> _mappedTo_22 = Pair.<String, Integer>of("s_out", Integer.valueOf(3));
      VertexProperty _create_11 = VertexProperty.create(Collections.<String, Integer>unmodifiableMap(CollectionLiterals.<String, Integer>newHashMap(_mappedTo_21, _mappedTo_22)));
      Pair<String, VertexProperty> _mappedTo_23 = Pair.<String, VertexProperty>of("production", _create_11);
      properties = Collections.<String, VertexProperty>unmodifiableMap(CollectionLiterals.<String, VertexProperty>newHashMap(_mappedTo_18, _mappedTo_20, _mappedTo_23));
      Vertex _vertex_2 = new Vertex("p4", ports, properties);
      a = _vertex_2;
      a.addTraits(VertexTrait.MOC_SDF_SDFACTOR, VertexTrait.DECISION_SDF_PASSEDSDFACTOR);
      model.addVertex(a);
      ports = Collections.<String>unmodifiableSet(CollectionLiterals.<String>newHashSet("s5", "s4", "combFunctions"));
      VertexProperty _create_12 = VertexProperty.create(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(3), Integer.valueOf(7))));
      Pair<String, VertexProperty> _mappedTo_24 = Pair.<String, VertexProperty>of("firingSlots", _create_12);
      Pair<String, Integer> _mappedTo_25 = Pair.<String, Integer>of("s5", Integer.valueOf(1));
      VertexProperty _create_13 = VertexProperty.create(Collections.<String, Integer>unmodifiableMap(CollectionLiterals.<String, Integer>newHashMap(_mappedTo_25)));
      Pair<String, VertexProperty> _mappedTo_26 = Pair.<String, VertexProperty>of("production", _create_13);
      Pair<String, Integer> _mappedTo_27 = Pair.<String, Integer>of("s4", Integer.valueOf(1));
      VertexProperty _create_14 = VertexProperty.create(Collections.<String, Integer>unmodifiableMap(CollectionLiterals.<String, Integer>newHashMap(_mappedTo_27)));
      Pair<String, VertexProperty> _mappedTo_28 = Pair.<String, VertexProperty>of("consumption", _create_14);
      properties = Collections.<String, VertexProperty>unmodifiableMap(CollectionLiterals.<String, VertexProperty>newHashMap(_mappedTo_24, _mappedTo_26, _mappedTo_28));
      Vertex _vertex_3 = new Vertex("p5", ports, properties);
      a = _vertex_3;
      a.addTraits(VertexTrait.MOC_SDF_SDFACTOR, VertexTrait.DECISION_SDF_PASSEDSDFACTOR);
      model.addVertex(a);
      Vertex type = new Vertex("uint32");
      type.addTraits(VertexTrait.TYPING_DATATYPES_INTEGER);
      VertexProperty _create_15 = VertexProperty.create(32);
      Pair<String, VertexProperty> _mappedTo_29 = Pair.<String, VertexProperty>of("numberOfBits", _create_15);
      properties = Collections.<String, VertexProperty>unmodifiableMap(CollectionLiterals.<String, VertexProperty>newHashMap(_mappedTo_29));
      type.properties = properties;
      model.addVertex(type);
      ports = Collections.<String>unmodifiableSet(CollectionLiterals.<String>newHashSet("portTypes", "s_in", "s6", "s1"));
      VertexProperty _create_16 = VertexProperty.create("s1=s6;");
      Pair<String, VertexProperty> _mappedTo_30 = Pair.<String, VertexProperty>of("inlinedCode", _create_16);
      VertexProperty _create_17 = VertexProperty.create(Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("s_in", "s6")));
      Pair<String, VertexProperty> _mappedTo_31 = Pair.<String, VertexProperty>of("inputPorts", _create_17);
      VertexProperty _create_18 = VertexProperty.create(Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("s1")));
      Pair<String, VertexProperty> _mappedTo_32 = Pair.<String, VertexProperty>of("outputPorts", _create_18);
      Map<String, VertexProperty> b = Collections.<String, VertexProperty>unmodifiableMap(CollectionLiterals.<String, VertexProperty>newHashMap(_mappedTo_30, _mappedTo_31, _mappedTo_32));
      Vertex impl = new Vertex("p1impl", ports, b);
      impl.addTraits(VertexTrait.IMPL_ANSICBLACKBOXEXECUTABLE, VertexTrait.TYPING_TYPEDOPERATION, 
        VertexTrait.IMPL_INSTRUMENTEDEXECUTABLE);
      model.addVertex(impl);
      model.connect(model.queryVertex("p1").get(), impl, "combFunctions");
      List<EdgeTrait> l = new ArrayList<EdgeTrait>();
      final List<EdgeTrait> _converted_l = (List<EdgeTrait>)l;
      model.connect(model.queryVertex("p1").get(), impl, "s_in", "s_in", ((EdgeTrait[])Conversions.unwrapArray(_converted_l, EdgeTrait.class)));
      final List<EdgeTrait> _converted_l_1 = (List<EdgeTrait>)l;
      model.connect(model.queryVertex("p1").get(), impl, "s6", "s6", ((EdgeTrait[])Conversions.unwrapArray(_converted_l_1, EdgeTrait.class)));
      final List<EdgeTrait> _converted_l_2 = (List<EdgeTrait>)l;
      model.connect(impl, model.queryVertex("p1").get(), "s1", "s1", ((EdgeTrait[])Conversions.unwrapArray(_converted_l_2, EdgeTrait.class)));
      model.connect(model.queryVertex("p1impl").get(), impl, "s_in", EdgeTrait.TYPING_DATATYPES_DATADEFINITION);
      model.connect(model.queryVertex("p1impl").get(), impl, "s1", EdgeTrait.TYPING_DATATYPES_DATADEFINITION);
      _xblockexpression = model.connect(model.queryVertex("p1impl").get(), impl, "s6", EdgeTrait.TYPING_DATATYPES_DATADEFINITION);
    }
    return _xblockexpression;
  }
}
