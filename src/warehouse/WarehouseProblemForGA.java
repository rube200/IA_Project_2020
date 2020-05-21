package warehouse;

import ga.Problem;

import java.util.LinkedList;

public class WarehouseProblemForGA implements Problem<WarehouseIndividual> {
    private WarehouseAgentSearch agentSearch;
    public WarehouseProblemForGA(WarehouseAgentSearch agentSearch) {
        this.agentSearch= agentSearch;

    }

    public WarehouseAgentSearch getAgentSearch() {
        return agentSearch;
    }

    @Override
    public WarehouseIndividual getNewIndividual() {
        return new WarehouseIndividual(this, WarehouseAgentSearch.getShelves().size());
    }


}
