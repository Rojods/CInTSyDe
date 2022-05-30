package cintsyde.inline.simulation

import cintsyde.interfaces.DirectoryComponent
import cintsyde.interfaces.InlineComponent
import cintsyde.interfaces.InlineDirectoryComponent
import forsyde.io.java.core.ForSyDeSystemGraph

import java.nio.file.Path

class SingleCorePASSSDF implements InlineDirectoryComponent<ForSyDeSystemGraph> {

    ForSyDeSystemGraph baseModel
    Path targetPath


    @Override
    Map<String, Object> getContextAsMap() {
        return null
    }

    @Override
    void setContextByMap(Map<String, Object> context) {

    }

    @Override
    void generateComponent() throws IOException {

    }
}
