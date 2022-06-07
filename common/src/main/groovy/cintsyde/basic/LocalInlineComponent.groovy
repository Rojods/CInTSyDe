package cintsyde.basic

import cintsyde.interfaces.StringComponent

import java.nio.file.Path

class LocalInlineComponent<BaseT> implements StringComponent<BaseT> {

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
