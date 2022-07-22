package cintsyde.inline.moc.sdf;

import cintsyde.engine.Generate;
import cintsyde.inline.generic.c.FIFOBuffersFile;
import cintsyde.interfaces.Component;
import cintsyde.interfaces.FileComponent;
import cintsyde.interfaces.StringComponent;
import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Generate
@Builder
public class SDFChannelsDefs implements FileComponent<ForSyDeSystemGraph> {

    @Getter
    @Setter
    private ForSyDeSystemGraph baseModel;
    @Getter
    @Setter
    private Path targetPath;
    @Getter
    @Setter
    private FIFOBuffersFile fifoBuffersFile;
    @Getter
    @Setter
    private List<SDFChannel> sdfChannels;

    @Override
    public List<? extends StringComponent<ForSyDeSystemGraph>> getStringComponents() {
        return List.of();
    }

    @Override
    public Map<String, Object> getContextAsMap() {
        Map<String, Object> map = new HashMap<>(1);
        map.put("fifoBufferPath", fifoBuffersFile.getTargetPath().toString());
        return map;
    }

    @Override
    public void setContextByMap(Map<String, Object> context) {

    }

    @Override
    public String getSuffixTemplateString() throws IOException, URISyntaxException {
        return "";
    }

    @Override
    public boolean subsumes(Component<ForSyDeSystemGraph> other) {
        if (other instanceof SDFChannelsDefs) {
            final SDFChannelsDefs sdfChannelsDefs = (SDFChannelsDefs) other;
            if (fifoBuffersFile.subsumes(sdfChannelsDefs.getFifoBuffersFile()) &&
                sdfChannels.containsAll(sdfChannelsDefs.getSdfChannels())) {
                    return true;
            }
        }
        return false;
    }

    public static Set<SDFChannelsDefs> generate(ForSyDeSystemGraph baseModel, Set<? extends Component<ForSyDeSystemGraph>> components) {
        final Set<SDFChannelsDefs> generatedComponents = components.stream().filter(c -> c instanceof SDFChannelsDefs)
                .map(c -> (SDFChannelsDefs) c).collect(Collectors.toSet());
        final Set<FIFOBuffersFile> generatedFifoBuffersFiles = components.stream().filter(c -> c instanceof FIFOBuffersFile)
                .map(c -> (FIFOBuffersFile) c).collect(Collectors.toSet());
        final Set<SDFChannel> sdfChannels = baseModel.vertexSet().stream().flatMap(v -> SDFChannel.safeCast(v).stream())
                .collect(Collectors.toSet());
        return Set.of();
    }

}
