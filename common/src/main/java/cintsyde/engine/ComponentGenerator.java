package cintsyde.engine;

import cintsyde.interfaces.Component;

import java.util.Collection;
import java.util.Set;

public interface ComponentGenerator<BaseT> {

    /**
     * Tries to generate a collection of elements of BaseT.
     *
     * @param baseModel the base model
     * @param generated what has been done so far
     * @return
     *  If there are still more elements to be generated, it will
     *  return an empty list. If no more elements can be genereated,
     *  a null is returned.
     */
    Collection<BaseT> generate(BaseT baseModel, Set<Component<BaseT>> generated);

}
