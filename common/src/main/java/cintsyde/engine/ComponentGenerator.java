package cintsyde.engine;

import cintsyde.interfaces.Component;

import java.util.Collection;
import java.util.Set;

public interface ComponentGenerator<BaseT> {

    /**
     * Tries to generate a component of BaseT. The main utility of this
     * class is to wrap {@link cintsyde.interfaces.DirectoryComponent} in a way that a cli
     * can generate them without explicit intervention of the end-user.
     *
     * @param baseModel the base model
     * @return
     *         The component if it can be generated, and null otherwise.
     */
    Component<BaseT> generate(BaseT baseModel);

}
