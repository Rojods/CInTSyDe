package cintsyde.interfaces;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import java.util.function.Function;

/**
 * This class represents a generic component that
 * can expend on top of a base model M.
 */
public interface Component<BaseT, ContextT> {

    ContextT getContext();

    BaseT getBaseModel();

    void setBaseModel(BaseT baseModel);

    void setContext(ContextT context);

    boolean componentIsEqual(Component<BaseT, ?> other);

    static MustacheFactory mustacheFactory = new DefaultMustacheFactory();
}
