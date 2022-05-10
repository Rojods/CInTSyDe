package cintsyde.interfaces;

import cintsyde.interfaces.Component;
import com.github.mustachejava.Mustache;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public interface FileComponent<BaseT, ContextT> extends Component<BaseT, ContextT> {
    Path getTemplatePath();

    void setTemplatePath(Path path);

    Path getComponentPath();

    void setComponentPath(Path path);

    default void generateComponent(BaseT baseModel, ContextT context) throws IOException {
        setContext(context);
        generateComponent(baseModel);
    }

    default void generateComponent(BaseT baseModel) throws IOException {
        final Mustache mustache = mustacheFactory.compile(Files.newBufferedReader(getTemplatePath()), getTemplatePath().toString());
        getQuery().apply(baseModel);
        mustache.execute(Files.newBufferedWriter(getComponentPath(), StandardOpenOption.CREATE_NEW, StandardOpenOption.TRUNCATE_EXISTING), getContext());
    }

    default boolean componentIsEqual(Component<BaseT, ?> other) {
        return other instanceof FileComponent<BaseT, ContextT> ? other.getTemplatePath() == getTemplatePath() &&
                other.getComponentPath() == getComponentPath() : false
    }
}
