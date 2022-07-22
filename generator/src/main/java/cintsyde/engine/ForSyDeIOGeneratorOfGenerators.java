package cintsyde.engine;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;

import cintsyde.interfaces.Component;
import forsyde.io.java.core.ForSyDeSystemGraph;

@SupportedAnnotationTypes("cintsyde.engine.Generate")
@SupportedSourceVersion(SourceVersion.RELEASE_11)
@AutoService(Processor.class)
public class ForSyDeIOGeneratorOfGenerators extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // final TypeSpec forsydeIoComponentGeneratorClass =
        //     ParameterizedTypeName.get(Collection.class, ClassName.get("", ""))
        final TypeName forsydeIoComponent = ParameterizedTypeName.get(Component.class, ForSyDeSystemGraph.class);
        final TypeName forsydeIoGenComponent = ParameterizedTypeName.get(ClassName.get(ComponentGenerator.class), TypeName.get(ForSyDeSystemGraph.class));
        final MethodSpec.Builder getComponentGeneratorsMethod = MethodSpec
                        .methodBuilder("getComponentGenerators")
                        .addModifiers(Modifier.PUBLIC)
                        .returns(
                            ParameterizedTypeName.get(
                                ClassName.get(Set.class),
                                forsydeIoGenComponent));
        final TypeSpec.Builder componentGeneratorsClassBuilder = TypeSpec
                        .classBuilder("ForSyDeIOComponentGenerators")
                        .addModifiers(Modifier.PUBLIC);
        
        for (TypeElement te : annotations) {
            if (te.getQualifiedName().contentEquals("cintsyde.engine.Generate")) {
                final Set<TypeElement> annotatedClasses = roundEnv.getElementsAnnotatedWith(te)
                        .stream().filter(o -> o instanceof TypeElement).map(o -> (TypeElement) o)
                        .collect(Collectors.toSet());
                final Set<TypeElement> toBeGenerated = new HashSet<>();
                // ParameterizedTypeName.get(List.class, ), name, modifiers));
                for (final TypeElement cls : annotatedClasses) {
                    // final Class<?> baseType = cls.getTypeParameters().get(0).getClass();
                    // System.out.println(baseType);
                    cls.getEnclosedElements().stream()
                        .filter(c -> c instanceof ExecutableElement)
                        .map(c -> (ExecutableElement) c)
                            // <init> is the name given to constructors
                        .filter(c -> c.getSimpleName().contentEquals("<init>"))
                        .filter(c -> c.getParameters().size() == 1)
                        // .filter(c -> c.getParameters().get(0).get.getSimpleName().contentEquals("ForSyDeSystemGraph"))
                        // .filter(c -> c.getReturnType().)
                        .findAny().ifPresent(exec -> {
                            final MethodSpec clsGenerationMethod = MethodSpec.methodBuilder("generate")
                                    .addModifiers(javax.lang.model.element.Modifier.PUBLIC)
                                    .returns(forsydeIoComponent)
                                    .addAnnotation(Override.class)
                                    .addParameter(ForSyDeSystemGraph.class, "baseModel")
//                                    .addParameter(ParameterizedTypeName.get(ClassName.get(Set.class), WildcardTypeName.subtypeOf(forsydeIoComponent)), "components")
                                    .addStatement("return new $T($L)", TypeName.get(cls.asType()), "baseModel")
                                    .build();
                            final TypeSpec.Builder clsGeneratorClassBuilder = TypeSpec
                                .classBuilder(cls.getSimpleName() + "Generator")
                                .addSuperinterface(forsydeIoGenComponent)
                                .addMethod(clsGenerationMethod);
                            componentGeneratorsClassBuilder.addType(clsGeneratorClassBuilder.build());
                            toBeGenerated.add(cls);
                        });
                }

                // getComponentGeneratorsMethod.addStatement("return null");

                // put all classes together
                getComponentGeneratorsMethod.addStatement("return $T.of($L)", Set.class,
                    toBeGenerated.stream().map(c -> "new " + c.getSimpleName() + "Generator()").collect(Collectors.joining(",\n"))
                );
                componentGeneratorsClassBuilder.addMethod(getComponentGeneratorsMethod.build());
                try {
                    final JavaFileObject generatorsFile = processingEnv.getFiler()
                            .createSourceFile("cinstyde.engine.ForSyDeIOComponentGenerators");
                    try (PrintWriter out = new PrintWriter(generatorsFile.openWriter())) {
                        final JavaFile fJavaFile = JavaFile.builder("cintsyde.engine",
                                componentGeneratorsClassBuilder.build()).build();
                        fJavaFile.writeTo(out);
                    }

                    // System.out.println(annotatedClasses);
                    // final Set<Element> generateMethods = annotatedClasses.stream()
                    // .flatMap(c -> c.getEnclosedElements().stream())
                    // // .filter(o -> o instanceof ExecutableType)
                    // // .map(o -> (ExecutableType) o)
                    // .collect(Collectors.toSet());
                    // System.out.println(generateMethods);
                } catch (IOException e) {
                    System.out.println(Arrays.toString(e.getStackTrace()));
                }

                return true;
            }

        }

        return false;
    }

}
