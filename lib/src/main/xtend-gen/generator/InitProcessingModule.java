package generator;

import com.google.common.base.Objects;
import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import template.templateInterface.InitTemplate;
import utils.Save;

@SuppressWarnings("all")
public class InitProcessingModule implements ModuleInterface {
  private Set<InitTemplate> templateSet;
  
  public InitProcessingModule() {
    HashSet<InitTemplate> _hashSet = new HashSet<InitTemplate>();
    this.templateSet = _hashSet;
  }
  
  @Override
  public void create() {
    final Consumer<InitTemplate> _function = (InitTemplate t) -> {
      this.process(t);
    };
    this.templateSet.stream().forEach(_function);
  }
  
  public void process(final InitTemplate t) {
    FileTypeAnno anno = t.getClass().<FileTypeAnno>getAnnotation(FileTypeAnno.class);
    FileType _type = anno.type();
    boolean _equals = Objects.equal(_type, FileType.C_INCLUDE);
    if (_equals) {
      String _create = t.create();
      String _savePath = t.savePath();
      String _plus = (Generator.root + _savePath);
      Save.save(_create, _plus);
    }
    FileType _type_1 = anno.type();
    boolean _equals_1 = Objects.equal(_type_1, FileType.C_SOURCE);
    if (_equals_1) {
      String _create_1 = t.create();
      String _savePath_1 = t.savePath();
      String _plus_1 = (Generator.root + _savePath_1);
      Save.save(_create_1, _plus_1);
    }
  }
  
  public boolean add(final InitTemplate t) {
    return this.templateSet.add(t);
  }
}
