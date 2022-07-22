package cintsyde.simulation.moc.sdf;

import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.typed.viewers.moc.sdf.SDFActor;
import forsyde.io.java.typed.viewers.typing.TypedOperation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class ForSyDeSystemCSDFActorAcessor {

    @Getter @Setter
    ForSyDeSystemGraph baseModel;

    @Getter @Setter
    SDFActor sdfActor;

    public String getConstructorCall() {
        return "";
    }
}
