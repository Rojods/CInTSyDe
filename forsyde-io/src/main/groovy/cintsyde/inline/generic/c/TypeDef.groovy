package cintsyde.inline.generic.c

import cintsyde.interfaces.StringComponent
import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.typed.viewers.typing.datatypes.Array
import forsyde.io.java.typed.viewers.typing.datatypes.DataType
import forsyde.io.java.typed.viewers.typing.datatypes.Double
import forsyde.io.java.typed.viewers.typing.datatypes.Float
import forsyde.io.java.typed.viewers.typing.datatypes.Integer
import forsyde.io.java.typed.viewers.typing.datatypes.Record

class TypeDef implements StringComponent<ForSyDeSystemGraph> {

    ForSyDeSystemGraph baseModel
    DataType dataType
    List<TypeDef> childrenTypeDefs = new ArrayList<>();

    @Override
    Map<String, Object> getContextAsMap() {
        return null
    }

    @Override
    void setContextByMap(Map<String, Object> context) {

    }

    @Override
    String getComponentTemplate() {
        if (dataType instanceof Record) {
            def a = """
                          |#ifndef DATA_TYPE_{{typeName}}_H_
                          |#define DATA_TYPE_{{typeName}}_H_
                          |typedef struct {
                          """
            def childNum = 0
            dataType.getInnerTypesPort(baseModel).forEach(child -> {
                childrenTypeDefs.add(new TypeDef(dataType: child))
                def edgeInfo = baseModel.getAllEdges(dataType.viewedVertex, child.viewedVertex).stream().findAny()
                edgeInfo.flatMap(e -> e.sourcePort.stream()).ifPresentOrElse(srcPort -> {
                    a += "  ${child.identifier} ${srcPort};\n"
                }, {
                    a += "  ${child.identifier} child${childNum};\n"
                })
                childNum += 1
            })
            a += """
                 |} ${dataType.identifier};
                 |#endif
                 """
            return a
        } else if (dataType instanceof Array) {
            return dataType.getInnerTypePort(baseModel).map(child -> {
                childrenTypeDefs.add(new TypeDef(dataType: child))
                def array = dataType as Array
                if (array.maximumElems > -1) {
                    return "typedef ${child.identifier}[${array.maximumElems}] ${array.identifier};"
                } else {
                    return "typedef ${child.identifier}* ${array.identifier};"
                }
            }).orElse("typedef void* ${dataType.identifier}; // ERROR: missing child value! default to void")
        } else if (dataType instanceof Integer) {
            if (dataType.numberOfBits <= 8) return "typedef char ${dataType.identifier};"
            else if (dataType.numberOfBits <= 32) return "typedef int ${dataType.identifier};"
            else if (dataType.numberOfBits <= 64) return "typedef long ${dataType.identifier};"
            else return "typedef long long ${dataType.identifier};"
        } else if (dataType instanceof Float) {
            return "typedef float ${dataType.identifier}"
        } else if (dataType instanceof Double) {
            return "typedef double ${dataType.identifier}"
        } else {
            return "ERROR! typedef could not be generated! check ${dataType.identifier} for mistakes."
        }
    }

    String generateWithAllChildren() {
        return childrenTypeDefs.collect {it.generateWithAllChildren()}.join("") + generateComponent()
    }

    DataType getDataType() {
        return dataType
    }

    void setDataType(DataType dataType) {
        this.dataType = dataType
    }

    List<TypeDef> getChildrenTypeDefs() {
        return childrenTypeDefs
    }

    void setChildrenTypeDefs(List<TypeDef> childrenTypeDefs) {
        this.childrenTypeDefs = childrenTypeDefs
    }
}
