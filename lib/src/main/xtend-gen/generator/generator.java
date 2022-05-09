package generator;

import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.core.VertexProperty;
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel;
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@SuppressWarnings("all")
public class Generator {
  public static String root = null;
  
  public static ForSyDeSystemGraph model;
  
  public static Set<Vertex> sdfchannelSet;
  
  public static Set<Vertex> sdfcombSet;
  
  public static Set<Schedule> multiProcessorSchedules;
  
  public static TreeMap<Integer, Vertex> uniprocessorSchedule;
  
  public static int TESTING = 1;
  
  public static int PC = 1;
  
  public static int NUCLEO = 0;
  
  private Set<ModuleInterface> modules = new HashSet<ModuleInterface>();
  
  public Generator(final ForSyDeSystemGraph model, final String root) {
    Generator.root = root;
    Generator.model = model;
    final Predicate<Vertex> _function = new Predicate<Vertex>() {
      public boolean test(final Vertex v) {
        return (SDFChannel.conforms(v)).booleanValue();
      }
    };
    Generator.sdfchannelSet = Generator.model.vertexSet().stream().filter(_function).collect(
      Collectors.<Vertex>toSet());
    final Predicate<Vertex> _function_1 = new Predicate<Vertex>() {
      public boolean test(final Vertex v) {
        return (SDFComb.conforms(v)).booleanValue();
      }
    };
    Generator.sdfcombSet = Generator.model.vertexSet().stream().filter(_function_1).collect(
      Collectors.<Vertex>toSet());
    this.createMultiprocessorSchedule();
    this.createUniprocessorSchedule();
    int a = 1;
  }
  
  public void create() {
    final Consumer<ModuleInterface> _function = new Consumer<ModuleInterface>() {
      public void accept(final ModuleInterface m) {
        m.create();
      }
    };
    this.modules.stream().forEach(_function);
  }
  
  public boolean add(final ModuleInterface m) {
    return this.modules.add(m);
  }
  
  public Set<Schedule> createMultiprocessorSchedule() {
    Set<Schedule> _xblockexpression = null;
    {
      final Predicate<Vertex> _function = new Predicate<Vertex>() {
        public boolean test(final Vertex v) {
          return (v.hasTrait("platform::GenericProcessingModule")).booleanValue();
        }
      };
      final Function<Vertex, Schedule> _function_1 = new Function<Vertex, Schedule>() {
        public Schedule apply(final Vertex v) {
          return new Schedule(v);
        }
      };
      Set<Schedule> schedules = Generator.model.vertexSet().stream().filter(_function).<Schedule>map(_function_1).collect(Collectors.<Schedule>toSet());
      _xblockexpression = Generator.multiProcessorSchedules = schedules;
    }
    return _xblockexpression;
  }
  
  public void createUniprocessorSchedule() {
    TreeMap<Integer, Vertex> _treeMap = new TreeMap<Integer, Vertex>();
    Generator.uniprocessorSchedule = _treeMap;
    for (final Vertex actor : Generator.sdfcombSet) {
      Generator.uniprocessorSchedule.put(Integer.valueOf(this.getFiringSlot(actor)), actor);
    }
  }
  
  private int getFiringSlot(final Vertex actor) {
    VertexProperty firingSlots = actor.getProperties().get("firingSlots");
    if ((firingSlots != null)) {
      Object _unwrap = firingSlots.unwrap();
      ArrayList<Integer> slot = ((ArrayList<Integer>) _unwrap);
      return (slot.get(0)).intValue();
    }
    return 10;
  }
}
