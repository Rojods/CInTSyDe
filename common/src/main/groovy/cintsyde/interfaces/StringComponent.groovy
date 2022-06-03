package cintsyde.interfaces

import com.github.mustachejava.Mustache

interface StringComponent<BaseT> extends Component<BaseT> {

    default String getComponentIdentifier() {
        return getClass().getName();
    };

    String getComponentTemplate();

    default String generateComponent() throws IOException {
        final Mustache mustache = mustacheFactory.compile(new StringReader(getComponentTemplate()), getComponentIdentifier());
        final StringWriter writer = new StringWriter();
        mustache.execute(writer, getContextAsMap());
        return writer.toString();
    }

    default boolean componentIsEqual(Component<BaseT> other) {
        return other instanceof StringComponent<BaseT> ? other.getComponentIdentifier() == getComponentIdentifier() &&
                other.getContextAsMap() == getContextAsMap() : false
    }

}