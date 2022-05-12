package cintsyde.basic

import cintsyde.interfaces.Component
import cintsyde.interfaces.FileComponent

import java.nio.file.Path
import java.util.function.Function

class IdentityFileComponent<BaseT> implements FileComponent<BaseT> {

    BaseT baseModel
    private Path templatePath;
    private Path componentPath;

    @Override
    Map<String, Object> getContextAsMap() {
        return [:]
    }

    @Override
    void setContextByMap(Map<String, Object> context) {

    }

    @Override
    boolean componentIsEqual(Component<BaseT> other) {
        return other instanceof FileComponent<BaseT> ? other.templatePath == templatePath : false
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
        return obj instanceof Component<BaseT> ? componentIsEqual(obj) : false
    }
}
