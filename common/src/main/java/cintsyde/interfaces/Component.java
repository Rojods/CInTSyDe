package cintsyde.interfaces;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

/**
 * This class represents a generic component that
 * can expend on top of a base model M.
 */
public interface Component<BaseT, ContextT> {

    ContextT getContext();

    void setContext(ContextT context);
    default void query(BaseT baseModel, ContextT currentContext) {
        setContext(currentContext);
        query(baseModel);
    };

    void query(BaseT baseModel);

    boolean componentIsEqual(Component<BaseT, ?> other);

    static MustacheFactory mustacheFactory = new DefaultMustacheFactory();
}
