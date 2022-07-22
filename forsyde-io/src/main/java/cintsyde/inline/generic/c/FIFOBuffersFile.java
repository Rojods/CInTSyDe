package cintsyde.inline.generic.c;

import cintsyde.engine.Generate;
import cintsyde.interfaces.Component;
import cintsyde.interfaces.FileComponent;
import cintsyde.interfaces.StringComponent;
import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.typed.viewers.typing.datatypes.DataType;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
    Set<FIFOBuffer> fifoBuffers = new HashSet<>();

    @Override
    public List<? extends StringComponent<ForSyDeSystemGraph>> getStringComponents() {
        // do a small flattening of the typedefs
        return new ArrayList<>(fifoBuffers);
    }

    @Override
    public Map<String, Object> getContextAsMap() {
        return Map.of("fifoBuffers", fifoBuffers);
    }

    @Override
    public void setContextByMap(Map<String, Object> context) {
        fifoBuffers = (Set<FIFOBuffer>) context.getOrDefault("fifoBuffers", fifoBuffers);
    }

    @Override
    public boolean subsumes(Component<ForSyDeSystemGraph> other) {
        if (other instanceof FIFOBuffersFile) {
            final FIFOBuffersFile fifoBuffersFile = (FIFOBuffersFile) other;
            if (fifoBuffers.containsAll(fifoBuffersFile.getFifoBuffers())) {
                return true;
            }
        }
        return false;
    }

    public static Set<FIFOBuffersFile> generate(ForSyDeSystemGraph baseModel, Set<? extends Component<ForSyDeSystemGraph>> components) {
        final boolean hasTypes = baseModel.vertexSet().stream().anyMatch(c -> DataType.conforms(c));
        final Set<FIFOBuffersFile> generatedComponents = components.stream().filter(c -> c instanceof FIFOBuffersFile)
                .map(c -> (FIFOBuffersFile) c).collect(Collectors.toSet());
        final Set<FIFOBuffer> generatedFifos = components.stream().filter(c -> c instanceof FIFOBuffer).map(c -> (FIFOBuffer) c)
                .collect(Collectors.toSet());
        // check if there is still any datatype that has not been covered by the
        // typedefsfile
        if (!hasTypes)  {
            return null;
        } else if (!generatedComponents.isEmpty() && generatedFifos.stream().allMatch(t -> generatedComponents.stream().anyMatch(c -> c.getFifoBuffers().contains(t)))) {
            return null;
        } else {
            final FIFOBuffersFile fifoBuffersFile = new FIFOBuffersFile();
            fifoBuffersFile.fifoBuffers = generatedFifos;
            fifoBuffersFile.baseModel = baseModel;
            fifoBuffersFile.targetPath = Paths.get("inc/fifobuffers.h");
            return Set.of(fifoBuffersFile);
        }
    }
}
