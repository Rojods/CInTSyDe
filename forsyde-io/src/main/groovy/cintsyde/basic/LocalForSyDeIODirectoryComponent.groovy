package cintsyde.basic

import cintsyde.interfaces.Component

import cintsyde.interfaces.FileComponent
import cintsyde.interfaces.StringComponent
import forsyde.io.java.core.ForSyDeSystemGraph

import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors

class LocalForSyDeIODirectoryComponent implements DirectoryComponent<ForSyDeSystemGraph> {

    ForSyDeSystemGraph baseModel
    Map<String, Object> dynamicContext
    Path sourcePath
    Path targetPath

    @Override
    Map<String, Object> getContextAsMap() {
        return dynamicContext
    }

    @Override
    void setContextByMap(Map<String, Object> context) {
        dynamicContext = context
    }

    @Override
    void generateComponent() throws IOException {
        final List<Path> sourceFilePaths = new ArrayList<>();
        final List<Path> sourceChildDirPaths = new ArrayList<>();
        Files.list(getSourcePath()).forEach(p -> {
            if (Files.isDirectory(p)) sourceChildDirPaths.add(p);
            else sourceFilePaths.add(p);
        });
        final Optional<Path> dirFilePath = sourceFilePaths.stream()
                .filter(p -> p.getFileName().toString().equals("cintsyde.groovy"))
                .findAny();
        final Map<Path, List<Path>> targetFilePaths = new HashMap<>(sourceFilePaths.size());
        final Map<Path, List<Path>> targetDirPaths = new HashMap<>(sourceFilePaths.size());
        final Map<String, Component<ForSyDeSystemGraph>> generate = new HashMap<>(targetDirPaths.size() + targetFilePaths.size());

        final Binding binding = new Binding();
        final GroovyShell groovyShell = new GroovyShell(binding);
        getContextAsMap().forEach(binding::setVariable);
        binding.setVariable("baseModel", getBaseModel());
        binding.setVariable("fileComponents",
                sourceFilePaths.stream().map(p -> p.getFileName().toString()).collect(Collectors.toList())
        );
        binding.setVariable("dirComponents",
                sourceChildDirPaths.stream().map(p -> p.getFileName().toString()).collect(Collectors.toList())
        );
        binding.setVariable("generate", generate);

        if (dirFilePath.isPresent())
            groovyShell.evaluate(Files.newBufferedReader(dirFilePath.get()));

        setContextByMap(binding.getVariables())
        Files.createDirectories(targetPath);
        for (String pathString : generate.keySet()) {
            final Component<ForSyDeSystemGraph> component = generate.get(pathString);
            final Path componentTargetPath = getTargetPath().resolve(pathString)
            if (component instanceof FileComponent) {
                final FileComponent<ForSyDeSystemGraph> fileComponent = (FileComponent<ForSyDeSystemGraph>) component;
                fileComponent.targetPath = componentTargetPath;
                fileComponent.setContextByMap(binding.getVariables());
                fileComponent.generateComponent();
            } else if (component instanceof StringComponent) {
                final StringComponent<ForSyDeSystemGraph> inlineComponent = (StringComponent<ForSyDeSystemGraph>) component;
                inlineComponent.targetPath = componentTargetPath;
                inlineComponent.setContextByMap(binding.getVariables());
                inlineComponent.generateComponent();
            } else if (component instanceof DirectoryComponent<ForSyDeSystemGraph>) {
                final DirectoryComponent<ForSyDeSystemGraph> directoryComponent = (DirectoryComponent<ForSyDeSystemGraph>) component;
                directoryComponent.targetPath = componentTargetPath;
                directoryComponent.setContextByMap(binding.getVariables());
                directoryComponent.generateComponent();
            }
        }

    }
}
