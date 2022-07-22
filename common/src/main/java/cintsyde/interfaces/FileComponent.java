package cintsyde.interfaces;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;

public interface FileComponent<BaseT> extends Component<BaseT> {

    public List<? extends StringComponent<BaseT>> getStringComponents();

    public Path getTargetPath();

    public void setTargetPath(Path path);

    default void generateComponentFile() throws IOException, URISyntaxException {
        final StringBuilder t = new StringBuilder(getPrefixTemplateString());
        for (StringComponent<BaseT> c : getStringComponents()) {
            t.append(c.generateComponentString());
        }
        t.append(getSuffixTemplateString());
        Files.writeString(getTargetPath(), t.toString(), StandardOpenOption.CREATE_NEW,
                StandardOpenOption.TRUNCATE_EXISTING);
    }

    default String getPrefixTemplateString() throws IOException, URISyntaxException {
        return "";
    }

    default String getSuffixTemplateString() throws IOException, URISyntaxException {
        return "";
    }

}
