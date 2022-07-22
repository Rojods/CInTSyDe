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
import java.util.*;
import java.util.stream.Collectors;

public class ForSyDeSystemCSDFModule implements StringComponent<ForSyDeSystemGraph>,
    FileComponent<ForSyDeSystemGraph> {

    @Getter
    @Setter
    ForSyDeSystemGraph baseModel;
    @Getter @Setter
    Path targetPath;

    @Getter
    Set<ForSyDeSystemCSDFModule> childModules = new HashSet<>();

    @Getter
    SDFActor topActor;
    @Getter
    Set<SDFActor> childLeafSDFActors = new HashSet<>();

    public ForSyDeSystemCSDFModule(ForSyDeSystemGraph baseModel, SDFActor topActor) {
        this.baseModel = baseModel;
        this.topActor = topActor;

        // TODO: recurse on the children of the top actor
        Set<SDFActor> sdfActors = baseModel.vertexSet().stream()
                .flatMap(v -> baseModel.outgoingEdgesOf(v).stream())
                .map(baseModel::getEdgeTarget)
                .flatMap(v -> SDFActor.safeCast(v).stream())
                .collect(Collectors.toSet());
        Set<SDFActor> composites = sdfActors.stream().filter(a ->
                baseModel.outgoingEdgesOf(a.getViewedVertex()).stream()
                        .map(baseModel::getEdgeTarget)
                        .flatMap(childA -> SDFActor.safeCast(childA).stream())
                        .findAny()
                        .isPresent()
        ).collect(Collectors.toSet());

        childModules = composites.stream()
                .map(sdfActor -> new ForSyDeSystemCSDFModule(baseModel, sdfActor))
                .collect(Collectors.toSet());

        childLeafSDFActors = sdfActors.stream().filter(composites::contains).collect(Collectors.toSet());

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
