package generator;

import com.google.common.base.Objects;
import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import template.templateInterface.SubsystemTemplate;
import utils.Save;

@SuppressWarnings("all")
public class SubsystemMultiprocessorModule implements ModuleInterface {
  private Set<SubsystemTemplate> templates;
  
  public SubsystemMultiprocessorModule() {
    HashSet<SubsystemTemplate> _hashSet = new HashSet<SubsystemTemplate>();
    this.templates = _hashSet;
  }
  
  @Override
  public void create() {
    final Consumer<Schedule> _function = (Schedule schedule) -> {
      this.process(schedule);
    };
    Generator.multiProcessorSchedules.stream().forEach(_function);
  }
  
  public void process(final Schedule s) {
    final Schedule schedule = s;
    final Consumer<SubsystemTemplate> _function = (SubsystemTemplate t) -> {
      FileTypeAnno anno = t.getClass().<FileTypeAnno>getAnnotation(FileTypeAnno.class);
      FileType _type = anno.type();
      boolean _equals = Objects.equal(_type, FileType.C_INCLUDE);
      if (_equals) {
        String _identifier = schedule.tile.getIdentifier();
        String _plus = ((Generator.root + "/inc/subsystem_") + _identifier);
        String _plus_1 = (_plus + ".h");
        Save.save(_plus_1, 
          t.create(schedule));
      }
      FileType _type_1 = anno.type();
      boolean _equals_1 = Objects.equal(_type_1, FileType.C_SOURCE);
      if (_equals_1) {
        String _identifier_1 = schedule.tile.getIdentifier();
        String _plus_2 = ((Generator.root + "/src/subsystem_") + _identifier_1);
        String _plus_3 = (_plus_2 + ".c");
        Save.save(_plus_3, t.create(schedule));
      }
    };
    this.templates.stream().forEach(_function);
  }
  
  public boolean add(final SubsystemTemplate t) {
    return this.templates.add(t);
  }
}
