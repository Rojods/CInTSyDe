package cintsyde.basic


import cintsyde.interfaces.FileComponent
import cintsyde.interfaces.StringComponent

import java.nio.file.Path
import java.nio.file.Paths

class LocalFileComponent<BaseT> implements FileComponent<BaseT> {

    BaseT baseModel
    Path templatePath
    Path targetPath = Paths.get("src-gen")
    Map<String, Object> dynamicContext = new HashMap<>()

    LocalFileComponent(Path templatePath) {
        this.templatePath = templatePath
        this.targetPath = Paths.get("src-gen").resolve(templatePath)
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

    @Override
    List<StringComponent<BaseT>> getStringComponents() {
        return null
    }

}
