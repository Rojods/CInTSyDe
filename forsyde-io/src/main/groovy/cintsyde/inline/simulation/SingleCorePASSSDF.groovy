package cintsyde.inline.simulation


import cintsyde.interfaces.DirectoryComponent
import cintsyde.interfaces.FileComponent
import forsyde.io.java.core.ForSyDeSystemGraph

import java.nio.file.Path

class SingleCorePASSSDF implements DirectoryComponent<ForSyDeSystemGraph> {

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

    @Override
    List<FileComponent<ForSyDeSystemGraph>> getFileComponents() {
        return List.of()
    }

    @Override
    List<DirectoryComponent<ForSyDeSystemGraph>> getDirComponents() {
        return List.of()
    }
}
