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
        String _create = t.create(schedule);
        String _savePath = t.savePath();
        String _plus = (Generator.root + _savePath);
        Save.save(_create, _plus);
      }
      FileType _type_1 = anno.type();
      boolean _equals_1 = Objects.equal(_type_1, FileType.C_SOURCE);
      if (_equals_1) {
        String _create_1 = t.create(schedule);
        String _savePath_1 = t.savePath();
        String _plus_1 = (Generator.root + _savePath_1);
        Save.save(_create_1, _plus_1);
      }
    };
    this.templates.stream().forEach(_function);
  }
  
  public boolean add(final SubsystemTemplate t) {
    return this.templates.add(t);
  }
}
