package cintsyde.basic

import cintsyde.interfaces.Component
import cintsyde.interfaces.FileComponent

import java.nio.file.Path
import java.util.function.Function

class LocalFileComponent<BaseT, ContextT> implements FileComponent<BaseT, ContextT> {

    ContextT context
    Path templatePath
    Path componentPath
    Closure<Void> queryClosure
    Map<Object, Object> dynamicContext = new HashMap<>()

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
        queryClosure = query as Closure<Void>
    }

    @Override
    Function<BaseT, Void> getQuery() {
        return queryClosure
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
        componentPath = path
    }

    def propertyMissing(String name) {
        return dynamicContext[name]
    }

    def propertyMissing(String name, def arg) {
        dynamicContext[name] = arg
    }
}
