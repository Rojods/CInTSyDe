package cintsyde.interfaces;

import com.github.mustachejava.Mustache;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URISyntaxException;

public interface StringComponent<BaseT> extends Component<BaseT> {

    default String getComponentIdentifier() {
        return getClass().getName();
    }

    String getComponentTemplate() throws IOException, URISyntaxException;

    default String generateComponent() throws IOException, URISyntaxException {
        final Mustache mustache = mustacheFactory.compile(new StringReader(getComponentTemplate()),
                getComponentIdentifier());
        final StringWriter writer = new StringWriter();
        mustache.execute(writer, getContextAsMap());
        return writer.toString();
    }

    default boolean componentIsEqual(Component<BaseT> other) {
        return other instanceof StringComponent<?>
                && ((StringComponent<BaseT>) other).getComponentIdentifier().equals(getComponentIdentifier())
                && other.getContextAsMap().equals(getContextAsMap());
    }

}
