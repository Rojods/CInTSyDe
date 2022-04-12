package cintsyde.interfaces;

import forsyde.io.java.core.ForSyDeSystemGraph;

import java.util.List;

public interface ComponentCreator<T extends Component> {

    T create(ForSyDeSystemGraph forSyDeSystemGraph, List<Component> existingComponents);
}
