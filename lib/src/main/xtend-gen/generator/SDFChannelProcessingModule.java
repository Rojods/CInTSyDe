package generator;

import com.google.common.base.Objects;
import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import template.templateInterface.ChannelTemplate;
import utils.Name;
import utils.Save;

@SuppressWarnings("all")
public class SDFChannelProcessingModule implements ModuleInterface {
  private Set<ChannelTemplate> templates;
  
  public SDFChannelProcessingModule() {
    HashSet<ChannelTemplate> _hashSet = new HashSet<ChannelTemplate>();
    this.templates = _hashSet;
  }
  
  @Override
  public void create() {
    final Predicate<Vertex> _function = (Vertex v) -> {
      return (SDFChannel.conforms(v)).booleanValue();
    };
    final Consumer<Vertex> _function_1 = (Vertex v) -> {
      this.process(v);
    };
    Generator.model.vertexSet().stream().filter(_function).forEach(_function_1);
  }
  
  public void process(final Vertex v) {
    final Consumer<ChannelTemplate> _function = (ChannelTemplate t) -> {
      FileTypeAnno anno = t.getClass().<FileTypeAnno>getAnnotation(FileTypeAnno.class);
      FileType _type = anno.type();
      boolean _equals = Objects.equal(_type, FileType.C_INCLUDE);
      if (_equals) {
        String _name = Name.name(v);
        String _plus = ((Generator.root + "/inc/sdfchannel_") + _name);
        String _plus_1 = (_plus + ".h");
        Save.save(_plus_1, t.create(v));
      }
      FileType _type_1 = anno.type();
      boolean _equals_1 = Objects.equal(_type_1, FileType.C_SOURCE);
      if (_equals_1) {
        String _name_1 = Name.name(v);
        String _plus_2 = ((Generator.root + "/src/sdfchannel_") + _name_1);
        String _plus_3 = (_plus_2 + ".c");
        Save.save(_plus_3, t.create(v));
      }
    };
    this.templates.stream().forEach(_function);
  }
  
  public void add(final ChannelTemplate t) {
    this.templates.add(t);
  }
}
