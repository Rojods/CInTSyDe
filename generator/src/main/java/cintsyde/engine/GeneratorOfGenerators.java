package cintsyde.engine;

import com.google.auto.service.AutoService;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@SupportedAnnotationTypes("cintsyde.engine.Generate")
@SupportedSourceVersion(SourceVersion.RELEASE_11)
@AutoService(Processor.class)
public class GeneratorOfGenerators extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement te : annotations) {
            if (te.getSimpleName().contentEquals("Generate")) {
                final List<TypeElement> methods = te.getEnclosedElements().stream().filter(e -> e instanceof TypeElement)
                                .map(e -> (TypeElement) e)
                                .collect(Collectors.toList());

                return true;
            }

        }

        return false;
    }

}
