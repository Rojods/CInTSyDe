package template.baremetal;

import com.google.common.base.Objects;
import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.EdgeInfo;
import forsyde.io.java.core.EdgeTrait;
import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.core.VertexProperty;
import forsyde.io.java.core.VertexTrait;
import generator.Generator;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.templateInterface.InitTemplate;
import utils.Query;

@FileTypeAnno(type = FileType.C_INCLUDE)
@SuppressWarnings("all")
public class DataTypeTemplateInc implements InitTemplate {
  private Set<String> record = new HashSet<String>();
  
  public DataTypeTemplateInc() {
  }
  
  public String getFileName() {
    return "datatype_definition";
  }
  
  public String create() {
    String _xblockexpression = null;
    {
      ForSyDeSystemGraph model = Generator.model;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("#ifndef DATATYPE_DEFINITION_");
      _builder.newLine();
      _builder.append("#define DATATYPE_DEFINITION_");
      _builder.newLine();
      _builder.append("#include <stdio.h>");
      _builder.newLine();
      _builder.append("/*");
      _builder.newLine();
      _builder.append("==============================================================");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("TYPING_DATATYPES_DOUBLE");
      _builder.newLine();
      _builder.append("==============================================================");
      _builder.newLine();
      _builder.append("*/");
      _builder.newLine();
      String _doubleTypeDef = this.doubleTypeDef();
      _builder.append(_doubleTypeDef);
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("/*");
      _builder.newLine();
      _builder.append("==============================================================");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("TYPING_DATATYPES_FLOAT");
      _builder.newLine();
      _builder.append("==============================================================");
      _builder.newLine();
      _builder.append("*/");
      _builder.newLine();
      String _floatTypeDef = this.floatTypeDef();
      _builder.append(_floatTypeDef);
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("/*");
      _builder.newLine();
      _builder.append("==============================================================");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("TYPING_DATATYPES_INTEGER");
      _builder.newLine();
      _builder.append("==============================================================");
      _builder.newLine();
      _builder.append("*/");
      _builder.newLine();
      String _intTypeDef = this.intTypeDef();
      _builder.append(_intTypeDef);
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("/*");
      _builder.newLine();
      _builder.append("==============================================================");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("TYPING_DATATYPES_ARRAY");
      _builder.newLine();
      _builder.append("==============================================================");
      _builder.newLine();
      _builder.append("*/");
      _builder.newLine();
      String _arrayTypeDef = this.arrayTypeDef();
      _builder.append(_arrayTypeDef);
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.newLine();
      _builder.newLine();
      _builder.append("#endif");
      _builder.newLine();
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
  
  public String doubleTypeDef() {
    StringConcatenation _builder = new StringConcatenation();
    final Predicate<Vertex> _function = new Predicate<Vertex>() {
      public boolean test(final Vertex v) {
        return (v.hasTrait(VertexTrait.TYPING_DATATYPES_DOUBLE)).booleanValue();
      }
    };
    Set<Vertex> doubleVertexSet = Generator.model.vertexSet().stream().filter(_function).collect(Collectors.<Vertex>toSet());
    _builder.newLineIfNotEmpty();
    {
      boolean _hasElements = false;
      for(final Vertex doubleVertex : doubleVertexSet) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate("", "");
        }
        _builder.append("typedef double ");
        String _identifier = doubleVertex.getIdentifier();
        _builder.append(_identifier);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        boolean tmp = this.record.add(doubleVertex.getIdentifier());
        _builder.newLineIfNotEmpty();
      }
      if (_hasElements) {
        _builder.append("");
      }
    }
    return _builder.toString();
  }
  
