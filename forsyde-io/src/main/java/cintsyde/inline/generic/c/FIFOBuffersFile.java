package cintsyde.inline.generic.c;

import cintsyde.engine.Generate;
import cintsyde.interfaces.FileComponent;
import cintsyde.interfaces.StringComponent;
import forsyde.io.java.core.ForSyDeSystemGraph;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Generate
public class FIFOBuffersFile implements FileComponent<ForSyDeSystemGraph> {

    @Getter
    @Setter
    ForSyDeSystemGraph baseModel;
    @Getter
    @Setter
    Path targetPath;
    @Getter
    @Setter
    List<FIFOBuffer> fifoBuffers = new ArrayList<>();

    @Override
    public List<? extends StringComponent<ForSyDeSystemGraph>> getStringComponents() {
        // do a small flattening of the typedefs
        return fifoBuffers;
    }

    @Override
    public Map<String, Object> getContextAsMap() {
        return Map.of("fifoBuffers", fifoBuffers);
    }

    @Override
    public void setContextByMap(Map<String, Object> context) {
        fifoBuffers = (List<FIFOBuffer>) context.getOrDefault("fifoBuffers", fifoBuffers);
    }
}
