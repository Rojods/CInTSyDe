package platform;

import cintsyde.interfaces.Component;
import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.typed.viewers.platform.GenericMemoryModule;
import forsyde.io.java.typed.viewers.platform.GenericProcessingModule;
import forsyde.io.java.typed.viewers.platform.runtime.CyclicExecutiveScheduler;
import interfaces.ForSyDeIOComponent;

import java.util.List;

public class CyclicExecutiveProcessorWithMemory implements ForSyDeIOComponent  {

    CyclicExecutiveScheduler scheduler;
    GenericProcessingModule processor;
    GenericMemoryModule mainMemory;
    List<GenericMemoryModule> auxiliaryMemories;

    public CyclicExecutiveProcessorWithMemory(CyclicExecutiveScheduler scheduler, GenericProcessingModule processor) {
        this.scheduler = scheduler;
        this.processor = processor;
    }

    @Override
    public boolean componentIsEqual(Component<ForSyDeSystemGraph> other) {
        if (other instanceof CyclicExecutiveProcessorWithMemory) {
            return false;
        } else {
            return false;
        }
    }

    @Override
    public void expand(ForSyDeSystemGraph baseModel) {

    }
}
