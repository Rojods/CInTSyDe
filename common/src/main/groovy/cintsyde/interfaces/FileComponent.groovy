package cintsyde.interfaces


import com.github.mustachejava.Mustache

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption
import java.util.stream.Collectors;

public interface FileComponent<BaseT> extends Component<BaseT> {

    List<StringComponent<BaseT>> getStringComponents();

    Path getTargetPath();

    void setTargetPath(Path path);

    default String getComponentIdentifier() {
        return getClass().getName();
    };

    default void generateComponent() throws IOException {
        Files.writeString(
                getTargetPath(),
                getPrefixTemplateString() + getStringComponents().stream().map(c -> c.generateComponent()).collect(Collectors.joining("")) + getSuffixTemplateString(),
                StandardOpenOption.CREATE_NEW, StandardOpenOption.TRUNCATE_EXISTING
        )
    }

    default boolean componentIsEqual(Component<BaseT> other) {
        return other instanceof FileComponent<BaseT> ? other.getContextAsMap() == getContextAsMap() &&
                other.getTargetPath() == getTargetPath() : false
    }

    default String getPrefixTemplateString() {
        return ""
    }

    default String getSuffixTemplateString() {
        return ""
    }
}
