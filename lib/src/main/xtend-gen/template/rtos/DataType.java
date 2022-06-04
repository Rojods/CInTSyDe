package template.rtos;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.core.VertexProperty;
import forsyde.io.java.core.VertexTrait;
import forsyde.io.java.typed.viewers.typing.TypedDataBlockViewer;
import forsyde.io.java.typed.viewers.typing.datatypes.Array;
import forsyde.io.java.typed.viewers.typing.datatypes.ArrayViewer;
import forsyde.io.java.typed.viewers.typing.datatypes.IntegerViewer;
import generator.Generator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.templateInterface.InitTemplate;

@FileTypeAnno(type = FileType.C_INCLUDE)
@SuppressWarnings("all")
public class DataType implements InitTemplate {
  private Set<String> record = new HashSet<String>();
  
  public DataType() {
  }
  
  @Override
  public String getFileName() {
    return "datatype_definition";
  }
  
  @Override
  public String create() {
    String _xblockexpression = null;
    {
      ForSyDeSystemGraph model = Generator.model;
      final Predicate<Vertex> _function = (Vertex v) -> {
        return ((!(v.hasTrait(VertexTrait.MOC_SDF_SDFCHANNEL)).booleanValue()) && (v.hasTrait(VertexTrait.TYPING_TYPEDDATABLOCK)).booleanValue());
      };
      Set<Vertex> outset = model.vertexSet().stream().filter(_function).collect(Collectors.<Vertex>toSet());
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
      String _doubleTypeDef = this.doubleTypeDef(model);
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
      String _floatTypeDef = this.floatTypeDef(model);
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
      String _intTypeDef = this.intTypeDef(model);
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
      String _arrayTypeDef = this.arrayTypeDef(model);
      _builder.append(_arrayTypeDef);
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("/*");
      _builder.newLine();
      _builder.append("==============================================================");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("Outside Source and Sink Extern");
      _builder.newLine();
      _builder.append("==============================================================\t\t\t");
      _builder.newLine();
      _builder.append("*/");
      _builder.newLine();
      {
        boolean _hasElements = false;
        for(final Vertex v : outset) {
          if (!_hasElements) {
            _hasElements = true;
          } else {
            _builder.appendImmediate("", "");
          }
          _builder.append("extern ");
          String _findType = this.findType(model, v);
          _builder.append(_findType);
          _builder.append("  ");
          String _identifier = v.getIdentifier();
          _builder.append(_identifier);
          _builder.append(";");
          _builder.newLineIfNotEmpty();
        }
        if (_hasElements) {
          _builder.append("");
        }
      }
      _builder.append("\t\t");
      _builder.newLine();
      _builder.append("#endif");
      _builder.newLine();
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
  
  public String doubleTypeDef(final ForSyDeSystemGraph model) {
    StringConcatenation _builder = new StringConcatenation();
    final Predicate<Vertex> _function = (Vertex v) -> {
      return (forsyde.io.java.typed.viewers.typing.datatypes.Double.conforms(v)).booleanValue();
    };
    Set<Vertex> doubleVertexSet = model.vertexSet().stream().filter(_function).collect(Collectors.<Vertex>toSet());
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
  
  public String floatTypeDef(final ForSyDeSystemGraph model) {
    StringConcatenation _builder = new StringConcatenation();
    final Predicate<Vertex> _function = (Vertex v) -> {
      return (forsyde.io.java.typed.viewers.typing.datatypes.Float.conforms(v)).booleanValue();
    };
    Set<Vertex> floatVertexSet = model.vertexSet().stream().filter(_function).collect(Collectors.<Vertex>toSet());
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
  
  public String intTypeDef(final ForSyDeSystemGraph model) {
    StringConcatenation _builder = new StringConcatenation();
    final Predicate<Vertex> _function = (Vertex v) -> {
      return (forsyde.io.java.typed.viewers.typing.datatypes.Integer.conforms(v)).booleanValue();
    };
    final Function<Vertex, IntegerViewer> _function_1 = (Vertex v) -> {
      return new IntegerViewer(v);
    };
    Set<IntegerViewer> intVertexViewerSet = model.vertexSet().stream().filter(_function).<IntegerViewer>map(_function_1).collect(Collectors.<IntegerViewer>toSet());
    _builder.newLineIfNotEmpty();
    {
      boolean _hasElements = false;
      for(final IntegerViewer intVertexViewer : intVertexViewerSet) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate("", "");
        }
        {
          Integer _numberOfBits = intVertexViewer.getNumberOfBits();
          boolean _equals = ((_numberOfBits).intValue() == 8);
          if (_equals) {
            _builder.append("typedef char ");
            String _identifier = intVertexViewer.getIdentifier();
            _builder.append(_identifier);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
        {
          Integer _numberOfBits_1 = intVertexViewer.getNumberOfBits();
          boolean _equals_1 = ((_numberOfBits_1).intValue() == 16);
          if (_equals_1) {
            _builder.append("typedef unsigned short ");
            String _identifier_1 = intVertexViewer.getIdentifier();
            _builder.append(_identifier_1);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
        {
          Integer _numberOfBits_2 = intVertexViewer.getNumberOfBits();
          boolean _equals_2 = ((_numberOfBits_2).intValue() == 32);
          if (_equals_2) {
            _builder.append("typedef unsigned int ");
            String _identifier_2 = intVertexViewer.getIdentifier();
            _builder.append(_identifier_2);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
        {
          Integer _numberOfBits_3 = intVertexViewer.getNumberOfBits();
          boolean _equals_3 = ((_numberOfBits_3).intValue() == 64);
          if (_equals_3) {
            _builder.append("typedef unsigned long ");
            String _identifier_3 = intVertexViewer.getIdentifier();
            _builder.append(_identifier_3);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
        boolean tmp = this.record.add(intVertexViewer.getIdentifier());
        _builder.append("\t\t");
        _builder.newLineIfNotEmpty();
      }
      if (_hasElements) {
        _builder.append("");
      }
    }
    return _builder.toString();
  }
  
  public String arrayTypeDef(final ForSyDeSystemGraph model) {
    String _xblockexpression = null;
    {
      final Predicate<Vertex> _function = (Vertex v) -> {
        return (Array.conforms(v)).booleanValue();
      };
      final Function<Vertex, ArrayViewer> _function_1 = (Vertex v) -> {
        return new ArrayViewer(v);
      };
      Set<ArrayViewer> arrayViewerSet = model.vertexSet().stream().filter(_function).<ArrayViewer>map(_function_1).collect(Collectors.<ArrayViewer>toSet());
      StringConcatenation _builder = new StringConcatenation();
      {
        boolean _hasElements = false;
        for(final ArrayViewer array : arrayViewerSet) {
          if (!_hasElements) {
            _hasElements = true;
          } else {
            _builder.appendImmediate("", "");
          }
          String _help1 = this.help1(model, array);
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
  
  private String help1(final ForSyDeSystemGraph model, final ArrayViewer arr) {
    StringConcatenation _builder = new StringConcatenation();
    String innerType = arr.getInnerTypePort(Generator.model).get().getIdentifier();
    _builder.newLineIfNotEmpty();
    {
      if ((this.record.contains(innerType) && (!this.record.contains(arr.getIdentifier())))) {
        int maximumElems = this.getMaximumElems(arr.getViewedVertex());
        _builder.newLineIfNotEmpty();
        {
          if ((maximumElems > 0)) {
            _builder.append("typedef ");
            _builder.append(innerType);
            _builder.append(" ");
            String _identifier = arr.getIdentifier();
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
            String _identifier_1 = arr.getIdentifier();
            _builder.append(_identifier_1);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
        boolean tmp = this.record.add(arr.getIdentifier());
        _builder.newLineIfNotEmpty();
      } else {
        if ((this.record.contains(innerType) && this.record.contains(arr.getIdentifier()))) {
        } else {
          Vertex _get = model.queryVertex(innerType).get();
          ArrayViewer _arrayViewer = new ArrayViewer(_get);
          String _help1 = this.help1(model, _arrayViewer);
          _builder.append(_help1);
          _builder.newLineIfNotEmpty();
          int maximumElems_1 = this.getMaximumElems(arr.getViewedVertex());
          _builder.newLineIfNotEmpty();
          {
            if ((maximumElems_1 > 0)) {
              _builder.append("typedef ");
              _builder.append(innerType);
              _builder.append(" ");
              String _identifier_2 = arr.getIdentifier();
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
              String _identifier_3 = arr.getIdentifier();
              _builder.append(_identifier_3);
              _builder.append(";");
              _builder.newLineIfNotEmpty();
            }
          }
          boolean tmp_1 = this.record.add(arr.getIdentifier());
          _builder.newLineIfNotEmpty();
        }
      }
    }
    return _builder.toString();
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
  
  public String findType(final ForSyDeSystemGraph model, final Vertex datablock) {
    Optional<forsyde.io.java.typed.viewers.typing.datatypes.DataType> a = new TypedDataBlockViewer(datablock).getDataTypePort(model);
    boolean _isPresent = a.isPresent();
    boolean _not = (!_isPresent);
    if (_not) {
      return null;
    }
    return a.get().getIdentifier();
  }
}
