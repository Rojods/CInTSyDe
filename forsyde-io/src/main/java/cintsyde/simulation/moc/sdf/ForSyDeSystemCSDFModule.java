package cintsyde.simulation.moc.sdf;

import cintsyde.basic.AnonymousStringComponent;
import cintsyde.interfaces.FileComponent;
import cintsyde.interfaces.StringComponent;
import forsyde.io.java.core.EdgeTrait;
import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.typed.viewers.moc.sdf.SDFActor;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ForSyDeSystemCSDFModule implements StringComponent<ForSyDeSystemGraph>,
    FileComponent<ForSyDeSystemGraph> {

    @Getter
    @Setter
    ForSyDeSystemGraph baseModel;
    @Getter @Setter
    Path targetPath;

    @Getter
    List<ForSyDeSystemCSDFModule> childModules = new ArrayList<>();

    @Getter
    SDFActor topActor;

    public ForSyDeSystemCSDFModule(ForSyDeSystemGraph baseModel, SDFActor topActor) {
        this.baseModel = baseModel;
        this.topActor = topActor;

        // TODO: recurse on the children of the top actor
        childModules = baseModel.vertexSet().stream()
                .flatMap(v -> baseModel.outgoingEdgesOf(v).stream())
                .map(baseModel::getEdgeTarget)
                .flatMap(v -> SDFActor.safeCast(v).stream())
                .map(sdfActor -> new ForSyDeSystemCSDFModule(baseModel, sdfActor))
                .collect(Collectors.toList());

        // make the context for the main file
        // populate the main file

    }

    @Override
    public Map<String, Object> getContextAsMap() {
        return Map.of(
                "actorName", topActor.getIdentifier(),
                "actorClassName", topActor.getIdentifier().substring(0, 1).toUpperCase() + topActor.getIdentifier().substring(1),
                "childModules", childModules
        );
    }

    @Override
    public void setContextByMap(Map<String, Object> context) {

    }

    @Override
    public List<? extends StringComponent<ForSyDeSystemGraph>> getStringComponents() {
        return List.of(this);
    }

    @Override
    public String getComponentTemplate() throws IOException, URISyntaxException {
        return Files.readString(Path.of(getClass().getResource("ForSyDeSystemCSDFModule.template.hpp").toURI()));
    }
}
