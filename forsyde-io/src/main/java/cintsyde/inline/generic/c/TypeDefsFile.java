package cintsyde.inline.generic.c;

import cintsyde.engine.Generate;
import cintsyde.interfaces.Component;
import cintsyde.interfaces.FileComponent;
import cintsyde.interfaces.StringComponent;
import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.typed.viewers.typing.datatypes.DataType;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Generate
public class TypeDefsFile implements FileComponent<ForSyDeSystemGraph> {

    @Getter
    @Setter
    ForSyDeSystemGraph baseModel;
    @Getter
    @Setter
    Path targetPath;
    @Getter
    @Setter
    List<TypeDef> typeDefs = new ArrayList<>();

    @Override
    public List<? extends StringComponent<ForSyDeSystemGraph>> getStringComponents() {
        // do a small flattening of the typedefs
        final LinkedList<TypeDef> open = new LinkedList<TypeDef>(typeDefs);
        final List<TypeDef> flattened = new LinkedList<>();
        while (open.size() > 0) {
            final TypeDef top = open.pop();
            for (final TypeDef child : top.getChildrenTypeDefs()) {
                if (!flattened.contains(child))
                    flattened.add(child);
                open.add(child);
            }
            if (!flattened.contains(top))
                flattened.add(top);
        }
        return flattened;
    }

    @Override
    public Map<String, Object> getContextAsMap() {
        return Map.of("typeDefs", typeDefs);
    }

    @Override
    public void setContextByMap(Map<String, Object> context) {
        typeDefs = (List<TypeDef>) context.getOrDefault("typeDefs", typeDefs);
    }

    public static List<TypeDef> generate(ForSyDeSystemGraph baseModel,
            List<? extends Component<ForSyDeSystemGraph>> components) {
        final List<TypeDefsFile> generatedComponents = components.stream().filter(c -> c instanceof TypeDefsFile)
                .map(c -> (TypeDefsFile) c).collect(Collectors.toList());
        final boolean hasTypes = baseModel.vertexSet().stream().anyMatch(c -> DataType.conforms(c));
        final List<TypeDef> generatedTypes = components.stream().filter(c -> c instanceof TypeDef).map(c -> (TypeDef) c)
                .collect(Collectors.toList());
        return List.of();
    }
}
