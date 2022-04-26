package generator;

import forsyde.io.java.core.ForSyDeSystemGraph;
import java.util.HashSet;
import java.util.Set;
import template.Template;
import utils.Global;

@SuppressWarnings("all")
public class generator {
  public static String root = null;
  
  private Set<Template> set = new HashSet<Template>();
  
  public generator(final ForSyDeSystemGraph model, final String root) {
    generator.root = root;
    Global.model = model;
  }
  
  public void create() {
    for (final Template s : this.set) {
      s.create();
    }
  }
  
  public boolean add(final Template template) {
    return this.set.add(template);
  }
}
