package warehouse;

import agentSearch.Action;
import agentSearch.Problem;

import java.util.LinkedList;
import java.util.List;

public class WarehouseProblemForSearch<S extends WarehouseState> extends Problem<S> {

    //TODO this class might require the definition of additional methods and/or attributes
    private Cell goalPosition;
    private LinkedList<Action> actions;
    //ver 8 puzzle problem
    public WarehouseProblemForSearch(S initialWarehouseState, Cell goalPosition) {
        super(initialWarehouseState);

        actions = new LinkedList<>();
        actions.add(new ActionDown());
        actions.add(new ActionUp());
        actions.add(new ActionRight());
        actions.add(new ActionLeft());

        this.goalPosition = goalPosition;


    }

    @Override
    public List<S> executeActions(S state) {
        List<S> successors = new LinkedList<>();
        for (Action a : actions) {
            if(a.isValid(state)){
                S successor = (S)state.clone();
                a.execute(successor);
                successors.add(successor);
            }
        }
        return successors;
    }

    public boolean isGoal(S state) {
        if(initialState.getMatrix()[goalPosition.getLine()][goalPosition.getColumn()] == 1){
            return state.getColumnAgent() == goalPosition.getColumn() &&  state.getLineAgent() == goalPosition.getLine();
        }else {
            return state.getColumnAgent() == goalPosition.getColumn()+1 &&  state.getLineAgent() == goalPosition.getLine();
        }
    }

    public int getGoalLine(S state){
        return goalPosition.getLine();
    }

    public int getGoalColumn(S state){
        return goalPosition.getColumn();
    }
}
