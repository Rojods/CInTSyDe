package engine;

import cintsyde.engine.ComponentGenerationEngine;
import cintsyde.interfaces.ProjectComponent;
import forsyde.io.java.core.ForSyDeSystemGraph;
import interfaces.ForSyDeIOProjectComponent;
import project.SDFToSharedMemMultiCore;

import java.util.ArrayList;
import java.util.List;

public class ForSyDeIOComponentGenerationEngine implements ComponentGenerationEngine<ForSyDeSystemGraph> {

    final static private ForSyDeIOComponentGenerationEngine engine = new ForSyDeIOComponentGenerationEngine();
    final private List<ProjectComponent<ForSyDeSystemGraph>> projectComponents = new ArrayList<>();

    private ForSyDeIOComponentGenerationEngine() {
        projectComponents.add(SDFToSharedMemMultiCore.getInstance());
    }

    static public ComponentGenerationEngine<ForSyDeSystemGraph> getInstance() {
        return engine;
    }

    @Override
    public List<ProjectComponent<ForSyDeSystemGraph>> getRegisteredProjectComponents() {
        return projectComponents;
    }
}
