package cintsyde.basic

import cintsyde.interfaces.Component
import cintsyde.interfaces.FileComponent

import java.nio.file.Path
import java.util.function.Function

class LocalFileComponent<BaseT> implements FileComponent<BaseT> {

    BaseT baseModel
    Path templatePath
    Path componentPath
    Map<String, Object> dynamicContext = new HashMap<>()

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

    @Override
    Map<String, Object> getContextAsMap() {
        return dynamicContext
    }

    @Override
    void setContextByMap(Map<String, Object> context) {
        dynamicContext = context
    }
}
