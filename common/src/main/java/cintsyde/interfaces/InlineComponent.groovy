package cintsyde.interfaces

import com.github.mustachejava.Mustache

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption

interface InlineComponent<BaseT, ContextT> extends Component<BaseT, ContextT> {

    String getComponentIdentifier();

    String getComponentTemplate();

    Path getComponentPath();

    void setComponentPath(Path path);

    default void generateComponent(BaseT baseModel, ContextT context) throws IOException {
        setContext(context);
        generateComponent(baseModel);
    }

    default void generateComponent(BaseT baseModel) throws IOException {
        final Mustache mustache = mustacheFactory.compile(new StringReader(getComponentTemplate()), getComponentIdentifier());
        getQuery().apply(baseModel);
        mustache.execute(Files.newBufferedWriter(getComponentPath(), StandardOpenOption.CREATE_NEW, StandardOpenOption.TRUNCATE_EXISTING), getContext());
    }

    default boolean componentIsEqual(Component<BaseT, ?> other) {
        return other instanceof InlineComponent<BaseT, ContextT> ? other.getComponentIdentifier() == getComponentIdentifier() &&
                other.getComponentPath() == getComponentPath() : false
    }

}