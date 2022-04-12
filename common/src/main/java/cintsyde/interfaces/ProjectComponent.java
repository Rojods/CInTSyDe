package cintsyde.interfaces;

import java.nio.file.Path;

public interface ProjectComponent extends Component {

    void generateFiles(Path rootPath);
}
