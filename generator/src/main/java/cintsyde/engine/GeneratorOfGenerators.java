package cintsyde.engine;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import org.codehaus.groovy.runtime.DefaultGroovyMethods;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ExecutableType;
import javax.tools.JavaFileObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
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
            if (te.getQualifiedName().contentEquals("cintsyde.engine.Generate")) {
                final Set<TypeElement> annotatedClasses = roundEnv.getElementsAnnotatedWith(te)
                        .stream().filter(o -> o instanceof TypeElement).map(o -> (TypeElement) o)
                        .collect(Collectors.toSet());
                try {
                    final JavaFileObject generatorsFile = processingEnv.getFiler()
                            .createSourceFile("cinstyde.engine.ComponentGenerators");
                    final MethodSpec.Builder getComponentGeneratorsMethod = MethodSpec
                            .methodBuilder("getComponentGenerators")
                            .returns(ParameterizedTypeName.get(List.class, ComponentGenerator.class));
                    final TypeSpec.Builder componentGeneratorsClassBuilder = TypeSpec
                            .classBuilder("ComponentGenerators");
                    // ParameterizedTypeName.get(List.class, ), name, modifiers));
                    for (final TypeElement cls : annotatedClasses) {
                        final Optional<ExecutableElement> generateMethod = cls.getEnclosedElements()
                                .stream()
                                .filter(c -> c instanceof ExecutableElement
                                        && c.getSimpleName().contentEquals("generate"))
                                .map(c -> (ExecutableElement) c).findAny();
                        generateMethod.ifPresent(ex -> {
                            System.out.println(cls);
                            System.out.println(ex);
                        });
                        // for (Element child : cls.getEnclosedElements()) {
                        // System.out.println(child.getKind());
                        // System.out.println(child instanceof ExecutableElement);
                        // System.out.println(child);
                        // }
                        // System.out.println(cls.getEnclosedElements());
                    }

                    getComponentGeneratorsMethod.addStatement("return null");

                    componentGeneratorsClassBuilder.addMethod(getComponentGeneratorsMethod.build());

                    try (PrintWriter out = new PrintWriter(generatorsFile.openWriter())) {
                        out.print(componentGeneratorsClassBuilder.build().toString());
                    }

                    // System.out.println(annotatedClasses);
                    // final Set<Element> generateMethods = annotatedClasses.stream()
                    // .flatMap(c -> c.getEnclosedElements().stream())
                    // // .filter(o -> o instanceof ExecutableType)
                    // // .map(o -> (ExecutableType) o)
                    // .collect(Collectors.toSet());
                    // System.out.println(generateMethods);
                } catch (IOException e) {
                    System.out.println(e.getStackTrace());
                }

                return true;
            }

        }

        return false;
    }

}
