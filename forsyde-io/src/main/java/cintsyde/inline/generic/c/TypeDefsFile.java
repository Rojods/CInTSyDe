package cintsyde.inline.generic.c;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import cintsyde.engine.Generate;
import cintsyde.interfaces.Component;
import cintsyde.interfaces.FileComponent;
import cintsyde.interfaces.StringComponent;
import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.typed.viewers.typing.datatypes.DataType;
import lombok.Getter;
import lombok.Setter;

public class TypeDefsFile implements FileComponent<ForSyDeSystemGraph> {

    @Getter
    @Setter
    ForSyDeSystemGraph baseModel;
    @Getter
    @Setter
    Path targetPath;
    @Getter
    @Setter
    Set<TypeDef> typeDefs = new HashSet<>();

    public TypeDefsFile(ForSyDeSystemGraph baseModel) {
        this.baseModel = baseModel;
        typeDefs = baseModel.vertexSet().stream().flatMap(
                        v -> DataType.safeCast(v).stream())
                .map(dt -> new TypeDef(baseModel, dt))
                .collect(Collectors.toSet());
    }

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
        typeDefs = (Set<TypeDef>) context.getOrDefault("typeDefs", typeDefs);
    }

    @Override
    public boolean subsumes(Component<ForSyDeSystemGraph> other) {
        if (other instanceof TypeDefsFile) {
            final TypeDefsFile typeDefsFile = (TypeDefsFile) other;
            return typeDefs.containsAll(typeDefsFile.getTypeDefs());
        }
        return false;
    }

//    public static Set<TypeDefsFile> generate(ForSyDeSystemGraph baseModel,
//                                             Set<? extends Component<ForSyDeSystemGraph>> components) {
//        final Set<TypeDefsFile> generatedComponents = components.stream().filter(c -> c instanceof TypeDefsFile)
//                .map(c -> (TypeDefsFile) c).collect(Collectors.toSet());
//        final boolean hasTypes = baseModel.vertexSet().stream().anyMatch(c -> DataType.conforms(c));
//        final Set<TypeDef> generatedTypes = components.stream().filter(c -> c instanceof TypeDef).map(c -> (TypeDef) c)
//                .collect(Collectors.toSet());
//        // check if there is still any datatype that has not been covered by the
//        // typedefsfile
//        if (!hasTypes)  {
//                return null;
//        } else if (!generatedComponents.isEmpty() && generatedTypes.stream().allMatch(t -> generatedComponents.stream().anyMatch(c -> c.getTypeDefs().contains(t)))) {
//                return null;
//        } else {
//            final TypeDefsFile typeDefsFile = new TypeDefsFile();
//            typeDefsFile.typeDefs = generatedTypes;
//            typeDefsFile.baseModel = baseModel;
//            typeDefsFile.targetPath = Paths.get("inc/datatypes.h");
//            return Set.of(typeDefsFile);
//        }
//    }
}
