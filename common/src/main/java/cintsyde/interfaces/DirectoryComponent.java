package cintsyde.interfaces;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;

public interface DirectoryComponent<BaseT> extends Component<BaseT> {
    List<FileComponent<BaseT>> getFileComponents();

    List<DirectoryComponent<BaseT>> getDirComponents();

    Path getTargetPath();

    void setTargetPath(Path path);

    default void generateComponentDirectory() throws IOException, URISyntaxException {
        for (FileComponent<BaseT> f : getFileComponents()) {
            // relativize the childrne file
            f.setTargetPath(f.getTargetPath().resolve(getTargetPath()));
            f.generateComponentFile();
        }

        for (DirectoryComponent<BaseT> d : getDirComponents()) {
            // relativize the children dir
            d.setTargetPath(d.getTargetPath().resolve(getTargetPath()));
            d.generateComponentDirectory();
        }

    }

}
