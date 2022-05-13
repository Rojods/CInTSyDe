package cintsyde.inline.platform;

import cintsyde.interfaces.Component
import cintsyde.interfaces.InlineComponent;
import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.typed.viewers.platform.GenericMemoryModule
import forsyde.io.java.typed.viewers.platform.GenericProcessingModule
import forsyde.io.java.typed.viewers.platform.runtime.CyclicExecutiveScheduler

import java.nio.file.Path;

public class PartitionedSharedMemMultiCore implements InlineComponent<ForSyDeSystemGraph> {

    String componentIdentifier
    Path targetPath
    ForSyDeSystemGraph baseModel

    String componentTemplate = ""

    @Override
    Map<String, Object> getContextAsMap() {
        return null
    }

    @Override
    void setContextByMap(Map<String, Object> context) {

    }
}
