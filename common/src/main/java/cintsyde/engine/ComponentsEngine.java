package cintsyde.engine;

import cintsyde.interfaces.Component;
import cintsyde.interfaces.ComponentCreator;
import cintsyde.interfaces.ProjectComponent;
import forsyde.io.java.core.ForSyDeSystemGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ComponentsEngine {

    final public List<ComponentCreator<?>> registeredCreators = new ArrayList<>();

    public<T extends Component> void registerCreator(ComponentCreator<T> creator) {
        registeredCreators.add(creator);
    }

    public List<ProjectComponent> findProjectComponents(ForSyDeSystemGraph forSyDeSystemGraph) {
//        return findProjectComponents(forSyDeSystemGraph, List.of(), List.of());
        final List<Component> foundComponents = new ArrayList<>(registeredCreators.size());
        int prevSize = -1;
        int currentSize = 0;
        while (prevSize < currentSize) {
            prevSize = currentSize;
            for (ComponentCreator<?> componentCreator : registeredCreators) {
                final Component component = componentCreator.create(forSyDeSystemGraph, foundComponents);
                component.expand(forSyDeSystemGraph);
                if (foundComponents.stream().noneMatch(c -> c.componentIsEqual(component))) {
                    foundComponents.add(component);
                }
            }
            currentSize = foundComponents.size();
        }
        return foundComponents.stream().filter(c -> c instanceof ProjectComponent).map(c -> (ProjectComponent) c)
                .collect(Collectors.toList());
    }
//
//    public List<ProjectComponent> findProjectComponents(ForSyDeSystemGraph forSyDeSystemGraph, List<ComponentCreator<?>> additionalCreators) {
//        return findProjectComponents(forSyDeSystemGraph, additionalCreators, List.of());
//    }
//
//    public List<ProjectComponent> findProjectComponents(ForSyDeSystemGraph forSyDeSystemGraph, List<ComponentCreator<?>> additionalCreators, List<Component> additionalComponents) {
//
//        return List.of();
//    }
}
