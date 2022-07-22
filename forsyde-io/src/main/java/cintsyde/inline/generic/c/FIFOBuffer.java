package cintsyde.inline.generic.c;

import cintsyde.engine.Generate;
import cintsyde.inline.moc.sdf.SDFChannelsDefs;
import cintsyde.interfaces.Component;
import cintsyde.interfaces.StringComponent;
import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel;
import forsyde.io.java.typed.viewers.typing.datatypes.Array;
import forsyde.io.java.typed.viewers.typing.datatypes.DataType;
import forsyde.io.java.typed.viewers.typing.datatypes.Record;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.ListUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

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

    public FIFOBuffer(ForSyDeSystemGraph baseModel, DataType fifoDataType, TypeDefsFile typeDefsFile) {
        this.baseModel = baseModel;
        this.fifoDataType = fifoDataType;
        this.typeDefsFile = typeDefsFile;
    }

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

    public static Set<FIFOBuffer> generate(ForSyDeSystemGraph baseModel, Set<? extends Component<ForSyDeSystemGraph>> components) {
        final List<DataType> generatedTypes = components.stream().filter(c -> c instanceof FIFOBuffer)
                .map(c -> (FIFOBuffer) c)
                .map(FIFOBuffer::getFifoDataType)
                .collect(Collectors.toList());
        final Set<FIFOBuffer> additional = baseModel.vertexSet().stream()
                .flatMap(v -> DataType.safeCast(v).stream())
                .filter(t -> !generatedTypes.contains(t))
                .flatMap(dataType ->
                    components.stream().filter(c -> c instanceof TypeDefsFile)
                            .map(c -> (TypeDefsFile) c)
                            .map(typeDefsFile1 -> new FIFOBuffer(baseModel, dataType, typeDefsFile1))
                )
                .collect(Collectors.toSet());
        System.out.println(generatedTypes);
        System.out.println(additional);
        // return null to denote that all has been generated
        if (additional.size() == 0)
            return null;
        else
            return additional;
    }
}
