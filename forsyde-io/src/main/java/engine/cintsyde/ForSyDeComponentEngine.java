package engine.cintsyde;

import cintsyde.engine.ComponentGenerator;
import cintsyde.engine.ForSyDeIOComponentGenerators;
import cintsyde.interfaces.Component;
import forsyde.io.java.core.ForSyDeSystemGraph;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ForSyDeComponentEngine {

    public Set<? extends Component<ForSyDeSystemGraph>> generateCompononents(ForSyDeSystemGraph baseModel) {
        final ForSyDeIOComponentGenerators forSyDeIOComponentGenerators = new ForSyDeIOComponentGenerators();
        return forSyDeIOComponentGenerators.getComponentGenerators().stream().map(cg -> cg.generate(baseModel))
                .collect(Collectors.toSet());
//        final Set<ComponentGenerator<ForSyDeSystemGraph>> generators = new HashSet<>(forSyDeIOComponentGenerators.getComponentGenerators());

//        final int maxIters = baseModel.vertexSet().size() * forSyDeIOComponentGenerators.getComponentGenerators().size();
//        final Set<Component<ForSyDeSystemGraph>> generatedComponents = new HashSet<>();
//        int iter = 0;
//        while (iter < maxIters && !generators.isEmpty()) {
//            final Set<ComponentGenerator<ForSyDeSystemGraph>> toRemove = new HashSet<>();
//            for (ComponentGenerator<ForSyDeSystemGraph> cg : generators) {
//                Set<? extends Component<ForSyDeSystemGraph>> additional = cg.generate(baseModel, generatedComponents);
//                // if its null, this is a fixpoint
//                if (additional == null) {
//                    toRemove.add(cg);
//                } else {
//                    // remove if at least one new component subsumes, but none of the new components is subsumed.
//                    generatedComponents.removeIf(c -> additional.stream().anyMatch(a -> a.subsumes(c)) && additional.stream().noneMatch(c::subsumes));
//                    generatedComponents.addAll(additional);
//                }
//            }
//            generators.removeAll(toRemove);
//            iter += 1;
//        }
//        return generatedComponents;
    }
}
