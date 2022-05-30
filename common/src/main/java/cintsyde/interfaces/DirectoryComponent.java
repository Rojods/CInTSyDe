package cintsyde.interfaces;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public interface DirectoryComponent<BaseT> extends Component<BaseT> {

    Path getSourcePath();

    void setSourcePath(Path path);

    Path getTargetPath();

    void setTargetPath(Path path);

    void generateComponent() throws IOException;

    default boolean componentIsEqual(Component<BaseT> other) {
        return other instanceof DirectoryComponent && ((DirectoryComponent<BaseT>) other).getSourcePath() == getSourcePath()
                && other.getBaseModel().equals(getBaseModel());
    }

    static boolean isDirectoryComponent(Path path) throws IOException {
        return Files.isDirectory(path) && Files.list(path).anyMatch(p -> p.getFileName().toString().equals("cintsyde.groovy"));
    }
}
