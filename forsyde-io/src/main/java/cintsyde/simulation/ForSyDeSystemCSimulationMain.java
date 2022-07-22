package cintsyde.simulation;

import cintsyde.inline.generic.c.TypeDefsFile;
import cintsyde.interfaces.FileComponent;
import cintsyde.interfaces.StringComponent;
import cintsyde.simulation.moc.sdf.ForSyDeSystemCSDFModule;
import forsyde.io.java.core.ForSyDeSystemGraph;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ForSyDeSystemCSimulationMain implements FileComponent<ForSyDeSystemGraph> {

    @Getter @Setter
    ForSyDeSystemGraph baseModel;
    @Getter
    List<StringComponent<ForSyDeSystemGraph>> stringComponents;
    @Getter @Setter
    Path targetPath;

    @Getter
    TypeDefsFile typeDefsFile;
    @Getter
    List<ForSyDeSystemCSDFModule> sdfModules = new ArrayList<>();

    public ForSyDeSystemCSimulationMain(ForSyDeSystemGraph baseModel, TypeDefsFile typeDefsFile) {
        this.baseModel = baseModel;
        this.typeDefsFile = typeDefsFile;
    }

    @Override
    public Map<String, Object> getContextAsMap() {
        return null;
    }

    @Override
    public void setContextByMap(Map<String, Object> context) {

    }

}
