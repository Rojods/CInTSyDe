package platform;

import cintsyde.interfaces.Component;
import forsyde.io.java.core.ForSyDeSystemGraph;
import interfaces.ForSyDeIOComponent;

public class PartitionedSharedMemMultiCore implements ForSyDeIOComponent {

    @Override
    public boolean componentIsEqual(Component<ForSyDeSystemGraph> other) {
        return false;
    }

    @Override
    public void expand(ForSyDeSystemGraph baseModel) {

    }
}
