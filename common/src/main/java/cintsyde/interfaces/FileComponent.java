package cintsyde.interfaces;

import com.github.mustachejava.Mustache;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public interface FileComponent<BaseT, ContextT> extends Component<BaseT, ContextT> {
    InputStream getTemplateString();

    Path getComponentPath();

    void setComponentPath(Path path);

    default void generateComponent(BaseT baseModel, ContextT context) throws IOException {
        setContext(context);
        generateComponent(baseModel);
    }

    default void generateComponent(BaseT baseModel) throws IOException {
        final Mustache mustache = mustacheFactory.compile(new InputStreamReader(getTemplateString()), getComponentPath().toString());
        query(baseModel);
        mustache.execute(Files.newBufferedWriter(getComponentPath(), StandardOpenOption.CREATE_NEW, StandardOpenOption.TRUNCATE_EXISTING), getContext());
    }
}
