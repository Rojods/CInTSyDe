package cintsyde.inline.platform


import cintsyde.interfaces.StringComponent;
import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.typed.viewers.decision.Scheduled;
import forsyde.io.java.typed.viewers.platform.GenericMemoryModule;
import forsyde.io.java.typed.viewers.platform.GenericProcessingModule;
import forsyde.io.java.typed.viewers.platform.runtime.CyclicExecutiveScheduler

import java.nio.file.Path
import java.util.stream.Collectors

public class CyclicExecutiveProcessorWithMemory implements StringComponent<ForSyDeSystemGraph> {

    String componentIdentifier
    ForSyDeSystemGraph baseModel
    CyclicExecutiveScheduler scheduler;
    GenericProcessingModule processor;
    GenericMemoryModule mainMemory;
    List<GenericMemoryModule> auxiliaryMemories;

    // deduced elements
    List<Scheduled> scheduledComputation = baseModel.vertexSet().stream().flatMap(v -> Scheduled.safeCast(v).stream())
        .filter(s -> s.getSchedulerPort(model).contains(scheduler))
        .collect(Collectors.toList())
//    def orderedEntries = scheduler.getEntries().collect {
//
//    }

    public CyclicExecutiveProcessorWithMemory(CyclicExecutiveScheduler scheduler, GenericProcessingModule processor) {
        this.scheduler = scheduler;
        this.processor = processor;
    }

    @Override
    Map<String, Object> getContextAsMap() {
        return null
    }

    @Override
    void setContextByMap(Map<String, Object> context) {

    }

    @Override
    String getComponentTemplate() {

        return null
    }
}
