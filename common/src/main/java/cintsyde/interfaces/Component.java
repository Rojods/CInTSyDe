package cintsyde.interfaces;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import java.util.Map;
import java.util.function.Function;

/**
 * This class represents a generic component that
 * can expend on top of a base model M.
 */
public interface Component<BaseT> {


    BaseT getBaseModel();

    void setBaseModel(BaseT baseModel);

    Map<String, Object> getContextAsMap();
    void setContextByMap(Map<String, Object> context);

    boolean componentIsEqual(Component<BaseT> other);

    static MustacheFactory mustacheFactory = new DefaultMustacheFactory();
}
