package cintsyde.inline.generic.c

import cintsyde.engine.Generate
import cintsyde.interfaces.Component
import cintsyde.interfaces.FileComponent
import cintsyde.interfaces.StringComponent
import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.typed.viewers.typing.datatypes.DataType

import java.nio.file.Path

@Generate
class TypeDefsFile implements FileComponent<ForSyDeSystemGraph> {

    ForSyDeSystemGraph baseModel
    Path targetPath
    List<TypeDef> typeDefs = new ArrayList<>();

    @Override
    List<StringComponent<ForSyDeSystemGraph>> getStringComponents() {
        // do a small flattening of the typedefs
        def open = new LinkedList<TypeDef>(typeDefs)
        def flattened = []
        while (open.size() > 0) {
            def top = open.pop()
            for (def child : top.childrenTypeDefs) {
                if (!flattened.contains(child)) flattened.add(child)
                open.add(child)
            }
            if (!flattened.contains(top)) flattened.add(top)
        }
        return flattened;
    }


    @Override
    Map<String, Object> getContextAsMap() {
        return ["typeDefs": typeDefs]
    }

    @Override
    void setContextByMap(Map<String, Object> context) {
        typeDefs = (List<TypeDef>) context.getOrDefault("typeDefs", typeDefs)
    }

    static List<TypeDef> generate(ForSyDeSystemGraph baseModel, List<Component<ForSyDeSystemGraph>> components) {
        def generatedComponents = components.findAll { it instanceof TypeDefsFile }
                .collect { it as TypeDefsFile }
        def hasTypes = baseModel.vertexSet().any { DataType.conforms(it) }
        def generatedTypes = components.findAll { it instanceof TypeDef}
                .collect { it as TypeDef }
        return List.of()
    }
}
