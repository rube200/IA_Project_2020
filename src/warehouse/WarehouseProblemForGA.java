package warehouse;

import ga.Problem;


public class WarehouseProblemForGA implements Problem<WarehouseIndividual> {
    protected WarehouseAgentSearch agentSearch;
    public WarehouseProblemForGA(WarehouseAgentSearch agentSearch) {
        this.agentSearch = agentSearch;
    }

    @Override
    public WarehouseIndividual getNewIndividual() {
        return new WarehouseIndividual(this, WarehouseAgentSearch.getShelves().size());
    }

    @Override
    public String toString() {
        return "agentSearch: " + agentSearch.toString();
    }
}
