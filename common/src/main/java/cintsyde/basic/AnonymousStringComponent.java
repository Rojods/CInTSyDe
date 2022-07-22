package cintsyde.basic;

import cintsyde.interfaces.StringComponent;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class AnonymousStringComponent<BaseT> implements StringComponent<BaseT> {

    BaseT baseModel;
    String componentTemplateString;
    Map<String, Object> context;

    public AnonymousStringComponent(BaseT baseModel, String componentTemplateString, Map<String, Object> context) {
        this.baseModel = baseModel;
        this.componentTemplateString = componentTemplateString;
        this.context = context;
    }

    @Override
    public BaseT getBaseModel() {
        return baseModel;
    }

    @Override
    public void setBaseModel(BaseT baseModel) {
        this.baseModel = baseModel;
    }

    @Override
    public Map<String, Object> getContextAsMap() {
        return context;
    }

    @Override
    public void setContextByMap(Map<String, Object> context) {
        this.context = context;
    }

    @Override
    public String getComponentTemplate() throws IOException, URISyntaxException {
        return componentTemplateString;
    }
}
