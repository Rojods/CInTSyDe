package cintsyde.interfaces;

import org.codehaus.groovy.runtime.DefaultGroovyMethods;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public interface FileComponent<BaseT> extends Component<BaseT> {

    public List<? extends StringComponent<BaseT>> getStringComponents();

    public Path getTargetPath();

    public void setTargetPath(Path path);

    default String getComponentIdentifier() {
        return getClass().getName();
    }

    default void generateComponent() throws IOException, URISyntaxException {
        final StringBuilder t = new StringBuilder(getPrefixTemplateString());
        for (StringComponent<BaseT> c : getStringComponents()) {
            t.append(c.generateComponent());
        }
        t.append(getSuffixTemplateString());
        Files.writeString(getTargetPath(), t.toString(), StandardOpenOption.CREATE_NEW,
                StandardOpenOption.TRUNCATE_EXISTING);
    }

    default boolean componentIsEqual(Component<BaseT> other) {
        return other instanceof FileComponent<?>
                && DefaultGroovyMethods.equals(other.getContextAsMap(), getContextAsMap())
                && ((FileComponent<BaseT>) other).getTargetPath().equals(getTargetPath());
    }

    default String getPrefixTemplateString() throws IOException, URISyntaxException {
        return "";
    }

    default String getSuffixTemplateString() throws IOException, URISyntaxException {
        return "";
    }

}
