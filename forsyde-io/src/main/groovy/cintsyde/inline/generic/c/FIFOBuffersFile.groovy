package cintsyde.inline.generic.c

import cintsyde.interfaces.FileComponent
import cintsyde.interfaces.StringComponent
import forsyde.io.java.core.ForSyDeSystemGraph

import java.nio.file.Path

class FIFOBuffersFile implements FileComponent<ForSyDeSystemGraph> {

    ForSyDeSystemGraph baseModel
    Path targetPath
    List<FIFOBuffer> fifoBuffers = new ArrayList<>();

    @Override
    List<StringComponent<ForSyDeSystemGraph>> getStringComponents() {
        // do a small flattening of the typedefs
        return fifoBuffers;
    }


    @Override
    Map<String, Object> getContextAsMap() {
        return ["fifoBuffers": fifoBuffers]
    }

    @Override
    void setContextByMap(Map<String, Object> context) {
        fifoBuffers = (List<TypeDef>) context.getOrDefault("fifoBuffers", fifoBuffers)
    }
}
