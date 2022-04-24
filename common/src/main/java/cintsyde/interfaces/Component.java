package cintsyde.interfaces;

import forsyde.io.java.core.ForSyDeSystemGraph;
/**
 * This class represents a generic component that
 * can expend on top of a base model M.
 */
public interface Component<BaseT> {

    boolean componentIsEqual(Component<BaseT> other);

    void expand(BaseT baseModel);
}
