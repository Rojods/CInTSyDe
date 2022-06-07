package cintsyde.engine;

import cintsyde.interfaces.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface ComponentGenerator<BaseT> {

    Optional<Collection<BaseT>> generate(BaseT baseModel, Set<Component<BaseT>> generated);

}
