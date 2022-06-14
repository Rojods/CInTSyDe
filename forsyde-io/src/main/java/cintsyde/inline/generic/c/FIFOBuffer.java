package cintsyde.inline.generic.c;

import cintsyde.engine.Generate;
import cintsyde.interfaces.StringComponent;
import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.typed.viewers.typing.datatypes.DataType;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@Generate
public class FIFOBuffer implements StringComponent<ForSyDeSystemGraph> {

    @Getter
    @Setter
    ForSyDeSystemGraph baseModel;
    @Getter
    @Setter
    DataType fifoDataType;
    @Getter
    @Setter
    TypeDefsFile typeDefsFile;

    @Override
    public Map<String, Object> getContextAsMap() {
        return Map.of(
                "fifoDataType", fifoDataType,
                "pathToTypeDefs", typeDefsFile.targetPath.toString(),
                "typeName", fifoDataType.getIdentifier());
    }

    @Override
    public void setContextByMap(Map<String, Object> context) {
        fifoDataType = (DataType) context.getOrDefault("fifoDateType", fifoDataType);
    }

    @Override
    public String getComponentTemplate() throws IOException, URISyntaxException {
        return Files.readString(Path.of(getClass().getResource("FIFOBuffer.headerOnly.h").toURI()));
    }
}
