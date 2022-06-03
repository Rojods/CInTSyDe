package cintsyde.inline.platform


import cintsyde.interfaces.StringComponent;
import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.typed.viewers.decision.Scheduled
import forsyde.io.java.typed.viewers.moc.sdf.SDFActor;
import forsyde.io.java.typed.viewers.platform.GenericMemoryModule;
import forsyde.io.java.typed.viewers.platform.GenericProcessingModule;
import forsyde.io.java.typed.viewers.platform.runtime.CyclicExecutiveScheduler

import java.nio.file.Path
import java.util.stream.Collectors

public class SimpleCyclicExecutiveSchedulerLoop implements StringComponent<ForSyDeSystemGraph> {

    String componentIdentifier
    ForSyDeSystemGraph baseModel
    CyclicExecutiveScheduler scheduler

    // deduced elements
    List<Scheduled> scheduledComputation = baseModel.vertexSet().stream().flatMap(v -> Scheduled.safeCast(v).stream())
        .filter(s -> s.getSchedulerPort(baseModel).contains(scheduler))
        .collect(Collectors.toList())
    List<Scheduled> orderedEntries = scheduler.getEntries().collect {name ->
        scheduledComputation.find {it.getIdentifier() == name }
    }


    @Override
    Map<String, Object> getContextAsMap() {
        deduceFields()
        return [
                schedulerName: scheduler.getIdentifier(),
                orderedEntries: orderedEntries.collect {
                    [entryName: it.getIdentifier()]
                }
        ]
    }

    @Override
    void setContextByMap(Map<String, Object> context) {
        scheduler = (CyclicExecutiveScheduler) context.getOrDefault("scheduler", scheduler)
        deduceFields()
    }

    @Override
    String getComponentTemplate() {
        return getClass().getResource("CyclicExecutiveProcessorWithMemory.h")
    }

    def deduceFields() {
        scheduledComputation = baseModel.vertexSet().stream().flatMap(v -> Scheduled.safeCast(v).stream())
                .filter(s -> s.getSchedulerPort(model).contains(scheduler))
                .collect(Collectors.toList())
        orderedEntries = scheduler.getEntries().collect {name ->
            scheduledComputation.find {it.getIdentifier() == name }
        }
    }

    def makeEntry(Scheduled entry) {
        SDFActor.safeCast(entry).map(a -> "fire_" + a.getIdentifier())
    }
}
