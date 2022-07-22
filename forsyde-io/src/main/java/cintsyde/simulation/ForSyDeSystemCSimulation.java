package cintsyde.simulation;

import cintsyde.engine.Generate;
import cintsyde.inline.generic.c.TypeDefsFile;
import cintsyde.interfaces.DirectoryComponent;
import cintsyde.interfaces.FileComponent;
import cintsyde.interfaces.StringComponent;
import forsyde.io.java.core.ForSyDeSystemGraph;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Generate
public class ForSyDeSystemCSimulation implements DirectoryComponent<ForSyDeSystemGraph> {

    @Getter
    @Setter
    ForSyDeSystemGraph baseModel;
    @Getter
    List<FileComponent<ForSyDeSystemGraph>> fileComponents = new ArrayList<>();
    @Getter
    List<DirectoryComponent<ForSyDeSystemGraph>> dirComponents = new ArrayList<>();
    @Getter
    List<StringComponent<ForSyDeSystemGraph>> stringComponents = new ArrayList<>();
    @Getter @Setter
    Path targetPath;

    @Getter
    TypeDefsFile typeDefsFile;
    @Getter
    ForSyDeSystemCSimulationMain forSyDeSystemCSimulationMain;

    public ForSyDeSystemCSimulation(ForSyDeSystemGraph baseModel) {
        this.baseModel = baseModel;
        typeDefsFile = new TypeDefsFile(baseModel);
        // this path will be resolved in the context of the file later,
        typeDefsFile.setTargetPath(Paths.get("inc/datatypes.hh"));
        fileComponents.add(typeDefsFile);

        forSyDeSystemCSimulationMain = new ForSyDeSystemCSimulationMain(baseModel, typeDefsFile);
        fileComponents.add(forSyDeSystemCSimulationMain);
    }

    @Override
    public Map<String, Object> getContextAsMap() {
        return null;
    }

    @Override
    public void setContextByMap(Map<String, Object> context) {

    }

}
