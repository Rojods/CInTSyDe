package cintsyde.interfaces;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public interface DirectoryComponent<BaseT, ContexT> extends Component<BaseT, ContexT> {

    Path getComponentPath();

    void setComponentPath(Path path);

    default void generate(Path targetPath) throws IOException {

    }

    static boolean isDirectoryComponent(Path path) throws IOException {
        return Files.isDirectory(path) && Files.list(path).anyMatch(p -> p.getFileName().toString().equals("cintsyde.groovy"));
    }
}
