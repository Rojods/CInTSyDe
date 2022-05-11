package cintsyde.basic

import cintsyde.interfaces.InlineComponent

import java.nio.file.Path
import java.util.function.Function

class LocalInlineComponent<BaseT, ContextT> implements InlineComponent<BaseT, ContextT> {

    ContextT context
    BaseT baseModel
    String inlineCode = ""
    String identifier = "unknown"
    Path componentPath
    Map<String, Object> dynamicContext = new HashMap<>()

    @Override
    String getComponentIdentifier() {
        return identifier
    }

    @Override
    String getComponentTemplate() {
        return inlineCode
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
