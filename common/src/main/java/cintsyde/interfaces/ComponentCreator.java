package cintsyde.interfaces;

import forsyde.io.java.core.ForSyDeSystemGraph;

import java.util.List;

@Deprecated
public interface ComponentCreator<T extends Component> {

    T create(ForSyDeSystemGraph forSyDeSystemGraph, List<Component> existingComponents);
}
