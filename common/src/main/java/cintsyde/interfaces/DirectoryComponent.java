package cintsyde.interfaces;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public interface DirectoryComponent<BaseT, ContexT> extends Component<BaseT, ContexT> {

    Path getSourcePath();

    void setSourcePath(Path path);

    Path getTargetPath();

    void setTargetPath(Path path);

    default void generateComponent() throws IOException {
        final Optional<Path> dirFile = Files.list(getSourcePath())
                .filter(p -> p.getFileName().toString().equals("cintsyde.groovy"))
                .findFirst();
        final Binding binding = new Binding();
        final GroovyShell groovyShell = new GroovyShell(binding);
        groovyShell.setVariable("model", getBaseModel());
        if (dirFile.isPresent())
            groovyShell.evaluate(Files.newBufferedReader(dirFile.get()));

    }

    static boolean isDirectoryComponent(Path path) throws IOException {
        return Files.isDirectory(path) && Files.list(path).anyMatch(p -> p.getFileName().toString().equals("cintsyde.groovy"));
    }
}
