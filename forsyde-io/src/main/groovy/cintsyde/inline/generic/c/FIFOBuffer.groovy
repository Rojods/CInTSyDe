package cintsyde.inline.generic.c

import cintsyde.interfaces.InlineComponent
import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.typed.viewers.typing.datatypes.DataType

import java.nio.file.Path

class FIFOBuffer implements InlineComponent<ForSyDeSystemGraph> {

    ForSyDeSystemGraph baseModel
    DataType fifoDataType
    List<Path> dataTypePaths

    @Override
    Map<String, Object> getContextAsMap() {
        return [
                "fifoDataType": fifoDataType,
                "dataTypePaths": dataTypePaths,
                "typeName": fifoDataType.identifier,
                "dataTypesPath": dataTypePaths.collectEntries { ["pathName", it.toString()]}
        ]
    }

    @Override
    void setContextByMap(Map<String, Object> context) {
        fifoDataType = context.getOrDefault("fifoDateType", fifoDataType) as DataType
        dataTypePaths = context.getOrDefault("dataTypePaths", dataTypePaths) as List<Path>
    }

    @Override
    String getComponentTemplate() {
        return getClass().getResource("FIFOBuffer.headerOnly.template.c")
    }
}
