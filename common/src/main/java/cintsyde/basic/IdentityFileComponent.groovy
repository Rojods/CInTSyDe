package cintsyde.basic

import cintsyde.interfaces.Component
import cintsyde.interfaces.FileComponent

import java.nio.file.Path
import java.util.function.Function

class IdentityFileComponent<BaseT, ContextT> implements FileComponent<BaseT, ContextT> {

    private ContextT context;
    private Path templatePath;
    private Path componentPath;

    @Override
    ContextT getContext() {
        return context
    }

    @Override
    void setContext(ContextT context) {
        this.context = context
    }

    @Override
    void setQuery(Function<BaseT, Void> query) {

    }

    @Override
    Function<BaseT, Void> getQuery() {
        return { b -> };
    }

    @Override
    boolean componentIsEqual(Component<BaseT, ?> other) {
        return other instanceof FileComponent<BaseT, ?> ? other.templatePath == templatePath : false
    }

    @Override
    Path getTemplatePath() {
        return templatePath
    }

    @Override
    void setTemplatePath(Path path) {
        templatePath = path
    }

    @Override
    Path getComponentPath() {
        return componentPath
    }

    @Override
    void setComponentPath(Path path) {
        this.componentPath = path
    }

    @Override
    boolean equals(Object obj) {
        return obj instanceof Component<BaseT, ?> ? componentIsEqual(obj) : false
    }
}
