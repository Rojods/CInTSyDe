package cintsyde.interfaces

import com.github.mustachejava.Mustache

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption

interface InlineDirectoryComponent<BaseT> extends Component<BaseT> {

    default String getComponentIdentifier() {
        return getClass().getName();
    };

    Path getTargetPath();

    void setTargetPath(Path path);

    void generateComponent() throws IOException

    default boolean componentIsEqual(Component<BaseT> other) {
        return other instanceof InlineDirectoryComponent<BaseT> ? other.getComponentIdentifier() == getComponentIdentifier() &&
                other.getTargetPath() == getTargetPath() : false
    }
}