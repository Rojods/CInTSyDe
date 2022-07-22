package cintsyde.simulation;

import cintsyde.inline.generic.c.TypeDefsFile;
import cintsyde.interfaces.FileComponent;
import cintsyde.interfaces.StringComponent;
import cintsyde.simulation.moc.sdf.ForSyDeSystemCSDFModule;
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

public class ForSyDeSystemCSimulationMain implements FileComponent<ForSyDeSystemGraph>, StringComponent<ForSyDeSystemGraph> {

    @Getter @Setter
    ForSyDeSystemGraph baseModel;

    @Getter @Setter
    Path targetPath;

    @Getter
    TypeDefsFile typeDefsFile;
    @Getter
    Set<ForSyDeSystemCSDFModule> sdfModules = new HashSet<>();
    @Getter
    Set<SDFActor> childLeafSDFActors = new HashSet<>();

    public ForSyDeSystemCSimulationMain(ForSyDeSystemGraph baseModel, TypeDefsFile typeDefsFile) {
        this.baseModel = baseModel;
        this.typeDefsFile = typeDefsFile;

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
        childLeafSDFActors = sdfActors.stream().filter(composites::contains).collect(Collectors.toSet());

        sdfModules = composites.stream()
                .map(sdfActor -> new ForSyDeSystemCSDFModule(baseModel, sdfActor))
                .collect(Collectors.toSet());
    }

    @Override
    public Map<String, Object> getContextAsMap() {
        return Map.of(
                "sdfActors", childLeafSDFActors.stream().map(a ->
                        Map.of("actorName", a.getIdentifier(),
                                "actorClassName", a.getIdentifier().substring(0, 1).toUpperCase() + a.getIdentifier().substring(1)
                        )
                )
        );
    }

    @Override
    public void setContextByMap(Map<String, Object> context) {

    }

    @Override
    public String getComponentTemplate() throws IOException, URISyntaxException {
        return Files.readString(Path.of(getClass().getResource("ForSyDeSystemCSimulationMain.template.cpp").toURI()));
    }

    @Override
    public List<? extends StringComponent<ForSyDeSystemGraph>> getStringComponents() {
        return List.of(this);
    }
}
