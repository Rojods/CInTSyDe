package generator;

import com.google.common.base.Objects;
import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import org.eclipse.xtext.xbase.lib.InputOutput;
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
      InputOutput.<String>println("save ");
      String _fileName = t.getFileName();
      String _plus = ((Generator.root + "/inc/") + _fileName);
      String _plus_1 = (_plus + ".h");
      Save.save(_plus_1, t.create());
    }
    FileType _type_1 = anno.type();
    boolean _equals_1 = Objects.equal(_type_1, FileType.C_SOURCE);
    if (_equals_1) {
      String _fileName_1 = t.getFileName();
      String _plus_2 = ((Generator.root + "/src/") + _fileName_1);
      String _plus_3 = (_plus_2 + ".c");
      Save.save(_plus_3, t.create());
    }
  }
  
  public boolean add(final InitTemplate t) {
    return this.templateSet.add(t);
  }
}
