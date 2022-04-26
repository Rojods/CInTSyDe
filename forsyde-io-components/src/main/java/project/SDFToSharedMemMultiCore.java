package project;

import cintsyde.interfaces.Component;
import cintsyde.interfaces.ProjectComponent;
import forsyde.io.java.core.ForSyDeSystemGraph;
import interfaces.ForSyDeIOProjectComponent;

import java.nio.file.Path;
import java.util.List;

public class SDFToSharedMemMultiCore implements ForSyDeIOProjectComponent {

    private final static SDFToSharedMemMultiCore instance = new SDFToSharedMemMultiCore();

    private SDFToSharedMemMultiCore() {

    }

    @Override
    public boolean componentIsEqual(Component<ForSyDeSystemGraph> other) {
        return other == instance;
    }

    @Override
    public void expand(ForSyDeSystemGraph baseModel) {

    }

    @Override
    public boolean canGenerate() {
        return false;
    }

    @Override
    public List<Path> generateFiles(Path rootPath) throws Exception {
        return null;
    }

    static public ProjectComponent<ForSyDeSystemGraph> getInstance() {
        return instance;
    }
}
