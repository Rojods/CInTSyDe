package generator;

import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.core.Vertex;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@SuppressWarnings("all")
public class Generator {
  public static String root = null;
  
  public static Set<Schedule> schedules;
  
  public static ForSyDeSystemGraph model;
  
  private Set<ModuleInterface> modules = new HashSet<ModuleInterface>();
  
  public Generator(final ForSyDeSystemGraph model, final String root) {
    Generator.root = root;
    Generator.model = model;
  }
  
  public void create() {
    this.schedule();
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
  
  public Set<Schedule> schedule() {
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
      Generator.schedules = Generator.model.vertexSet().stream().filter(_function).<Schedule>map(_function_1).collect(Collectors.<Schedule>toSet());
      _xblockexpression = Generator.schedules = Generator.schedules;
    }
    return _xblockexpression;
  }
  
  public void init() {
  }
}
