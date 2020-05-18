package warehouse;

import agentSearch.Heuristic;

public class HeuristicWarehouse extends Heuristic<WarehouseProblemForSearch, WarehouseState> {
    @Override
    public double compute(WarehouseState state){
        return Math.abs(state.getLineAgent()-this.getProblem().getGoalLine(state))+Math.abs(state.getColumnAgent()-this.getProblem().getGoalColumn(state));
    }

    @Override
    public String toString(){
        return "distance to final position";

    }
}