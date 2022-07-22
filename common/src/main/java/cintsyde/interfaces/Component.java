package cintsyde.interfaces;

import java.util.Map;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.MustacheFactory;

/**
 * This class represents a generic component that
 * can expend on top of a base model M.
 */
public interface Component<BaseT> {

    default String getComponentIdentifier() {
        return getClass().getName();
    }
    BaseT getBaseModel();

    void setBaseModel(BaseT baseModel);

    Map<String, Object> getContextAsMap();

    void setContextByMap(Map<String, Object> context);

    default boolean subsumes(Component<BaseT> other) {
        return false;
    }

    static MustacheFactory mustacheFactory = new DefaultMustacheFactory();
}
