package cintsyde.interfaces;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public interface DirectoryComponent<BaseT, ContexT> extends Component<BaseT, ContexT> {

    Path getSourcePath();

    void setSourcePath(Path path);

    Path getTargetPath();

    void setTargetPath(Path path);

    default void generateComponent() throws IOException {

    }

    static boolean isDirectoryComponent(Path path) throws IOException {
        return Files.isDirectory(path) && Files.list(path).anyMatch(p -> p.getFileName().toString().equals("cintsyde.groovy"));
    }
}
