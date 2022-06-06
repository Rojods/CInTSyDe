package generator;

import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.core.VertexAcessor;
import forsyde.io.java.core.VertexTrait;
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.eclipse.xtext.xbase.lib.InputOutput;
import template.templateInterface.ChannelTemplate;
import utils.Query;
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
      this.save(Generator.model, v, t);
    };
    this.templates.stream().forEach(_function);
  }
  
  public void add(final ChannelTemplate t) {
    this.templates.add(t);
  }
  
  public Object save(final ForSyDeSystemGraph model, final Vertex v, final ChannelTemplate t) {
    Object _xifexpression = null;
    if ((Generator.platform != 2)) {
      String _create = t.create(v);
      String _savePath = t.savePath();
      String _plus = ((Generator.root + "/tile/") + _savePath);
      _xifexpression = Boolean.valueOf(Save.save(_create, _plus));
    } else {
      Object _xifexpression_1 = null;
      boolean _isOnOneCoreChannel = Query.isOnOneCoreChannel(model, v);
      if (_isOnOneCoreChannel) {
        Object _xblockexpression = null;
        {
          Vertex consumer = VertexAcessor.getNamedPort(model, v, "consumer", VertexTrait.MOC_SDF_SDFACTOR).orElse(null);
          Object _xifexpression_2 = null;
          if ((consumer != null)) {
            String _xblockexpression_1 = null;
            {
              Vertex tile = Query.findTile(model, consumer);
              String _create_1 = t.create(v);
              String _identifier = tile.getIdentifier();
              String _plus_1 = ((Generator.root + "/") + _identifier);
              String _savePath_1 = t.savePath();
              String _plus_2 = (_plus_1 + _savePath_1);
              Save.save(_create_1, _plus_2);
              String _identifier_1 = tile.getIdentifier();
              String _plus_3 = ((Generator.root + "/") + _identifier_1);
              String _savePath_2 = t.savePath();
              String _plus_4 = (_plus_3 + _savePath_2);
              _xblockexpression_1 = InputOutput.<String>println(_plus_4);
            }
            _xifexpression_2 = _xblockexpression_1;
          } else {
            boolean _xblockexpression_2 = false;
            {
              Vertex producer = VertexAcessor.getNamedPort(model, v, "producer", 
                VertexTrait.MOC_SDF_SDFACTOR).orElse(null);
              boolean _xifexpression_3 = false;
              if ((producer != null)) {
                boolean _xblockexpression_3 = false;
                {
                  Vertex tile2 = Query.findTile(model, producer);
                  String _create_1 = t.create(v);
                  String _identifier = tile2.getIdentifier();
                  String _plus_1 = ((Generator.root + "/") + _identifier);
                  String _savePath_1 = t.savePath();
                  String _plus_2 = (_plus_1 + _savePath_1);
                  _xblockexpression_3 = Save.save(_create_1, _plus_2);
                }
                _xifexpression_3 = _xblockexpression_3;
              }
              _xblockexpression_2 = _xifexpression_3;
            }
            _xifexpression_2 = Boolean.valueOf(_xblockexpression_2);
          }
          _xblockexpression = ((Object)_xifexpression_2);
        }
        _xifexpression_1 = ((Object)_xblockexpression);
      } else {
        boolean _xblockexpression_1 = false;
        {
          Vertex consumer = VertexAcessor.getNamedPort(model, v, "consumer", VertexTrait.MOC_SDF_SDFACTOR).orElse(null);
          if ((consumer != null)) {
            Vertex tile = Query.findTile(Generator.model, consumer);
            String _create_1 = t.create(v);
            String _identifier = tile.getIdentifier();
            String _plus_1 = ((Generator.root + "/") + _identifier);
            String _savePath_1 = t.savePath();
            String _plus_2 = (_plus_1 + _savePath_1);
            Save.save(_create_1, _plus_2);
          }
          Vertex producer = VertexAcessor.getNamedPort(model, v, "producer", VertexTrait.MOC_SDF_SDFACTOR).orElse(null);
          boolean _xifexpression_2 = false;
          if ((producer != null)) {
            boolean _xblockexpression_2 = false;
            {
              Vertex tile2 = Query.findTile(model, producer);
              String _create_2 = t.create(v);
              String _identifier_1 = tile2.getIdentifier();
              String _plus_3 = ((Generator.root + "/") + _identifier_1);
              String _savePath_2 = t.savePath();
              String _plus_4 = (_plus_3 + _savePath_2);
              _xblockexpression_2 = Save.save(_create_2, _plus_4);
            }
            _xifexpression_2 = _xblockexpression_2;
          }
          _xblockexpression_1 = _xifexpression_2;
        }
        _xifexpression_1 = Boolean.valueOf(_xblockexpression_1);
      }
      _xifexpression = ((Object)_xifexpression_1);
    }
    return _xifexpression;
  }
}
