package cintsyde.interfaces;

import com.github.mustachejava.Mustache;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URISyntaxException;

public interface StringComponent<BaseT> extends Component<BaseT> {

    String getComponentTemplate() throws IOException, URISyntaxException;

    default String generateComponentString() throws IOException, URISyntaxException {
        final Mustache mustache = mustacheFactory.compile(new StringReader(getComponentTemplate()),
                getComponentIdentifier());
        final StringWriter writer = new StringWriter();
        mustache.execute(writer, getContextAsMap());
        return writer.toString();
    }


}
