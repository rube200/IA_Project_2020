package warehouse;

import ga.Problem;

import java.util.LinkedList;

public class WarehouseProblemForGA implements Problem<WarehouseIndividual> {
    protected WarehouseAgentSearch agentSearch;
    public WarehouseProblemForGA(WarehouseAgentSearch agentSearch) {
        this.agentSearch = agentSearch;
    }

    @Override
    public WarehouseIndividual getNewIndividual() {
        return new WarehouseIndividual(this, WarehouseAgentSearch.getNumProducts());
    }

    @Override
    public String toString() {
        return "agentSearch: " + agentSearch.toString();
    }
}
