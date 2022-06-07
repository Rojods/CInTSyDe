package cintsyde.engine

import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.TypeElement


class GeneratorOfGenerator extends AbstractProcessor {

    @Override
    boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        return false
    }
}
