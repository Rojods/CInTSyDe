package cintsyde.engine;

import cintsyde.interfaces.Component;
import cintsyde.interfaces.ProjectComponent;

import java.util.List;
import java.util.stream.Collectors;

public interface ComponentGenerationEngine<BaseT> {

    ComponentGenerationEngine<BaseT> getInstance();

    List<ProjectComponent<BaseT>> getRegisteredProjectComponents();

    default void registerProjectComponent(ProjectComponent<BaseT> projectComponent) {
        final List<ProjectComponent<BaseT>> components = getRegisteredProjectComponents();
        for (ProjectComponent<BaseT> p : components) {
            if (p.componentIsEqual(projectComponent)) {
                return;
            }
        }
        components.add(projectComponent);
    }

    default List<ProjectComponent<BaseT>> getSynthetizable(BaseT baseModel) {
        return getRegisteredProjectComponents().stream()
                .peek(c -> c.expand(baseModel))
                .filter(ProjectComponent::canGenerate)
                .collect(Collectors.toList());
    }
}
