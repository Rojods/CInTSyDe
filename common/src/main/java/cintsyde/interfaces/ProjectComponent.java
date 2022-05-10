package cintsyde.interfaces;

import java.nio.file.Path;
import java.util.List;

/**
 * This class represents a component that can generate all the
 * information on disk, for a given project, so it can be compiled.
 *
 * It is supposed to be a singleton in order to act as the starting
 * point for component-based generation. It runs on top of a base model
 * of type BaseT.
 */
public interface ProjectComponent<BaseT, ContextT> extends Component<BaseT, ContextT> {

    boolean canGenerate();

    List<Path> generateFiles(Path rootPath) throws Exception;

    // ProjectComponent<BaseT> getInstance();
}
