package cintsyde.interfaces;

import cintsyde.interfaces.Component;
import com.github.mustachejava.Mustache;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public interface FileComponent<BaseT> extends Component<BaseT> {
    Path getTemplatePath();

    void setTemplatePath(Path path);

    Path getComponentPath();

    void setComponentPath(Path path);

    default void generateComponent() throws IOException {
        final Mustache mustache = mustacheFactory.compile(Files.newBufferedReader(getTemplatePath()), getTemplatePath().toString());
        mustache.execute(Files.newBufferedWriter(getComponentPath(), StandardOpenOption.CREATE_NEW, StandardOpenOption.TRUNCATE_EXISTING), getContextAsMap());
    }

    default boolean componentIsEqual(Component<BaseT> other) {
        return other instanceof FileComponent<BaseT> ? other.getTemplatePath() == getTemplatePath() &&
                other.getComponentPath() == getComponentPath() : false
    }
}
