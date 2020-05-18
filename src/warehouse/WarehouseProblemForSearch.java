package warehouse;

import agentSearch.Action;
import agentSearch.Problem;

import java.util.LinkedList;
import java.util.List;

public class WarehouseProblemForSearch<S extends WarehouseState> extends Problem<S> {
    private Cell goalPosition;
    private LinkedList<Action> possibleActions;


    public WarehouseProblemForSearch(S initialWarehouseState, Cell goalPosition) {
        super(initialWarehouseState);
        this.goalPosition = goalPosition;

        this.possibleActions = new LinkedList<>();
        this.possibleActions.add(new ActionUp());
        this.possibleActions.add(new ActionDown());
        this.possibleActions.add(new ActionLeft());
        this.possibleActions.add(new ActionRight());
    }

    @Override
    public List<S> executeActions(S state) {
        List<S> successors = new LinkedList<>();
        for (Action action : possibleActions) 
        {
            if(action.isValid(state))
            {
                S successor = (S)state.clone();
                action.execute(successor);
                successors.add(successor);
            }
        }


        return successors;
    }

    public boolean isGoal(S state) {
        byte compensator = 1;
        if (this.goalPosition.getLine() == state.getLineExit() && this.goalPosition.getColumn() == state.getColumnExit())
            compensator = 0;

        return state.getLineAgent() == this.goalPosition.getLine() && state.getColumnAgent() == this.goalPosition.getColumn() + compensator;
    }

    public int getGoalLine(){
        return this.goalPosition.getLine();
    }

    public int getGoalColumn(){
        return this.goalPosition.getColumn();
    }
}
