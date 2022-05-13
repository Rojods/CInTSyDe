package cintsyde.interfaces

import com.github.mustachejava.Mustache

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption

interface InlineComponent<BaseT> extends Component<BaseT> {

    String getComponentIdentifier();

    String getComponentTemplate();

    Path getTargetPath();

    void setTargetPath(Path path);

    default void generateComponent() throws IOException {
        final Mustache mustache = mustacheFactory.compile(new StringReader(getComponentTemplate()), getComponentIdentifier());
        mustache.execute(Files.newBufferedWriter(getTargetPath(), StandardOpenOption.CREATE_NEW, StandardOpenOption.TRUNCATE_EXISTING), getContextAsMap());
    }

    default boolean componentIsEqual(Component<BaseT> other) {
        return other instanceof InlineComponent<BaseT> ? other.getComponentIdentifier() == getComponentIdentifier() &&
                other.getTargetPath() == getTargetPath() : false
    }

}