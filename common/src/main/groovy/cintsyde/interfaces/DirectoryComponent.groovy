package cintsyde.interfaces


import java.nio.file.Path

interface DirectoryComponent<BaseT> extends Component<BaseT> {

    List<FileComponent<BaseT>> getFileComponents();
    List<DirectoryComponent<BaseT>> getDirComponents();

    default String getComponentIdentifier() {
        return getClass().getName();
    };

    Path getTargetPath();

    void setTargetPath(Path path);

    default void generateComponent() throws IOException {
        for (FileComponent<BaseT> f : getFileComponents()) {
            // relativize the childrne file
            f.setTargetPath(f.getTargetPath().resolve(getTargetPath()))
            f.generateComponent();
        }
        for (DirectoryComponent<BaseT> d : getDirComponents()) {
            // relativize the children dir
            d.setTargetPath(d.getTargetPath().resolve(getTargetPath()))
            d.generateComponent();
        }
    }

    default boolean componentIsEqual(Component<BaseT> other) {
        return other instanceof DirectoryComponent<BaseT> ? other.getComponentIdentifier() == getComponentIdentifier() &&
                other.getTargetPath() == getTargetPath() : false
    }
}