package generator;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Consumer;

@SuppressWarnings("all")
public class Generator {
  public static String root = null;
  
  public static /* ForSyDeSystemGraph */Object model;
  
  public static /* Set<Vertex> */Object sdfchannelSet;
  
  public static /* Set<Vertex> */Object sdfcombSet;
  
  public static Set<Schedule> multiProcessorSchedules;
  
  public static /* TreeMap<Integer, Vertex> */Object uniprocessorSchedule;
  
  public static int TESTING = 1;
  
  public static int PC = 1;
  
  public static int NUCLEO = 0;
  
  private Set<ModuleInterface> modules = new HashSet<ModuleInterface>();
  
  public Generator(final /* ForSyDeSystemGraph */Object model, final String root) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field SDFChannel is undefined"
      + "\nThe method or field SDFComb is undefined"
      + "\nThe field Generator.model refers to the missing type ForSyDeSystemGraph"
      + "\nThe field Generator.sdfchannelSet refers to the missing type Vertex"
      + "\nThe field Generator.model refers to the missing type ForSyDeSystemGraph"
      + "\nThe field Generator.sdfcombSet refers to the missing type Vertex"
      + "\nThe field Generator.model refers to the missing type ForSyDeSystemGraph"
      + "\nvertexSet cannot be resolved"
      + "\nstream cannot be resolved"
      + "\nfilter cannot be resolved"
      + "\nconforms cannot be resolved"
      + "\ncollect cannot be resolved"
      + "\nvertexSet cannot be resolved"
      + "\nstream cannot be resolved"
      + "\nfilter cannot be resolved"
      + "\nconforms cannot be resolved"
      + "\ncollect cannot be resolved");
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
    throw new Error("Unresolved compilation problems:"
      + "\nThe field Generator.model refers to the missing type ForSyDeSystemGraph"
      + "\nThe constructor Schedule(Vertex) refers to the missing type Vertex"
      + "\nvertexSet cannot be resolved"
      + "\nstream cannot be resolved"
      + "\nfilter cannot be resolved"
      + "\nhasTrait cannot be resolved"
      + "\nmap cannot be resolved"
      + "\ncollect cannot be resolved");
  }
  
  public void createUniprocessorSchedule() {
    throw new Error("Unresolved compilation problems:"
      + "\nVertex cannot be resolved to a type."
      + "\nThe field Generator.uniprocessorSchedule refers to the missing type Vertex"
      + "\nThe field Generator.sdfcombSet refers to the missing type Vertex"
      + "\nThe field Generator.uniprocessorSchedule refers to the missing type Vertex"
      + "\nThe method getFiringSlot(Vertex) from the type Generator refers to the missing type Vertex");
  }
  
  private int getFiringSlot(final /* Vertex */Object actor) {
    throw new Error("Unresolved compilation problems:"
      + "\ngetProperties cannot be resolved"
      + "\nget cannot be resolved"
      + "\n!== cannot be resolved"
      + "\nunwrap cannot be resolved");
  }
}