  public String floatTypeDef() {
    StringConcatenation _builder = new StringConcatenation();
    final Predicate<Vertex> _function = new Predicate<Vertex>() {
      public boolean test(final Vertex v) {
        return (v.hasTrait(VertexTrait.TYPING_DATATYPES_FLOAT)).booleanValue();
      }
    };
    Set<Vertex> floatVertexSet = Generator.model.vertexSet().stream().filter(_function).collect(Collectors.<Vertex>toSet());
    _builder.newLineIfNotEmpty();
    {
      boolean _hasElements = false;
      for(final Vertex floatVertex : floatVertexSet) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate("", "");
        }
        _builder.append("typedef float ");
        String _identifier = floatVertex.getIdentifier();
        _builder.append(_identifier);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        boolean tmp = this.record.add(floatVertex.getIdentifier());
        _builder.newLineIfNotEmpty();
      }
      if (_hasElements) {
        _builder.append("");
      }
    }
    return _builder.toString();
  }
  
  public String intTypeDef() {
    StringConcatenation _builder = new StringConcatenation();
    final Predicate<Vertex> _function = new Predicate<Vertex>() {
      public boolean test(final Vertex v) {
        return (v.hasTrait(VertexTrait.TYPING_DATATYPES_INTEGER)).booleanValue();
      }
    };
    Set<Vertex> intVertexSet = Generator.model.vertexSet().stream().filter(_function).collect(Collectors.<Vertex>toSet());
    _builder.newLineIfNotEmpty();
    {
      boolean _hasElements = false;
      for(final Vertex intVertex : intVertexSet) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate("", "");
        }
        {
          Object _unwrap = intVertex.getProperties().get("numberOfBits").unwrap();
          boolean _equals = ((((Integer) _unwrap)).intValue() == 8);
          if (_equals) {
            _builder.append("typedef char ");
            String _identifier = intVertex.getIdentifier();
            _builder.append(_identifier);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
        {
          Object _unwrap_1 = intVertex.getProperties().get("numberOfBits").unwrap();
          boolean _equals_1 = ((((Integer) _unwrap_1)).intValue() == 16);
          if (_equals_1) {
            _builder.append("typedef unsigned short ");
            String _identifier_1 = intVertex.getIdentifier();
            _builder.append(_identifier_1);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
        {
          Object _unwrap_2 = intVertex.getProperties().get("numberOfBits").unwrap();
          boolean _equals_2 = ((((Integer) _unwrap_2)).intValue() == 32);
          if (_equals_2) {
            _builder.append("typedef unsigned int ");
            String _identifier_2 = intVertex.getIdentifier();
            _builder.append(_identifier_2);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
        {
          Object _unwrap_3 = intVertex.getProperties().get("numberOfBits").unwrap();
          boolean _equals_3 = ((((Integer) _unwrap_3)).intValue() == 64);
          if (_equals_3) {
            _builder.append("typedef unsigned long ");
            String _identifier_3 = intVertex.getIdentifier();
            _builder.append(_identifier_3);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
        boolean tmp = this.record.add(intVertex.getIdentifier());
        _builder.append("\t\t");
        _builder.newLineIfNotEmpty();
      }
      if (_hasElements) {
        _builder.append("");
      }
    }
    return _builder.toString();
  }
  
  public String arrayTypeDef() {
    String _xblockexpression = null;
    {
      final Predicate<Vertex> _function = new Predicate<Vertex>() {
        public boolean test(final Vertex v) {
          return (v.hasTrait(VertexTrait.TYPING_DATATYPES_ARRAY)).booleanValue();
        }
      };
      Set<Vertex> arrayVertexSet = Generator.model.vertexSet().stream().filter(_function).collect(Collectors.<Vertex>toSet());
      StringConcatenation _builder = new StringConcatenation();
      {
        boolean _hasElements = false;
        for(final Vertex arrayVertex : arrayVertexSet) {
          if (!_hasElements) {
            _hasElements = true;
          } else {
            _builder.appendImmediate("", "");
          }
          String _help1 = this.help1(arrayVertex);
          _builder.append(_help1);
          _builder.newLineIfNotEmpty();
        }
        if (_hasElements) {
          _builder.append("");
        }
      }
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
  
  private String help1(final Vertex arrayVertex) {
    StringConcatenation _builder = new StringConcatenation();
    String innerType = this.getInnerType(arrayVertex);
    _builder.newLineIfNotEmpty();
    {
      if ((this.record.contains(innerType) && (!this.record.contains(arrayVertex.getIdentifier())))) {
        int maximumElems = this.getMaximumElems(arrayVertex);
        _builder.newLineIfNotEmpty();
        {
          if ((maximumElems > 0)) {
            _builder.append("typedef ");
            _builder.append(innerType);
            _builder.append(" ");
            String _identifier = arrayVertex.getIdentifier();
            _builder.append(_identifier);
            _builder.append("[");
            _builder.append(maximumElems);
            _builder.append("];");
            _builder.newLineIfNotEmpty();
          }
        }
        {
          if ((maximumElems < 0)) {
            _builder.append("typedef ");
            _builder.append(innerType);
            _builder.append(" *");
            String _identifier_1 = arrayVertex.getIdentifier();
            _builder.append(_identifier_1);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
        boolean tmp = this.record.add(arrayVertex.getIdentifier());
        _builder.newLineIfNotEmpty();
      } else {
        if ((this.record.contains(innerType) && this.record.contains(arrayVertex.getIdentifier()))) {
        } else {
          String _help1 = this.help1(Query.findVertexByName(Generator.model, innerType));
          _builder.append(_help1);
          _builder.newLineIfNotEmpty();
          int maximumElems_1 = this.getMaximumElems(arrayVertex);
          _builder.newLineIfNotEmpty();
          {
            if ((maximumElems_1 > 0)) {
              _builder.append("typedef ");
              _builder.append(innerType);
              _builder.append(" ");
              String _identifier_2 = arrayVertex.getIdentifier();
              _builder.append(_identifier_2);
              _builder.append("[");
              _builder.append(maximumElems_1);
              _builder.append("];");
              _builder.newLineIfNotEmpty();
            }
          }
          {
            if ((maximumElems_1 < 0)) {
              _builder.append("typedef ");
              _builder.append(innerType);
              _builder.append(" *");
              String _identifier_3 = arrayVertex.getIdentifier();
              _builder.append(_identifier_3);
              _builder.append(";");
              _builder.newLineIfNotEmpty();
            }
          }
          boolean tmp_1 = this.record.add(arrayVertex.getIdentifier());
          _builder.newLineIfNotEmpty();
        }
      }
    }
    return _builder.toString();
  }
  
  private String getInnerType(final Vertex arrayType) {
    final Predicate<EdgeInfo> _function = new Predicate<EdgeInfo>() {
      public boolean test(final EdgeInfo e) {
        return e.hasTrait(EdgeTrait.TYPING_DATATYPES_DATADEFINITION);
      }
    };
    final Predicate<EdgeInfo> _function_1 = new Predicate<EdgeInfo>() {
      public boolean test(final EdgeInfo e) {
        return (Objects.equal(e.getSource(), arrayType.getIdentifier()) && Objects.equal(e.getSourcePort().get(), "innerType"));
      }
    };
    String innerType = Generator.model.outgoingEdgesOf(arrayType).stream().filter(_function).filter(_function_1).findAny().get().getTarget();
    return innerType;
  }
  
  private int getMaximumElems(final Vertex typeVertex) {
    int maximumElems = 0;
    VertexProperty _get = typeVertex.getProperties().get("maximumElems");
    boolean _tripleNotEquals = (_get != null);
    if (_tripleNotEquals) {
      Object _unwrap = typeVertex.getProperties().get("maximumElems").unwrap();
      maximumElems = (((Integer) _unwrap)).intValue();
    } else {
      Object _unwrap_1 = typeVertex.getProperties().get("production").unwrap();
      maximumElems = (((Integer) _unwrap_1)).intValue();
    }
    return maximumElems;
  }
}
