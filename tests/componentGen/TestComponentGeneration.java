package componentGen;


import cintsyde.interfaces.Component;
import engine.cintsyde.ForSyDeComponentEngine;
import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.drivers.ForSyDeModelHandler;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class TestComponentGeneration {

    final ForSyDeModelHandler forSyDeModelHandler = (new ForSyDeModelHandler());

    @Test
    public void testGenerationFromFile() throws Exception {
        final ForSyDeComponentEngine forSyDeComponentEngine = new ForSyDeComponentEngine();
        final ForSyDeSystemGraph forSyDeSystemGraph = forSyDeModelHandler.loadModel("lib/example1-2cores.fiodl");
        final Set<? extends Component<ForSyDeSystemGraph>> componentSet = forSyDeComponentEngine.generateCompononents(forSyDeSystemGraph);
        System.out.println(componentSet);
    }

}
