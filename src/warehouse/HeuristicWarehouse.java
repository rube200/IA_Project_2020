package warehouse;

import agentSearch.Heuristic;
import agentSearch.Problem;

public class HeuristicWarehouse extends Heuristic<WarehouseProblemForSearch, WarehouseState> {
    @Override
    public double compute(WarehouseState state){
        return Math.abs(state.getLineAgent() - problem.getGoalLine()) + Math.abs(state.getColumnAgent() - problem.getGoalColumn());
    }

    @Override
    public String toString(){
        return "Distance to final position";
    }
}