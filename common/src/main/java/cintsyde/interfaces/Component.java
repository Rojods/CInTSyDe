package cintsyde.interfaces;

import forsyde.io.java.core.ForSyDeSystemGraph;

public interface Component {

    boolean componentIsEqual(Component other);

    void expand(ForSyDeSystemGraph forSyDeSystemGraph);
}
