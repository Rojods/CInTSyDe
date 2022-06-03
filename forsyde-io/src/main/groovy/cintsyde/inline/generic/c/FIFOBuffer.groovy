package cintsyde.inline.generic.c

import cintsyde.interfaces.StringComponent
import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.typed.viewers.typing.datatypes.DataType

import java.lang.reflect.Type
import java.nio.file.Path

class FIFOBuffer implements StringComponent<ForSyDeSystemGraph> {

    ForSyDeSystemGraph baseModel
    DataType fifoDataType
    TypeDefsFile typeDefsFile

    @Override
    Map<String, Object> getContextAsMap() {
        return [
                "fifoDataType": fifoDataType,
                "pathToTypeDefs": typeDefsFile.targetPath.toString(),
                "typeName": fifoDataType.identifier
        ]
    }

    @Override
    void setContextByMap(Map<String, Object> context) {
        fifoDataType = context.getOrDefault("fifoDateType", fifoDataType) as DataType
    }

    @Override
    String getComponentTemplate() {
        return getClass().getResource("FIFOBuffer.headerOnly.h")
    }
}
