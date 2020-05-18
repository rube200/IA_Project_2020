package warehouse;

import agentSearch.Problem;

import java.util.LinkedList;
import java.util.List;

public class WarehouseProblemForSearch<S extends WarehouseState> extends Problem<S> {
    private Cell goal;

    public WarehouseProblemForSearch(S initialWarehouseState, Cell goalPosition) {
        super(initialWarehouseState);
        this.goal = goalPosition;
    }

    @Override
    public List<S> executeActions(S state) {
        List<S> actions = new LinkedList<S>();


        if (state.canMoveUp())
        {
            S upState = (S)state.clone();
            upState.moveUp();
            actions.add(upState);
        }


        if (state.canMoveDown())
        {
            S upState = (S)state.clone();
            upState.moveDown();
            actions.add(upState);
        }


        if (state.canMoveLeft())
        {
            S upState = (S)state.clone();
            upState.moveLeft();
            actions.add(upState);
        }


        if (state.canMoveRight())
        {
            S upState = (S)state.clone();
            upState.moveRight();
            actions.add(upState);
        }

        return actions;//TODO something wrong
    }

    public boolean isGoal(S state) {
        return this.goal.getLine() == state.getLineAgent() && this.goal.getColumn() == state.getColumnAgent();
    }
}
