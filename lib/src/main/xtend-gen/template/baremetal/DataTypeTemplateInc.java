package template.baremetal;

import com.google.common.base.Objects;
import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.core.VertexAcessor;
import forsyde.io.java.core.VertexTrait;
import generator.Generator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.templateInterface.InitTemplate;

@FileTypeAnno(type = FileType.C_INCLUDE)
@SuppressWarnings("all")
public class DataTypeTemplateInc implements InitTemplate {
  private List<VertexTrait> primitiveDataTypeList;
  
  public DataTypeTemplateInc() {
    ArrayList<VertexTrait> _arrayList = new ArrayList<VertexTrait>();
    this.primitiveDataTypeList = _arrayList;
    this.init();
  }
  
  public boolean init() {
    boolean _xblockexpression = false;
    {
      this.primitiveDataTypeList.add(VertexTrait.TYPING_DATATYPES_INTEGER);
      this.primitiveDataTypeList.add(VertexTrait.TYPING_DATATYPES_FLOAT);
      this.primitiveDataTypeList.add(VertexTrait.TYPING_DATATYPES_DOUBLE);
      _xblockexpression = this.primitiveDataTypeList.add(VertexTrait.TYPING_DATATYPES_ARRAY);
    }
    return _xblockexpression;
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
      _builder.append("//double");
      _builder.newLine();
      String _doubleTypeDef = this.doubleTypeDef();
      _builder.append(_doubleTypeDef);
      _builder.newLineIfNotEmpty();
      _builder.append("//float");
      _builder.newLine();
      String _floatTypeDef = this.floatTypeDef();
      _builder.append(_floatTypeDef);
      _builder.newLineIfNotEmpty();
      _builder.append("//int");
      _builder.newLine();
      String _intTypeDef = this.intTypeDef();
      _builder.append(_intTypeDef);
      _builder.newLineIfNotEmpty();
      _builder.append("//array");
      _builder.newLine();
      String _arrayTypeDef = this.arrayTypeDef();
      _builder.append(_arrayTypeDef);
      _builder.newLineIfNotEmpty();
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
      }
      if (_hasElements) {
        _builder.append("");
      }
    }
    return _builder.toString();
  }
  
  public String arrayTypeDef() {
    StringConcatenation _builder = new StringConcatenation();
    final Predicate<Vertex> _function = new Predicate<Vertex>() {
      public boolean test(final Vertex v) {
        return (v.hasTrait(VertexTrait.TYPING_DATATYPES_ARRAY)).booleanValue();
      }
    };
    Set<Vertex> arrayVertexSet = Generator.model.vertexSet().stream().filter(_function).collect(Collectors.<Vertex>toSet());
    _builder.newLineIfNotEmpty();
    {
      boolean _hasElements = false;
      for(final Vertex arrayVertex : arrayVertexSet) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate("", "");
        }
        Object _unwrap = arrayVertex.getProperties().get("maximumElems").unwrap();
        Integer maximumElems = ((Integer) _unwrap);
        _builder.newLineIfNotEmpty();
        {
          if (((maximumElems).intValue() > 0)) {
            _builder.append("typedef ");
            String _innerType = this.getInnerType(arrayVertex);
            _builder.append(_innerType);
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
          if (((maximumElems).intValue() < 0)) {
            _builder.append("typedef ");
            String _innerType_1 = this.getInnerType(arrayVertex);
            _builder.append(_innerType_1);
            _builder.append(" *");
            String _identifier_1 = arrayVertex.getIdentifier();
            _builder.append(_identifier_1);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
      }
      if (_hasElements) {
        _builder.append("");
      }
    }
    return _builder.toString();
  }
  
  private String getInnerType(final Vertex arrayType) {
    Vertex innertypeVertex = VertexAcessor.getNamedPort(Generator.model, arrayType, "innerType", 
      VertexTrait.TYPING_DATATYPES_DATATYPE).orElse(null);
    boolean _equals = Objects.equal(innertypeVertex, null);
    if (_equals) {
      return "ERROR! InnerType Not Found!";
    } else {
      return innertypeVertex.getIdentifier();
    }
  }
  
  public boolean add(final VertexTrait trait) {
    return this.primitiveDataTypeList.add(trait);
  }
}
