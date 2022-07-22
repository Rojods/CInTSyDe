package cintsyde.inline.platform;

import java.util.Map;

import cintsyde.interfaces.StringComponent;
import forsyde.io.java.core.ForSyDeSystemGraph;
import lombok.Getter;
import lombok.Setter;

public class PartitionedSharedMemMultiCore implements StringComponent<ForSyDeSystemGraph> {

	@Getter @Setter
    ForSyDeSystemGraph baseModel;

	@Getter @Setter
    String componentTemplate = "";

    @Override
    public Map<String, Object> getContextAsMap() {
        return null;
    }

    @Override
    public void setContextByMap(Map<String, Object> context) {

    }
}
