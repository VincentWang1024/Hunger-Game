package junction.model;

import java.util.LinkedList;

import massim.core.model.Action;

public class EnvironmentState {
    public VehicleState perceptions;
    public LinkedList<Action> actions = new LinkedList<>();
    
    public EnvironmentState(VehicleState perceptions) {
        this.perceptions = perceptions;

        // Build affordance list (here for now)
        actions.add(new Action("skip"));

        if (perceptions.at == 0) {
            for (String exit:perceptions.exits) {
                actions.add(new Action("turn", new Object[] {exit}));
            }
        }
    }
}
