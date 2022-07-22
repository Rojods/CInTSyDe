package cintsyde.inline.platform;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cintsyde.interfaces.StringComponent;
import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.typed.viewers.decision.Scheduled;
import forsyde.io.java.typed.viewers.platform.runtime.CyclicExecutiveScheduler;
import lombok.Getter;
import lombok.Setter;

public class SimpleCyclicExecutiveSchedulerLoop implements StringComponent<ForSyDeSystemGraph> {

    @Getter @Setter String componentIdentifier;
    @Getter @Setter ForSyDeSystemGraph baseModel;
    @Getter @Setter CyclicExecutiveScheduler scheduler;

    // deduced elements
    List<Scheduled> scheduledComputation = baseModel.vertexSet().stream().flatMap(v -> Scheduled.safeCast(v).stream())
        .filter(s -> s.getSchedulersPort(baseModel).contains(scheduler))
        .collect(Collectors.toList());
    List<Scheduled> orderedEntries = new ArrayList<>();
//    		.collect {name ->
//        scheduledComputation.find {it.getIdentifier() == name }
//    }


    @Override
    public Map<String, Object> getContextAsMap() {
        deduceFields();
        return Map.of(
                "schedulerName", scheduler.getIdentifier(),
                "orderedEntries", orderedEntries.stream().map(e -> e.getIdentifier()).collect(Collectors.toList())
                );
    }

    @Override
    public void setContextByMap(Map<String, Object> context) {
        scheduler = (CyclicExecutiveScheduler) context.getOrDefault("scheduler", scheduler);
        deduceFields();
    }

    @Override
    public String getComponentTemplate() throws IOException, URISyntaxException {
        return Files.readString(Path.of(getClass().getResource("CyclicExecutiveProcessorWithMemory.h").toURI()));
    }

    public void deduceFields() {
        scheduledComputation = baseModel.vertexSet().stream().flatMap(v -> Scheduled.safeCast(v).stream())
                .filter(s -> s.getSchedulersPort(baseModel).contains(scheduler))
                .collect(Collectors.toList());
        orderedEntries = scheduler.getEntries().stream().flatMap(id -> scheduledComputation.stream().filter(s -> s.getIdentifier().equals(id))).collect(Collectors.toList());
    }

//    public def makeEntry(Scheduled entry) {
//        SDFActor.safeCast(entry).map(a -> "fire_" + a.getIdentifier())
//    }
}
