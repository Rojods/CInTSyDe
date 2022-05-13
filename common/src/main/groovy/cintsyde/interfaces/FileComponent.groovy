package cintsyde.interfaces


import com.github.mustachejava.Mustache

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public interface FileComponent<BaseT> extends Component<BaseT> {
    Path getTemplatePath();

    void setTemplatePath(Path path);

    Path getTargetPath();

    void setTargetPath(Path path);

    default void generateComponent() throws IOException {
        final Mustache mustache = mustacheFactory.compile(Files.newBufferedReader(getTemplatePath()), getTemplatePath().toString());
        mustache.execute(Files.newBufferedWriter(getTargetPath(), StandardOpenOption.CREATE_NEW, StandardOpenOption.TRUNCATE_EXISTING), getContextAsMap());
    }

    default boolean componentIsEqual(Component<BaseT> other) {
        return other instanceof FileComponent<BaseT> ? other.getTemplatePath() == getTemplatePath() &&
                other.getTargetPath() == getTargetPath() : false
    }
}
