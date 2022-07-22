package cintsyde.inline.generic.c;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.ListUtils;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;

import cintsyde.engine.Generate;
import cintsyde.interfaces.Component;
import cintsyde.interfaces.StringComponent;
import forsyde.io.java.core.EdgeInfo;
import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.typed.viewers.typing.datatypes.Array;
import forsyde.io.java.typed.viewers.typing.datatypes.DataType;
import forsyde.io.java.typed.viewers.typing.datatypes.Double;
import forsyde.io.java.typed.viewers.typing.datatypes.Float;
import forsyde.io.java.typed.viewers.typing.datatypes.Integer;
import forsyde.io.java.typed.viewers.typing.datatypes.Record;
import lombok.Getter;
import lombok.Setter;

@Generate
public class TypeDef implements StringComponent<ForSyDeSystemGraph> {

    @Getter
    @Setter
    private ForSyDeSystemGraph baseModel;
    @Getter
    @Setter
    private DataType dataType;
    @Getter
    @Setter
    private Set<TypeDef> childrenTypeDefs = new HashSet<>();

    public TypeDef(ForSyDeSystemGraph baseModel, DataType dataType) {
        this.baseModel = baseModel;
        this.dataType = dataType;
    }

    @Override
    public Map<String, Object> getContextAsMap() {
        return null;
    }

    @Override
    public void setContextByMap(Map<String, Object> context) {

    }

    @Override
    public String getComponentTemplate() {
        if (dataType instanceof Record) {
            final StringBuilder template = new StringBuilder("");
            final Record r = (Record) dataType;
            template.append("#ifndef DATA_TYPE_{{typeName}}_H_\n")
                    .append("#define DATA_TYPE_{{typeName}}_H_\n")
                    .append("typedef struct {\n");
            int childNum = 0;
            r.getInnerTypesPort(baseModel).forEach(i -> {
                baseModel.getAllEdges(r.getViewedVertex(), i.getViewedVertex()).stream()
                        .map(EdgeInfo::getSourcePort)
                        .forEach(srcPortOpt -> {
                            srcPortOpt.ifPresentOrElse(srcPort -> {
                                template.append("  ${child.identifier} ${srcPort};\n");
                            }, () -> {
                                template.append("  ${child.identifier} child${childNum};\n");
                            });
                        });
            });
            template.append("\n} ").append(getDataType().getIdentifier()).append(";\n#endif");
            return template.toString();
        } else if (dataType instanceof Array) {
            final Array a = (Array) dataType;
            final StringBuilder template = new StringBuilder("");
            a.getInnerTypePort(baseModel).ifPresentOrElse(child -> {
                template.append("typedef ").append(child.getIdentifier()).append(" ");
                if (a.getMaximumElems() > -1)
                    template.append("[").append(a.getMaximumElems()).append("] ");
                else
                    template.append("* ");
                template.append(a.getIdentifier()).append(";");
            }, () -> {
                template.append("typedef void* ").append(getDataType().getIdentifier())
                        .append("; // ERROR: missing child value! default to void");
            });
            return template.toString();
        } else if (dataType instanceof Integer) {
            if (((Integer) dataType).getNumberOfBits() <= 8)
                return "typedef char " + getDataType().getIdentifier() + ";";
            else if (((Integer) dataType).getNumberOfBits() <= 32)
                return "typedef int " + getDataType().getIdentifier() + ";";
            else if (((Integer) dataType).getNumberOfBits() <= 64)
                return "typedef long " + getDataType().getIdentifier() + ";";
            else
                return "typedef long long " + getDataType().getIdentifier() + ";";
        } else if (dataType instanceof Float) {
            return "typedef float " + getDataType().getIdentifier();
        } else if (dataType instanceof Double) {
            return "typedef double " + getDataType().getIdentifier();
        } else {
            return "ERROR! typedef could not be generated! check " + getDataType().getIdentifier() + " for mistakes.";
        }

    }

    public String generateWithAllChildren() throws IOException, URISyntaxException {
        StringBuilder t = new StringBuilder();
        for (TypeDef child : childrenTypeDefs) {
            t.append(child.generateWithAllChildren());
        }
        return t + generateComponent();
    }

    public boolean equals(Object o) {
        if (DefaultGroovyMethods.is(this, o))
            return true;
        if (!getClass().equals(o.getClass()))
            return false;

        TypeDef typeDef = (TypeDef) o;

        if (!baseModel.equals(typeDef.getBaseModel()))
            return false;
        if (!DefaultGroovyMethods.equals(childrenTypeDefs, typeDef.getChildrenTypeDefs()))
            return false;
        if (!dataType.equals(typeDef.getDataType()))
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = baseModel.hashCode();
        result = 31 * result + dataType.hashCode();
        return result;
    }

    public static Set<TypeDef> generate(final ForSyDeSystemGraph baseModel,
            Set<? extends Component<ForSyDeSystemGraph>> components) {
        final List<TypeDef> generatedComponents = components.stream().filter(c -> c instanceof TypeDef)
                .map(c -> (TypeDef) c)
                .collect(Collectors.toList());
        final List<DataType> generatedTypes = generatedComponents.stream().map(TypeDef::getDataType)
                .collect(Collectors.toList());
        final List<DataType> newTypes = baseModel.vertexSet().stream().flatMap(
                v -> DataType.safeCast(v).stream()).filter(d -> !generatedTypes.contains(d))
                .collect(Collectors.toList());
        final List<TypeDef> newComponents = newTypes.stream().map(t -> new TypeDef(baseModel, t))
                .collect(Collectors.toList());
        final List<TypeDef> allComponents = ListUtils.union(generatedComponents, generatedComponents);
        // connect data types that depend on each other
        for (TypeDef src : allComponents) {
            for (TypeDef dst : allComponents) {
                // check if the array inner type is the same as the dst
                Array.safeCast(src.getDataType())
                        .filter(a -> a.getInnerTypePort(baseModel).equals(Optional.of(dst.dataType)))
                        .filter(a -> !src.getChildrenTypeDefs().contains(dst))
                        .ifPresent(a -> src.getChildrenTypeDefs().add(dst));
                // same for records
                Record.safeCast(src.getDataType()).filter(a -> a.getInnerTypesPort(baseModel).contains(dst.dataType))
                        .filter(a -> !src.getChildrenTypeDefs().contains(dst))
                        .ifPresent(a -> src.getChildrenTypeDefs().add(dst));
            }

        }

        // return null to denote that all has been generated
        if (newComponents.size() == 0)
            return null;
        else
            return new HashSet<>(newComponents);
    }

    @Override
    public boolean componentIsEqual(Component<ForSyDeSystemGraph> other) {
        return (other instanceof TypeDef) && ((TypeDef) other).dataType.equals(getDataType());
    }

    @Override
    public boolean subsumes(Component<ForSyDeSystemGraph> other) {
        if (other instanceof TypeDef) {
            final TypeDef typeDef = (TypeDef) other;
            return dataType.equals(typeDef.getDataType()) && childrenTypeDefs.containsAll(typeDef.getChildrenTypeDefs());
        }
        return StringComponent.super.subsumes(other);
    }
}
